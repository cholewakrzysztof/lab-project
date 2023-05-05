package pl.edu.pwr.student.Utility.FileManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;

public class FileReader {

    public static void readFromFile(){

    }
    public static UiElement generateUIElementFormJSON(String jsonString, Canvas canvas) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONAvaible jsonAvaible = objectMapper.readValue(jsonString,JSONAvaible.class);
        return new UiElement(jsonAvaible,canvas);
    }
}
