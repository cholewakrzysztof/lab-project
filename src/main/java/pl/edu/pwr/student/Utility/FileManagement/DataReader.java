package pl.edu.pwr.student.Utility.FileManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.Delay;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.VirtualIO;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.IO.Input.Clock;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.*;
import pl.edu.pwr.student.UI.Blocks.CompoundElement;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.Creator.AbstractGateFactory;
import pl.edu.pwr.student.UI.UiAvailable;
import pl.edu.pwr.student.UI.Blocks.UiElement;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Class responsible for reading data from files and generating HashSet of UI elements
 */
public class DataReader {

    /**
     * @param file File with JSON string of UI Elements
     * @param canvas Canvas where all UiElements will be saved
     * @throws Exception when something wrong with path
     */
    public static void readFromFile(File file, final Canvas canvas) throws Exception {
        if (file == null || !file.exists() || file.isDirectory()) {
            canvas.showPopup("File does not exist or path is wrong");
            return;
        }

        Scanner myReader = new Scanner(file);
        canvas.clear();

        HashMap<Integer, UiAvailable> gates = new HashMap<>();
        HashMap<Integer,JSONAvailable> schema = new HashMap<>();

        UiAvailable element;

        while(myReader.hasNextLine()){
            JSONAvailable source = generateJSONAvailableFromJSON(myReader.nextLine());
            Integer id = source.getHashCode();
            String gateType = source.getGateType().toUpperCase();
            if(gateType.equals("COMPOUNDGATE")){
                CompoundGate CG;
                if (AbstractGateFactory.isRegistered(source.getElName())){
                    CG = (CompoundGate) AbstractGateFactory.create(source.getElName());
                    CompoundElement drawable = new CompoundElement(source.getElName(), canvas, source.getPosition(), CG);

                    if (source.getSwap()) {
                        drawable.toggleIOSide();
                    }
                    canvas.addElement(drawable);
                } else {
                    String message = source.getMessage();
                    String name = source.getElName();

                    HashMap<Integer, Compoundable> gatesTMP = new HashMap<>();
                    HashMap<Integer, JSONAvailable> schemaTMP = new HashMap<>();


                    for (JSONAvailable logicPart: source.getLogic()) {
                        Integer idTMP = logicPart.getHashCode();
                        String gateTypeTMP = logicPart.getGateType();
                        UiAvailable elementTMP = AbstractGateFactory.create(gateTypeTMP);
                        if(Objects.equals(gateTypeTMP, "VirtualIO")){
                            ((VirtualIO) elementTMP).name = logicPart.getElName();
                        }

                        schemaTMP.put(idTMP,logicPart);
                        gatesTMP.put(idTMP,(Compoundable) elementTMP);
                    }

                    connectElements(new HashMap<>(gatesTMP), schemaTMP);
                    CompoundGate compoundGate = new CompoundGate(name, message, new HashSet<>(gatesTMP.values()));

                    canvas.registerCompoundGate(compoundGate.name, compoundGate.message, compoundGate);

                    CG = (CompoundGate) AbstractGateFactory.create(compoundGate.name);
                    CompoundElement drawable = new CompoundElement(compoundGate.name, canvas, source.getPosition(), CG);
                    if (source.getSwap()) {
                        drawable.toggleIOSide();
                    }
                    canvas.addElement(drawable);
                }

                for (JSONAvailable logicPart: source.getLogic()) {
                    if(Objects.equals(logicPart.getGateType(), "VirtualIO")){
                        UiAvailable el = (UiAvailable) CG.input(logicPart.getElName());

                        if (el == null) {
                            el = (UiAvailable) CG.output(logicPart.getElName());
                        }

                        gates.put(logicPart.getHashCode(),  el);
                        schema.put(logicPart.getHashCode(),logicPart);
                    }
                }

            } else {
                element = AbstractGateFactory.create(gateType);
                if(Objects.equals(gateType, "VIRTUALIO")){
                    ((VirtualIO) element).name = source.getElName();
                }

                UiElement uiElement= new UiElement(gateType, canvas, source.getPosition(), element, source.getRotation());
                setSpecialProperty(uiElement,source);
                canvas.addElement(uiElement);

                gates.put(id, element);
                schema.put(id, source);
            }
        }
        connectElements(gates, schema);
        myReader.close();
    }

    /**
     * Read all saved CompoundGates in directory
     * @param directory Source directory
     * @param canvas Destination of CompoundGates canvas
     * @throws IOException when something wrong with path
     */
    public static void initCompoundGates(File directory, final Canvas canvas) throws IOException {
        if (directory == null || !directory.exists() || directory.isFile()) {
            directory.mkdir();
            return;
        }

        for(File file : Objects.requireNonNull(directory.listFiles())){
            readCompoundGateFromFile(file,canvas);
        }
    }
    /**
     * Read single CompoundGate from file
     * @param file Source of CompoundGate
     * @param canvas Destination canvas
     * @throws IOException when something wrong with path
     */
    public static void readCompoundGateFromFile(File file, final Canvas canvas) throws IOException {
        if (file == null || !file.exists() || file.isDirectory()) {
            canvas.showPopup("File does not exist or path is wrong");
            return;
        }

        Scanner myReader = new Scanner(file);
        String json = myReader.nextLine();
        JSONAvailable source = generateJSONAvailableFromJSON(json);

        String message = source.getMessage();
        String name = source.getElName();

        HashMap<Integer, Compoundable> gates = new HashMap<>();
        HashMap<Integer, JSONAvailable> schema = new HashMap<>();


        for (JSONAvailable logicPart: source.getLogic()) {
            Integer id = logicPart.getHashCode();
            String gateType = logicPart.getGateType();
            UiAvailable element = AbstractGateFactory.create(gateType);
            if(Objects.equals(gateType, "VirtualIO")){
                ((VirtualIO) element).name = logicPart.getElName();
            }

            schema.put(id,logicPart);
            gates.put(id, (Compoundable) element);
        }

        connectElements(new HashMap<>(gates), schema);

        try {
            CompoundGate compoundGate = new CompoundGate(name, message, new HashSet<>(gates.values()));

            if(!AbstractGateFactory.isRegistered(compoundGate.name))
                canvas.registerCompoundGate(compoundGate.name, compoundGate.message, compoundGate);
        } catch (Exception e) {
            canvas.showPopup("CompoundGate cannot have more than one gate with the same name");
        }

        myReader.close();
    }

    /**
     * Generate JSONAvailable object from JSON string
     * @param jsonString Source string
     * @return  JSONAvailable object
     * @throws IOException Throw usually when fields don't match
     */
    private static JSONAvailable generateJSONAvailableFromJSON(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString,JSONAvailable.class);
    }

    /**
     * Connect elements in HashMap using schema from other HashMap
     * @param gates HashMap of elements to connect
     * @param schema HashMap of JSONAvailable representation of schema
     */
    private static void connectElements(final HashMap<Integer, UiAvailable> gates, final HashMap<Integer,JSONAvailable> schema){
        for(Map.Entry<Integer, JSONAvailable> entry : schema.entrySet()) {
            JSONAvailable value = entry.getValue();
            UiAvailable gate = gates.get(entry.getKey());
            if(gate instanceof CompoundGate){
                for(Compoundable compoundable:((CompoundGate) gate).getGates())
                    for (Integer hashCode: value.getOutputs())
                        if(gates.containsKey(hashCode))
                            compoundable.connection((SignalReceiver) gates.get(hashCode));
            }else{
                for (Integer hashCode: value.getOutputs())
                    if(gates.containsKey(hashCode))
                        gate.connection((SignalReceiver) gates.get(hashCode));
            }
        }
    }

    /**
     * Set special property of Output element
     * @param element element to change
     * @param source source of data
     */
    private static void setSpecialProperty(UiElement element, JSONAvailable source){
        if(element.getGate() instanceof LED)
            element.color = new Color(source.getColor());

        if(element.getGate() instanceof Speaker)
            ((Speaker) element.getGate()).setFrequency(source.getFreq());

        if(element.getGate() instanceof Switch)
            if(!source.getState())
                ((Switch) element.getGate()).react();

        if(element.getGate() instanceof Clock)
            ((Clock) element.getGate()).setIntervals(source.getIntervalOn(),source.getIntervalOff());

        if(element.getGate() instanceof Delay)
            ((Delay) element.getGate()).setDelay(source.getDelay());
    }
}
