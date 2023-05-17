package pl.edu.pwr.student.Utility.FileManagement;


import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;

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
        for (UiElement uiElement: canvas.getElements()) {
            bufferedWriter.write(DataWriter.generateJSONfromUIElement(uiElement));
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    public static void safeToFileCompoundGate(Canvas canvas, File directory,String name, String message) throws IOException {
        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            canvas.showPopup("Directory does not exist or is not a directory");
            return;
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(directory.getAbsoluteFile() + "\\" + name + ".gss"));
        bufferedWriter.write(message);
        bufferedWriter.newLine();
        for (UiElement uiElement: canvas.getElements()) {
            bufferedWriter.write(DataWriter.generateJSONfromUIElement(uiElement));
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    /**
     * @param element UI Element to convert
     * @return JSON string representation of single UI Element
     * @throws IOException something wrong with UI element
     */
    public static String generateJSONfromUIElement(UiElement element) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(new JSONAvailable(element));
    }

}
