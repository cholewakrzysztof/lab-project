package pl.edu.pwr.student.Utility.FileManagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.UI.Blocks.Drawable;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.Creator.AbstractGateFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.HashSet;
import java.util.Objects;

/**
 * Class responsible for safe HashSet of UI elements to files
 */
public class DataWriter {
    /**
     * Save current canvas into file
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

    /**
     * Create new file with schema of CompoundGate and then read this
     * @param canvas Source of elements
     * @param name Name of CompoundGate
     * @param message Message of CompoundGate
     * @throws IOException of manage files
     */
    public static void saveToFileCompoundGate(Canvas canvas, String name, String message) throws IOException {
        File directory = new File("gates");

        if (!directory.exists())
            directory.mkdir();

        if(name.length()==0)
            name = "Custom_gate";
        if(AbstractGateFactory.isRegistered(name))
            name+=System.currentTimeMillis();
        if(message.length()==0)
            message = "Custom_gate_message";

        HashSet<Compoundable> basicGates = new HashSet<>();
        HashSet<CompoundGate> compGates = new HashSet<>();

        for (Drawable drawable:canvas.getElements()) {
            if(Objects.equals(drawable.getGate().getClass(), CompoundGate.class))
                compGates.add((CompoundGate) drawable.getGate());
            else
                basicGates.add((Compoundable) drawable.getGate());
        }

        CompoundGate compoundGate = null;

        try {
            compoundGate = new CompoundGate(name, message, basicGates, compGates);
        } catch (Exception ignored) {}

        File file;
        BufferedWriter bufferedWriter;

        if (compoundGate != null) {
            file = new File(directory.getAbsoluteFile() + "\\" + name + ".gss");
            bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
        } else {
            throw new RuntimeException("CompoundGate VirtualIO names are not unique");
        }

        bufferedWriter.write(DataWriter.generateJSONFromCompoundGate(compoundGate));

        bufferedWriter.close();
        canvas.clear();
        DataReader.readCompoundGateFromFile(file, canvas);
    }

    /**
     * Generate JSON string representation of Drawable object
     * @param element Drawable element to convert
     * @return JSON string representation of single Drawable object
     * @throws JsonProcessingException when can't generate JSONAvailable
     */
    public static String generateJSONFromUIElement(Drawable element) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(new JSONAvailable(element));
    }

    /**
     * Generate JSON string representation of CompoundGate object
     * @param compoundGate CompoundGate to convert
     * @return JSON string representation of one CompoundGate
     * @throws JsonProcessingException when can't generate JSONAvailable
     */
    public static String generateJSONFromCompoundGate(CompoundGate compoundGate) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(new JSONAvailable(compoundGate));
    }
}
