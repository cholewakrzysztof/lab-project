package pl.edu.pwr.student.Utility.FileManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.IO.Output.*;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.Creator.GateCreator;
import pl.edu.pwr.student.UI.UiAvailable;
import pl.edu.pwr.student.UI.UiElement;

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

        canvas.clear();
        HashMap<Integer, Compoundable> gates = new HashMap<>();
        HashMap<Integer,JSONAvailable> schema = new HashMap<>();

        Scanner myReader = new Scanner(file);
        while(myReader.hasNext()){
            String json = myReader.next();
            JSONAvailable source = generateJSONAvailableFromJSON(json);

            Compoundable element =(Compoundable) GateCreator.create(source.getElName());
            canvas.getElements().add(new UiElement(source.getElName(), canvas, source.getPosition(), element));

            Integer id = source.getHashCode();
            gates.put(id, element);
            schema.put(id, source);
        }
        connectElements(gates,schema);
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

        for(File file : Objects.requireNonNull(directory.listFiles()))
            readCompoundGateFromFile(file,canvas);
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

        HashMap<Integer, Compoundable> gates = new HashMap<>();
        HashMap<Integer,JSONAvailable> schema = new HashMap<>();

        Scanner myReader = new Scanner(file);
        String message = myReader.next();
        while(myReader.hasNext()){
            String json = myReader.next();
            JSONAvailable source = generateJSONAvailableFromJSON(json);

            Integer id = source.getHashCode();
            gates.put(id, (Compoundable) GateCreator.create(source.getElName()));
            schema.put(id, source);
        }

        connectElements(gates,schema);

        CompoundGate compoundGate = new CompoundGate(new HashSet<>(gates.values()));
        String name = file.getName().substring(0,file.getName().length()-4);
        canvas.registerCompoundGate(name,message, compoundGate);

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
    private static void connectElements(final HashMap<Integer, Compoundable> gates, final HashMap<Integer,JSONAvailable> schema){
        for(Map.Entry<Integer, JSONAvailable> entry : schema.entrySet()) {
            JSONAvailable value = entry.getValue();
            UiAvailable gate = gates.get(entry.getKey());
            for (Integer hashCode: value.getOutputs()) {
                for (Map.Entry<Integer, Compoundable> candidate : gates.entrySet()) {
                    if(Objects.equals(hashCode, candidate.getKey())){
                        gate.connection((SignalReceiver) candidate.getValue());
                    }
                }
            }
        }
    }
}
