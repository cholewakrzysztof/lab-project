package pl.edu.pwr.student.Utility.FileManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.VirtualIO;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.IO.Output.*;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.Creator.GateCreator;
import pl.edu.pwr.student.UI.UiAvailable;
import pl.edu.pwr.student.UI.Blocks.UiElement;

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

        while(myReader.hasNextLine()){
            JSONAvailable source = generateJSONAvailableFromJSON(myReader.nextLine());
            Integer id = source.getHashCode();
            GateCreator.initGates();
            String gateType = source.getGateType();
            UiAvailable element = GateCreator.create(gateType);
            if(Objects.equals(gateType, "VirtualIO")){
                ((VirtualIO) element).name = source.getElName();
            }
            canvas.getElements().add(new UiElement(gateType, canvas, source.getPosition(), element));
            gates.put(id, element);
            schema.put(id, source);
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
            canvas.showPopup("Directory does not exist or path is wrong");
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
        JSONAvailable jsonAvailable = generateJSONAvailableFromJSON(json);

        String message = jsonAvailable.getMessage();
        String name = jsonAvailable.getElName();

        HashMap<Integer, Compoundable> gates = new HashMap<>();
        HashMap<Integer, JSONAvailable> schema = new HashMap<>();


        for (JSONAvailable logicPart: jsonAvailable.getLogic()) {
            Integer id = logicPart.getHashCode();
            GateCreator.initGates();
            String gateType = logicPart.getGateType();
            UiAvailable element = GateCreator.create(gateType);
            if(Objects.equals(gateType, "VirtualIO")){
                ((VirtualIO) element).name = logicPart.getElName();
            }

            schema.put(id,logicPart);
            gates.put(id, (Compoundable) element);
        }

        connectElements(new HashMap<>(gates), schema);

        try {
            CompoundGate compoundGate = new CompoundGate(name, message, new HashSet<>(gates.values()));

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
            for (Integer hashCode: value.getOutputs()) {
                for (Map.Entry<Integer, UiAvailable> candidate : gates.entrySet()) {
                    if(Objects.equals(hashCode, candidate.getKey())){
                        gate.connection((SignalReceiver) candidate.getValue());
                    }
                }
            }
        }
    }
}
