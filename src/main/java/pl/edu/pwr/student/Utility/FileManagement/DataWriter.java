package pl.edu.pwr.student.Utility.FileManagement;


import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.pwr.student.UI.Blocks.Drawable;
import pl.edu.pwr.student.UI.Canvas;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

/**
 * Class responsible for safe HashSet of UI elements to files
 */
public class DataWriter {
    /**
     * @param canvas source of UI elements to safe
     * @param directory path to place where file will be saved
     */
    public static void saveToFile(Canvas canvas, File directory) throws IOException {
        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            canvas.showPopup("Directory does not exist or is not a directory");
            return;
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(directory.getAbsoluteFile() + "\\" + System.currentTimeMillis() + ".gss"));
        for (Drawable d: canvas.getElements()) {
            bufferedWriter.write(DataWriter.generateJSONfromUIElement(d));
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    /**
     * @param element UI Element to convert
     * @return JSON string representation of single UI Element
     * @throws IOException something wrong with UI element
     */
    public static String generateJSONfromUIElement(Drawable element) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(new JSONAvailable(element));
    }


}
