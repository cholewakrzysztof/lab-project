package pl.edu.pwr.student.UI;

import pl.edu.pwr.student.Examples.Register;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.Delay;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.VirtualIO;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.IO.Input.Clock;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.BasicReceiver;
import pl.edu.pwr.student.IO.Output.LED;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.IO.Output.Speaker;
import pl.edu.pwr.student.UI.Blocks.CompoundElement;
import pl.edu.pwr.student.UI.Blocks.Drawable;
import pl.edu.pwr.student.UI.Blocks.UiElement;
import pl.edu.pwr.student.UI.Buttons.*;
import pl.edu.pwr.student.UI.Creator.GateCreator;
import pl.edu.pwr.student.Utility.FileManagement.DataReader;
import pl.edu.pwr.student.Utility.FileManagement.DataWriter;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;
import uibooster.UiBooster;
import uibooster.components.WaitingDialog;
import uibooster.model.Form;
import uibooster.model.ListElement;
import uibooster.model.UiBoosterOptions;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Class representing the Processing canvas.
 * Handles drawing and interactions with elements.
 */
public class Canvas extends PApplet {

    /**
     * Form with the list of gates.
     */
    private Form form;

    /**
     * The UiBooster object used for UI interactions.
     */
    private final UiBooster booster;

    /**
     * The set of all elements on the canvas.
     */
    private final List<Drawable> elements = new ArrayList<>();

    /**
     * The list of all buttons.
     */
    private final ArrayList<Button> buttons = new ArrayList<>();

    /**
     * The selected element on the canvas.
     */
    private Drawable selectedElement = null;

    /**
     * The selected element on the canvas.
     */
    private UiAvailable connecting = null;

    /**
     * Holds the offset of canvas.
     */
    private final PVector offset = new PVector(0, 0);

    /**
     * Holds the temporary offset of canvas, used in math.
     */
    private final PVector tempOffset = new PVector(0, 0);

    /**
     * Holds the temporary starting mouse position, used in math.
     */
    private PVector startingMousePosition;

    private ArrayList<ListElement> gatesList;

    /**
     * Constructor
     */
    public Canvas () {
        try {
            booster = new UiBooster(UiBoosterOptions.Theme.DARK_THEME);

            WaitingDialog dialog = booster.showWaitingDialog("Starting", "Please wait");

            GateCreator.initGates();
            initForm();

            // Set up the canvas
            String[] processingArgs = {"Gates-Simulation"};
            this.runSketch(processingArgs);

            ShapeLoader.loadShapes(this);

            initButtons();

            windowResizable(true);

            dialog.setMessage("Ready");
            dialog.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method called when user wants to exit.
     * Displays a dialog asking if the user wants to save their work.
     */
    @Override
    public void exit() {
        if (elements.isEmpty()) {
            super.exit();
        } else {
            booster.showConfirmDialog(
                "Do you want to save your work?",
                "Exiting",
                () -> {
                    try {
                        DataWriter.saveToFile(this, getDirectory());
                        super.exit();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                },
                super::exit);
        }
    }

    /**
     * Method setting up canvas
     */
    @Override
    public void settings() {
        size(1000, 1000);

        buildForm();

        showPopup("Started");
    }

    /**
     * Method called when canvas is ready and starts drawing
     */
    @Override
    public void draw() {
        background(255);

        for (int i = 0; i < elements.size(); i++) {
            elements.get(i).run();
        }

        if (elements.isEmpty()) {
            fill(0);
            textAlign(CENTER);
            textSize(32);
            text(
                    "Select gate and place it",
                    width / 2f,
                    height / 2f
            );
        }

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).run();
        }
    }

    /**
     * Method called when user presses mouse
     */
    @Override
    public void mousePressed() {
        for (int i = 0; i < buttons.size(); i++) {
            if(buttons.get(i).over(new PVector(mouseX, mouseY))) {
                try {
                    buttons.get(i).click();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
        }


        switch (CanvasState.getState()) {
            case 0 -> {
                for (Drawable g : elements) {
                    if (g.over(new PVector(mouseX, mouseY))) {
                        selectedElement = g;
                        break;
                    }
                }
            }
            case 1 -> {
                ListElement selected = (ListElement) form.getByLabel("Select Gate").getValue();
                if (selected != null) {
                    PVector mouse = new PVector(
                            mouseX / ShapeLoader.scale - ShapeLoader.size/2f + offset.x,
                            mouseY / ShapeLoader.scale - ShapeLoader.size/2f + offset.y
                    );

                    try {
                        UiAvailable temp = GateCreator.create(selected.getTitle());
                        if (temp instanceof CompoundGate){
                            elements.add(new CompoundElement(selected.getTitle(), this, mouse, temp));
                        } else {
                            elements.add(new UiElement(selected.getTitle(), this, mouse, temp));
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            case 2 -> {
                for (Drawable g : elements) {
                    if (g.over(new PVector(mouseX, mouseY))) {
                        CanvasState.setState(4);
                        connecting = g.getOutput();
                        if (connecting == null || connecting instanceof CompoundGate || connecting instanceof BasicReceiver) {
                            CanvasState.lastState();
                            connecting = null;
                            showPopup("This gate cannot have output");
                        }
                        break;
                    }
                }
            }
            case 3 -> {
                for (Drawable g : elements) {
                    if (g.over(new PVector(mouseX, mouseY))) {
                        g.getGate().fullDisconnect();
                        elements.remove(g);
                        break;
                    }
                }
            }
            case 4 -> {
                for (Drawable g : elements) {
                    if (g.over(new PVector(mouseX, mouseY))) {
                        if (connecting != null) {
                            try {
                                connecting.connection((SignalReceiver) g.getInput());
                            } catch (Exception e) {
                                showPopup("This gate cannot have input");
                            }
                            CanvasState.lastState();
                            connecting = null;
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * Method called when user clicks mouse
     */
    @Override
    public void mouseClicked(){
        if (CanvasState.getState() == 0) {
            for (Drawable g : elements) {
                if(g.over(new PVector(mouseX, mouseY))){
                    if (g.getGate() instanceof Switch) {
                        ((Switch) g.getGate()).react();
                    } else if (g.getGate() instanceof LED){
                        g.color = booster.showColorPicker("Choose color of LED", "Color picking");
                    } else if (g.getGate() instanceof Clock){
                        Form temp = booster.createForm("Clock")
                                .addSlider("On time", 100, 10000, 1000, 10000, 1000)
                                .addSlider("Off time", 100, 10000, 1000, 10000, 1000)
                                .show();
                        ((Clock) g.getGate()).setIntervals(
                                temp.getByLabel("On time").asInt(),
                                temp.getByLabel("Off time").asInt()
                        );
                    } else if (g.getGate() instanceof Delay){
                        Form temp = booster.createForm("Delay")
                                .addSlider("Delay time", 100, 10000, 1000, 10000, 1000)
                                .show();
                        ((Delay) g.getGate()).setDelay(
                                temp.getByLabel("Delay time").asInt()
                        );
                    } else if (g.getGate() instanceof Speaker) {
                        Form temp = booster.createForm("SPEAKER")
                                .addSlider("Frequency", 100, 10000, 200, 10000, 1000)
                                .show();
                        ((Speaker) g.getGate()).setFrequency(
                                temp.getByLabel("Frequency").asInt()
                        );
                    } else if (g.getGate() instanceof VirtualIO) {
                        Form temp = booster.createForm("NAME")
                                .addTextArea("Name", ((VirtualIO)g.getGate()).name)
                                .show();
                        ((VirtualIO) g.getGate()).setName(
                                temp.getByLabel("Name").asString()
                        );
                    } else if (g.getGate() instanceof CompoundGate) {
                        booster.showConfirmDialog(
                            "Swap Inputs with Outputs",
                            "IO sides",
                            () -> ((CompoundElement)g).toggleIOSide(),
                            ()->{}
                        );
                    }
                }
            }
        }
    }

    /**
     * Method called when user drags mouse
     */
    @Override
    public void mouseDragged() {
        if (CanvasState.getState() == 0) {
            if (selectedElement != null) {
                selectedElement.updatePosition (new PVector(
                    mouseX / ShapeLoader.scale - ShapeLoader.size/2f + offset.x,
                    mouseY / ShapeLoader.scale - ShapeLoader.size/2f + offset.y
                ));
            } else {
                if (startingMousePosition == null) {
                    startingMousePosition = new PVector(mouseX, mouseY);
                } else {
                    offset.x = tempOffset.x + (startingMousePosition.x - mouseX) / ShapeLoader.scale;
                    offset.y = tempOffset.y + (startingMousePosition.y - mouseY) / ShapeLoader.scale;
                }
            }
        }
    }

    /**
     * Method called when user releases mouse
     */
    @Override
    public void mouseReleased() {
        if (CanvasState.getState() == 0) {
            selectedElement = null;
            if (startingMousePosition != null){
                tempOffset.x = offset.x;
                tempOffset.y = offset.y;
                startingMousePosition = null;
            }
        }
    }

    /**
     * Method called when user rotates mouse wheel
     */
    @Override
    public void mouseWheel(MouseEvent event) {
        for (Drawable g : elements) {
            if (g.over(new PVector(mouseX, mouseY))) {
                g.rotation(event.getCount());
                return;
            }
        }
        ShapeLoader.scale(event.getCount());
    }

    /**
     * Hides form
     */
    public void hideForm() {
        form.hide();
    }

    /**
     * Shows form
     */
    public void showForm() {
        form.show();
    }

    /**
     * Gets set of all elements
     * @return list of elements
     */
    public List<Drawable> getElements() {
        return elements;
    }

    public void addElement(Drawable element) {
        elements.add(element);
    }

    /**
    * Gets offset of the canvas
     * @return offset
    */
    public PVector getOffset() {
        return offset;
    }

    /**
     * Gets file to save to or load from
     * @return file
     */
    public File getFile() {
        return booster.showFileSelection("Get file to load from: .gss", "gss");
    }

    /**
     * Gets file to save to or load from
     * @return file
     */
    public File getDirectory() {
        return booster.showDirectorySelection();
    }

    /**
     * Shows popup with message
     *
     * @param message - String to show
     */
    public void showPopup(String message) {
        booster.createNotification(
            message,
            "Gates-Simulation"
        );
    }

    public void registerCompoundGate(String name, String message, CompoundGate gate) {
        gatesList.add(new ListElement(name, message, ""));
        GateCreator.registerGate(name, gate);
        if (form != null)
            buildForm();
    }

    private void buildForm(){
        form = booster
            .createForm("Gates")
            .addList("Select Gate", gatesList)
            .run()
            .hide();
    }

    private void initForm(){
        try {
            gatesList = new ArrayList<>();
            gatesList.add(new ListElement("AND", "Logical Conjunction", ""));
            gatesList.add(new ListElement("NAND", "Logical Alternative denial", ""));
            gatesList.add(new ListElement("OR", "Logical Disjunction", ""));
            gatesList.add(new ListElement("NOR", "Logical Joint denial", ""));
            gatesList.add(new ListElement("XOR", "Logical Exclusive or", ""));
            gatesList.add(new ListElement("XNOR", "Logical Biconditional", ""));
            gatesList.add(new ListElement("NOT", "Inverter", ""));
            gatesList.add(new ListElement("SPEAKER", "Speaker", ""));
            gatesList.add(new ListElement("LED", "Diode", ""));
            gatesList.add(new ListElement("SWITCH", "1 or 0 state", ""));
            gatesList.add(new ListElement("CLOCK", "Changes states", ""));
            gatesList.add(new ListElement("DELAY", "Buffer", ""));
            gatesList.add(new ListElement("VIRTUALIO", "Input or output for CompoundGate", ""));
            registerCompoundGate("DLatch", "", Register.createDLatch());
            registerCompoundGate("Register", "", Register.createRegister());
            registerCompoundGate("SRLatch", "", Register.createSRLatch());
            registerCompoundGate("4bitRegister", "", Register.create4bitRegister());
            registerCompoundGate("ETDFlipFlop", "", Register.createETDFlipFlop());

            DataReader.initCompoundGates(new File("gates"), this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initButtons(){
        buttons.add(new InteractionButton(this));
        buttons.add(new CreateButton(this));
        buttons.add(new ConnectButton(this));
        buttons.add(new DeleteButton(this));
        buttons.add(new SaveButton(this));
        buttons.add(new LoadButton(this));
        buttons.add(new AddButton(this));
    }

    public Form saveCompoundGateDialog(){
        return booster.createForm("Compound Gate Creator")
                .addTextArea("Name of the gate", 1)
                .addTextArea("Message for the gate", 1)
                .show();
    }

    /**
     * Clear all HashSets of canvas
     */
    public void clear(){
        elements.clear();
    }
}
