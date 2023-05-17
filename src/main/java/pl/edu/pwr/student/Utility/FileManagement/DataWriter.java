package pl.edu.pwr.student.Utility.FileManagement;


import com.fasterxml.jackson.databind.ObjectMapper;
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
            bufferedWriter.write(DataWriter.generateJSONFromUIElement(uiElement));
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    public static void saveToFileCompoundGate(Canvas canvas, String name, String message) throws IOException {
        File directory = new File("gates");

        if (!directory.exists()) {
            directory.mkdir();
        }
        if(name.length()==0){
            name = "Custom_gate";
        }
        File file = new File(directory.getAbsoluteFile() + "\\" + name + ".gss");

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));

        if(message.length()!=0)
            bufferedWriter.write(message);
        else
            bufferedWriter.write("Custom_gate_message");
        bufferedWriter.newLine();

        for (UiElement uiElement: canvas.getElements()) {
            bufferedWriter.write(DataWriter.generateJSONFromUIElement(uiElement));
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        canvas.clear();
        DataReader.readCompoundGateFromFile(file, canvas);
    }

    /**
     * @param element UI Element to convert
     * @return JSON string representation of single UI Element
     * @throws IOException something wrong with UI element
     */
    public static String generateJSONFromUIElement(UiElement element) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(new JSONAvailable(element));
    }
}
