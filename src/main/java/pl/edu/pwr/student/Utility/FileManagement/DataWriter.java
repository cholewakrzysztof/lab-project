package pl.edu.pwr.student.Utility.FileManagement;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.UI.Blocks.Drawable;
import pl.edu.pwr.student.UI.Canvas;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.HashSet;

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
        for (Drawable uiElement: canvas.getElements()) {
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
        File file = new File(directory.getAbsoluteFile() + "\\" + name + ".gss");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));

        if(name.length()==0){
            name = "Custom_gate";
        }
        if(message.length()==0)
            message = "Custom_gate_message";

        HashSet<Compoundable> basicGates = new HashSet<>();
        HashSet<CompoundGate> compGates = new HashSet<>();

        for (Drawable drawable:canvas.getElements()) {
            if(drawable.getGate()!=null)
                if(drawable.getGate().getClass()== CompoundGate.class){
                    compGates.add((CompoundGate) drawable.getGate());
                }
                else{
                    basicGates.add((Compoundable) drawable.getGate());
                }
        }

        CompoundGate compoundGate = new CompoundGate(name,message,basicGates,compGates);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(new JSONAvailable(compoundGate));
        bufferedWriter.write(json);
        bufferedWriter.newLine();


        bufferedWriter.close();
        canvas.clear();
        DataReader.readCompoundGateFromFile(file, canvas);
    }

    /**
     * @param element UI Element to convert
     * @return JSON string representation of single UI Element
     * @throws IOException something wrong with UI element
     */
    public static String generateJSONFromUIElement(Drawable element) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(new JSONAvailable(element));
    }



}
