package pl.edu.pwr.student.Utility.FileManagement;


import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;

import java.io.IOException;

/**
 * Class responsible for safe HashSet of UI elements to files
 */
public class FileWriter {
    /**
     * @param canva source of UI elements to safe
     * @param path path to place where file will be saved
     */
    public static void safeToFile(Canvas canva, String path){
        for (UiElement uiElement: canva.elements) {
        }
    }

    /**
     * @param element UI Element to convert
     * @return JSON string representation of single UI Element
     * @throws IOException
     */
    public static String generateJSONfromUIElement(UiElement element) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(new JSONAvaible(element));
    }


}
