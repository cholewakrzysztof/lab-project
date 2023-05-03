package pl.edu.pwr.student.UI;

import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.UI.Buttons.*;
import pl.edu.pwr.student.UI.Handlers.*;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PApplet;
import uibooster.UiBooster;
import uibooster.model.Form;

import java.util.*;

/**
 * Class representing the Processing canvas.
 * Handles drawing and interactions with elements.
 */
public class Canvas extends PApplet {
    /**
     * Form with the list of gates.
     */
    public Form form;

    /**
     * The state of the canvas. It can have the following values:
     * 0 - interacting with elements
     * 1 - creating new elements
     * 2 - adding or removing a new output
     * 3 - deleting elements
     * 4 - connecting elements.
     */
    public int state;

    /**
     * The last state of the canvas.
     */
    public int lastState;

    /**
     * The UiBooster object used for UI interactions.
     */
    public UiBooster booster;

    /**
     * The set of all elements on the canvas.
     */
    public Set<UiElement> elements = new HashSet<>();

    /**
     * The set of all basic gates.
     */
    public HashSet<Compoundable> basicGates;

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
    public HashSet<SignalSender> userInputs;

    /**
     * The set of all system outputs.
     */
    public HashSet<SignalReceiver> systemOutputs;

    /**
     * The list of all buttons.
     */
    public final ArrayList<Button> buttons = new ArrayList<>();

    /**
     * The selected element on the canvas.
     */
    public UiElement selectedElement = null;

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
        this.basicGates = basicGates;
        this.compoundGates = compoundGates;
        this.savedCompoundGates = savedCompoundGates;
        this.userInputs = userInputs;
        this.systemOutputs = systemOutputs;

        booster = new UiBooster();

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
        SettingsHandler.settings(this);
    }

    /**
     * Method called when canvas is ready and starts drawing
     */
    public void draw() {
        DrawHandler.draw(this);
    }

    /**
     * Method called when user presses mouse
     */
    public void mousePressed() {
        MousePressedHandler.mousePressed(this);
    }

    /**
     * Method called when user clicks mouse
     */
    public void mouseClicked(){
        MouseClickedHandler.mouseClicked(this);
    }

    /**
     * Method called when user drags mouse
     */
    public void mouseDragged() {
        MouseDraggedHandler.mouseDragged(this);
    }

    /**
     * Method called when user releases mouse
     */
    public void mouseReleased() {
        MouseReleasedHandler.mouseReleased(this);
    }
}
