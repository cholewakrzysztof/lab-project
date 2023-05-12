package pl.edu.pwr.student.Utility.FileManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.pwr.student.IO.Output.*;
import pl.edu.pwr.student.UI.Canvas;
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

        Scanner myReader = new Scanner(file);

        clearBasicSets(canvas);

        HashMap<Integer,UiElement> elements = new HashMap<>();
        HashMap<Integer,JSONAvailable> jsonAvailableHashMap = new HashMap<>();

        while(myReader.hasNext()){
            String json = myReader.next();
            UiElement uiElement = generateUIElementFormJSON(json,canvas);
            JSONAvailable jsonAvailable = generateJSONAvailableFromJSON(json);

            Integer id = jsonAvailable.hashCode;
            elements.put(id,uiElement);
            jsonAvailableHashMap.put(id,jsonAvailable);

            canvas.create(uiElement.elName, uiElement.position);
        }

        for(Map.Entry<Integer, JSONAvailable> entry : jsonAvailableHashMap.entrySet()) {
            Integer key = entry.getKey();
            JSONAvailable value = entry.getValue();
            UiElement el = elements.get(key);
            for (Integer hashCode: value.outputs) {
                for (Map.Entry<Integer, UiElement> candidate : elements.entrySet()) {
                    if(Objects.equals(hashCode, candidate.getKey())){
                        el.uiElem.connection((SignalReceiver) candidate.getValue().uiElem);
                    }
                }
            }
        }
    }

    /**
     * Clear all HashSets of canvas
     * @param canvas Source canvas
     */
    private static void clearBasicSets(final Canvas canvas){
        canvas.userInputs.clear();
        canvas.systemOutputs.clear();
        canvas.basicGates.clear();
        canvas.getElements().clear();
    }

    /**
     *
     * @param jsonString Raw JSON string of single JSONAvailable object
     * @param canvas Canvas connected with UI Element
     * @return UiElement
     * @throws Exception Throw when JSONAvailable object cannot be created
     */
    public static UiElement generateUIElementFormJSON(String jsonString, Canvas canvas) throws Exception {
        return new UiElement(DataReader.generateJSONAvailableFromJSON(jsonString),canvas);
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
}
