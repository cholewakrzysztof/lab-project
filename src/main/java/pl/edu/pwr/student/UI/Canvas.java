package pl.edu.pwr.student.UI;

import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.*;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.Delay;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.NOT;
import pl.edu.pwr.student.IO.Input.Clock;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.IO.Output.Speaker;
import pl.edu.pwr.student.UI.Buttons.*;
import pl.edu.pwr.student.Utility.FileManagement.DataWriter;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;
import uibooster.UiBooster;
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
     * The state of the canvas. It can have the following values:
     * 0 - interacting with elements
     * 1 - creating new elements
     * 2 - adding or removing a new output
     * 3 - deleting elements
     * 4 - connecting elements.
     */
    private int state;

    /**
     * The last state of the canvas.
     */
    private int lastState;

    /**
     * The UiBooster object used for UI interactions.
     */
    private final UiBooster booster;

    /**
     * The set of all elements on the canvas.
     */
    private final Set<UiElement> elements = new HashSet<>();

    /**
     * The list of all buttons.
     */
    private final ArrayList<Button> buttons = new ArrayList<>();

    /**
     * The selected element on the canvas.
     */
    private UiElement selectedElement = null;

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

    /**
     * Constructor
     */
    public Canvas () {
        booster = new UiBooster(UiBoosterOptions.Theme.DARK_THEME);

        // Set up the canvas
        String[] processingArgs = {"Gates-Simulation"};
        this.runSketch(processingArgs);

        ShapeLoader.loadShapes(this);

        // Initialize the list of buttons
        buttons.add(new InteractionButton(this));
        buttons.add(new CreateButton(this));
        buttons.add(new ConnectButton(this));
        buttons.add(new DeleteButton(this));
        buttons.add(new SaveButton(this));
        buttons.add(new LoadButton(this));
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
                            DataWriter.saveToFile(this,getDirectory());
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
        //TODO: make it added automatically (created new gates by user are now a problem)
        form = booster
                .createForm("Gates")
                .addList("Select Gate",
                        new ListElement("AND", null,"/icon/AND.png"),
                        new ListElement("NAND", null,"/icon/NAND.png"),
                        new ListElement("OR", null, "/icon/OR.png"),
                        new ListElement("NOR", null,"/icon/NOR.png"),
                        new ListElement("XOR", null,"/icon/XOR.png"),
                        new ListElement("XNOR", null,"/icon/XNOR.png"),
                        new ListElement("NOT", null, "/icon/NOT.png"),
                        new ListElement("SPEAKER", null, "/icon/SPEAKER.png"),
                        new ListElement("LED", null, "/icon/LED.png"),
                        new ListElement("SWITCH", null, "/icon/SWITCH-FALSE.png"),
                        new ListElement("CLOCK", null, "/icon/CLOCK.png"),
                        new ListElement("DELAY", null, "/icon/DELAY.png")
                ).run().hide();

        showPopup("Started");
    }

    /**
     * Method called when canvas is ready and starts drawing
     */
    @Override
    public void draw() {
        background(255);

        for (UiElement g : elements) {
            g.run();
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


        switch (state) {
            case 0 -> {
                for (UiElement g : elements) {
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
                        create(selected.getTitle(), mouse);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            case 2 -> {
                for (UiElement g : elements) {
                    if (g.over(new PVector(mouseX, mouseY))) {
                        setState(4);
                        selectedElement = g;
                        break;
                    }
                }
            }
            case 3 -> {
                for (UiElement g : elements) {
                    if (g.over(new PVector(mouseX, mouseY))) {
                        g.uiElem.fullDisconnect();
                        elements.remove(g);
                        break;
                    }
                }
            }
            case 4 -> {
                for (UiElement g : elements) {
                    if (g.over(new PVector(mouseX, mouseY))) {
                        if (selectedElement != null) {
                            try {
                                selectedElement.uiElem.connection((SignalReceiver) g.uiElem);
                                selectedElement = null;
                                state = lastState;
                            } catch (Exception e) {
                                booster.createNotification(
                                        "You cannot connect this to that",
                                        "Gates-Simulation");
                            }
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
        if (state == 0) {
            for (UiElement g : elements) {
                if(g.over(new PVector(mouseX, mouseY))){
                    if (g.uiElem instanceof Switch) {
                        ((Switch) g.uiElem).toggle();
                    } else if (g.uiElem instanceof LED){
                        g.color = booster.showColorPicker("Choose color of LED", "Color picking");
                    } else if (g.uiElem instanceof Clock){
                        Form temp = booster.createForm("Clock")
                                .addSlider("On time", 100, 10000, 1000, 10000, 1000)
                                .addSlider("Off time", 100, 10000, 1000, 10000, 1000)
                                .show();
                        ((Clock) g.uiElem).setIntervals(
                                temp.getByLabel("On time").asInt(),
                                temp.getByLabel("Off time").asInt()
                        );
                    } else if (g.uiElem instanceof Delay){
                        Form temp = booster.createForm("Delay")
                                .addSlider("Delay time", 100, 10000, 1000, 10000, 1000)
                                .show();
                        ((Delay) g.uiElem).setDelay(
                                temp.getByLabel("Delay time").asInt()
                        );
                    } else if (g.uiElem instanceof Speaker) {
                        Form temp = booster.createForm("SPEAKER")
                                .addSlider("Frequency", 100, 10000, 200, 10000, 1000)
                                .show();
                        ((Speaker) g.uiElem).setFrequency(
                                temp.getByLabel("Frequency").asInt()
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
        if (state == 0) {
            if (selectedElement != null) {
                selectedElement.position = new PVector(
                        mouseX / ShapeLoader.scale - ShapeLoader.size/2f + offset.x,
                        mouseY / ShapeLoader.scale - ShapeLoader.size/2f + offset.y
                );
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
        if (state == 0) {
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
        if (event.getCount() > 0) {
            ShapeLoader.decrementScale();
        } else {
            ShapeLoader.incrementScale();
        }
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
     * Sets state of the canvas
     * @param state state
     */
    public void setState(int state) {
        lastState = this.state;
        this.state = state;
    }

    /**
     * Gets state of the canvas
     * @return state
     */
    public int getState() {
        return state;
    }

    /**
     * Gets set of all elements
     * @return set of elements
     */
    public Set<UiElement> getElements() {
        return elements;
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
        return booster.showFileSelection("Get file to save to: .gss", "gss");
    }

    /**
     * Gets file to save to or load from
     * @return file
     */
    public File getDirectory() {
        return booster.showDirectorySelection();
    }

    /**
     * Creates gate, saves it to proper place and performs necessary actions
     *
     * @param type - type of gate
     * @param mouse - vector of mouse
     */
    public UiAvailable create(String type, PVector mouse) throws Exception {
        UiAvailable temp;
        switch (type) {
            case "AND" -> {
                temp =  new AND();
                elements.add(new UiElement(type, this, mouse, temp));
            }
            case "NAND" -> {
                temp = new NAND();
                elements.add(new UiElement(type, this, mouse, temp));
            }
            case "OR" -> {
                temp = new OR();
                elements.add(new UiElement(type, this, mouse, temp));
            }
            case "NOR" -> {
                temp = new NOR();
                elements.add(new UiElement(type, this, mouse, temp));
            }
            case "XOR" -> {
                temp = new XOR();
                elements.add(new UiElement(type, this, mouse, temp));
            }
            case "XNOR" -> {
                temp = new XNOR();
                elements.add(new UiElement(type, this, mouse, temp));
            }
            case "NOT" -> {
                temp = new NOT();
                elements.add(new UiElement(type, this, mouse, temp));
            }
            case "SPEAKER" -> {
                temp = new Speaker();
                elements.add(new UiElement(type, this, mouse, temp));
            }
            case "LED" -> {
                temp = new LED("", 0);
                ((LED)temp).toggle();
                elements.add(new UiElement(type, this, mouse, temp));
            }
            case "SWITCH" -> {
                temp = new Switch();
                ((Switch)temp).toggle();
                elements.add(new UiElement(type, this, mouse, temp));
            }
            case "CLOCK" -> {
                temp = new Clock(1000, 1000);
                ((Clock)temp).toggle();
                elements.add(new UiElement(type, this, mouse, temp));
            }
            case "DELAY" -> {
                temp = new Delay(1000);
                elements.add(new UiElement(type, this, mouse, temp));
            }
            default -> throw new Exception("Bad name of JSONAvaible");

        }

        return temp;
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
}
