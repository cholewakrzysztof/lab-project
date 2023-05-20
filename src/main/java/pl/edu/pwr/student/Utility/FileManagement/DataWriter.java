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
     * Saves the current project to a file.
     *
     * @param canvas source of UI elements to save
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
     * Saves a {@link pl.edu.pwr.student.Gates.CompoundGate} element to a file.
     *
     * @param canvas source of UI elements to save
     * @param name name of the {@link pl.edu.pwr.student.Gates.CompoundGate} to save
     * @param message description of the element once loaded into the program
     * @throws IOException thrown when something has gone wrong during the saving process
     */
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
            bufferedWriter.write(DataWriter.generateJSONfromUIElement(uiElement));
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
    public static String generateJSONfromUIElement(Drawable element) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(new JSONAvailable(element));
    }
}
