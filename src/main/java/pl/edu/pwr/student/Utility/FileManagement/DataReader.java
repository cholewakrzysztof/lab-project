package pl.edu.pwr.student.Utility.FileManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;

/**
 * Class responsible for reading data from files and generating HashSet of UI elements
 */
public class DataReader {
    /**
     *
     * @param path Path to file with JSON string of UI Elements
     * @param canvas Canva where all UI elements will be safe
     */
    public static void readFromFile(String path, Canvas canvas){

    }

    /**
     *
     * @param jsonString Raw JSON string of single JSONAvaible object
     * @param canvas Canva connected with UI Element
     * @return new UiElement
     * @throws Exception when something wrong with jsonString
     */
    public static UiElement generateUIElementFormJSON(String jsonString, Canvas canvas) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONAvaible jsonAvaible = objectMapper.readValue(jsonString,JSONAvaible.class);
        return new UiElement(jsonAvaible,canvas);
    }
}
