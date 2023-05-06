package pl.edu.pwr.student.UI;

import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.*;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.Delay;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.NOT;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.IO.Input.Clock;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.IO.Output.Speaker;
import pl.edu.pwr.student.UI.Buttons.*;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;
import uibooster.UiBooster;
import uibooster.model.Form;
import uibooster.model.ListElement;
import uibooster.model.UiBoosterOptions;

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
     * The set of all basic gates.
     */
    private final HashSet<Compoundable> basicGates;

    /**
     * The set of all compound gates.
     */
    private final HashSet<CompoundGate> compoundGates;

    /**
     * The map of all saved compound gates.
     */
    private final HashMap<String, CompoundGate> savedCompoundGates;

    /**
     * The set of all user inputs.
     */
    private final HashSet<SignalSender> userInputs;

    /**
     * The set of all system outputs.
     */
    private final HashSet<SignalReceiver> systemOutputs;

    /**
     * The list of all buttons.
     */
    private final ArrayList<Button> buttons = new ArrayList<>();

    /**
     * The selected element on the canvas.
     */
    private UiElement selectedElement = null;

    /**
     * The selected element on the canvas.
     */
    private final PVector offset = new PVector(0, 0);
    private final PVector tempOffset = new PVector(0, 0);

    /**
     * The selected element on the canvas.
     */
    private PVector startingMousePosition;



    /**
     * Constructor
     * @param basicGates list of all basic gates
     * @param compoundGates list of all compound gates
     * @param savedCompoundGates list of all saved compound gates
     * @param userInputs list of all user inputs
     * @param systemOutputs list of all system outputs
     */
    public Canvas (
        HashSet<Compoundable> basicGates,
        HashSet<CompoundGate> compoundGates,
        HashMap<String, CompoundGate> savedCompoundGates,
        HashSet<SignalSender> userInputs,
        HashSet<SignalReceiver> systemOutputs
    ) {
        booster = new UiBooster(UiBoosterOptions.Theme.DARK_THEME);

        this.basicGates = basicGates;
        this.compoundGates = compoundGates;
        this.savedCompoundGates = savedCompoundGates;
        this.userInputs = userInputs;
        this.systemOutputs = systemOutputs;

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
                        //TODO: saving
                        super.exit();
                    },
                    super::exit);
        }
    }

    /**
     * Method setting up canvas
     */
    public void settings() {
        size(1000, 1000);
        //TODO: make it added automatically (created new gates by user are now a problem)
        form = booster
                .createForm("Gates")
                .addList("Select Gate",
                        new ListElement("AND", null,""),
                        new ListElement("NAND", null,""),
                        new ListElement("OR", null, ""),
                        new ListElement("NOR", null,""),
                        new ListElement("XOR", null,""),
                        new ListElement("XNOR", null,""),
                        new ListElement("NOT", null, ""),
                        new ListElement("SPEAKER", null, ""),
                        new ListElement("LED", null, ""),
                        new ListElement("SWITCH", null, ""),
                        new ListElement("CLOCK", null, ""),
                        new ListElement("DELAY", null, "")
                ).run().hide();

        booster.createNotification(
                "Started",
                "Gates-Simulation");
    }

    /**
     * Method called when canvas is ready and starts drawing
     */
    public void draw() {
        background(255);

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).run();
        }

        for (UiElement g : elements) {
            g.run();
        }
        if (elements.isEmpty()) {
            fill(0);
            textAlign(CENTER);
            textSize(32);
            text("Select gate and place it", width / 2f, height / 2f);
        }
    }

    /**
     * Method called when user presses mouse
     */
    public void mousePressed() {
        for (int i = 0; i < buttons.size(); i++) {
            if(buttons.get(i).over(new PVector(mouseX, mouseY))) {
                buttons.get(i).click();
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
                            (mouseX+offset.x) / ShapeLoader.scale - ShapeLoader.size/2f,
                            (mouseY+offset.y) / ShapeLoader.scale - ShapeLoader.size/2f
                    );
                    //TODO: make it added automatically (created new gates by user are now a problem)
                    switch (selected.getTitle()) {
                        case "AND" -> {
                            AND temp = new AND();
                            basicGates.add(temp);
                            elements.add(new UiElement("AND", this, mouse, temp));
                        }
                        case "NAND" -> {
                            NAND temp = new NAND();
                            basicGates.add(temp);
                            elements.add(new UiElement("NAND", this, mouse, temp));
                        }
                        case "OR" -> {
                            OR temp = new OR();
                            basicGates.add(temp);
                            elements.add(new UiElement("OR", this, mouse, temp));
                        }
                        case "NOR" -> {
                            NOR temp = new NOR();
                            basicGates.add(temp);
                            elements.add(new UiElement("NOR", this, mouse, temp));
                        }
                        case "XOR" -> {
                            XOR temp = new XOR();
                            basicGates.add(temp);
                            elements.add(new UiElement("XOR", this, mouse, temp));
                        }
                        case "XNOR" -> {
                            XNOR temp = new XNOR();
                            basicGates.add(temp);
                            elements.add(new UiElement("XNOR", this, mouse, temp));
                        }
                        case "NOT" -> {
                            NOT temp = new NOT();
                            basicGates.add(temp);
                            elements.add(new UiElement("NOT", this, mouse, temp));
                        }
                        case "SPEAKER" -> {
                            Speaker temp = new Speaker();
                            systemOutputs.add(temp);
                            elements.add(new UiElement("SPEAKER", this, mouse, temp));
                        }
                        case "LED" -> {
                            LED temp = new LED("", 0);
                            temp.toggle();
                            systemOutputs.add(temp);
                            elements.add(new UiElement("LED", this, mouse, temp));
                        }
                        case "SWITCH" -> {
                            Switch temp = new Switch();
                            temp.toggle();
                            userInputs.add(temp);
                            elements.add(new UiElement("SWITCH", this, mouse, temp));
                        }
                        case "CLOCK" -> {
                            Clock temp = new Clock(1000, 1000);
                            temp.toggle();
                            userInputs.add(temp);
                            elements.add(new UiElement("CLOCK", this, mouse, temp));
                        }
                        case "DELAY" -> {
                            Delay temp = new Delay(1000);
                            userInputs.add(temp);
                            elements.add(new UiElement("DELAY", this, mouse, temp));
                        }
                    }
                }
            }
            case 2 -> {
                for (UiElement g : elements) {
                    if (g.over(new PVector(mouseX, mouseY))) {
                        lastState = state;
                        state = 4;
                        selectedElement = g;
                        break;
                    }
                }
            }
            case 3 -> {
                for (UiElement g : elements) {
                    if (g.over(new PVector(mouseX, mouseY))) {
                        g.uiElem.fullDisconnect();
                        basicGates.remove(g.uiElem);
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
    public void mouseClicked(){
        if (state == 0) {
            for (UiElement g : elements) {
                if(g.over(new PVector(mouseX, mouseY))){
                    switch (g.elName) {
                        case "SWITCH" -> ((Switch) g.uiElem).toggle();
                        case "LED" -> g.color = booster.showColorPicker("Choose color of LED", "Color picking");
                        case "CLOCK" -> {
                            Form temp = booster.createForm("Clock")
                                    .addSlider("On time", 100, 10000, 1000, 10000, 1000)
                                    .addSlider("Off time", 100, 10000, 1000, 10000, 1000)
                                    .show();
                            ((Clock) g.uiElem).setIntervals(
                                    temp.getByLabel("On time").asInt(),
                                    temp.getByLabel("Off time").asInt()
                            );
                        }
                        case "DELAY" -> {
                            Form temp = booster.createForm("Delay")
                                    .addSlider("Delay time", 100, 10000, 1000, 10000, 1000)
                                    .show();
                            ((Delay) g.uiElem).setDelay(
                                    temp.getByLabel("Delay time").asInt()
                            );
                        }
                        case "SPEAKER" -> {
                            Form temp = booster.createForm("SPEAKER")
                                    .addSlider("Frequency", 100, 10000, 200, 10000, 1000)
                                    .show();
                            ((Speaker) g.uiElem).setFrequency(
                                    temp.getByLabel("Frequency").asInt()
                            );
                        }
                    }
                    break;
                }
            }
        }
    }

    /**
     * Method called when user drags mouse
     */
    public void mouseDragged() {
        if (state == 0) {
            if (selectedElement != null) {
                selectedElement.position = new PVector(
                        (mouseX+offset.x) / ShapeLoader.scale - ShapeLoader.size/2f,
                        (mouseY+offset.y) / ShapeLoader.scale - ShapeLoader.size/2f
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
     */
    public void setState(int state) {
        lastState = state;
        this.state = state;
    }

    /**
     * Gets state of the canvas
     */
    public int getState() {
        return state;
    }

    /**
     * Gets set of all elements
     */
    public Set<UiElement> getElements() {
        return elements;
    }

    /**
    * Gets offset of the canvas
    */
    public PVector getOffset() {
        return offset;
    }
}
