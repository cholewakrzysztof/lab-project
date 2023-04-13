package pl.edu.pwr.student.UI;

import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.Gates.Compoundable;
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
 * Class representing Processing canvas
 */
public class Canvas extends PApplet {
    /**
     * Form with list of gates
     */
    public Form form;

    /**
     * State of canvas
     * 0 - interacting with elements
     * 1 - creating new elements
     * 2 - Add/Remove new output
     * 3 - deleting elements
     * 4 - connecting elements
     */
    public int state;

    /**
     * Last state of canvas
     */
    public int lastState;

    /**
     * UiBooster object
     */
    public UiBooster booster;

    /**
     * List of all elements on canvas
     */
    public Set<UiElement> elements = new HashSet<UiElement>();

    /**
     * List of all basic gates
     */
    public HashSet<Compoundable> basicGates;

    /**
     * List of all compound gates
     */
    private HashSet<CompoundGate> compoundGates;

    /**
     * List of all saved compound gates
     */
    private HashMap<String, CompoundGate> savedCompoundGates;

    /**
     * List of all user inputs
     */
    public HashSet<SignalSender> userInputs;

    /**
     * List of all system outputs
     */
    public HashSet<SignalReceiver> systemOutputs;

    /**
     * List of all buttons
     */
    public final ArrayList<UiElement> buttons = new ArrayList<>();

    /**
     * Selected element
     */
    public UiElement selectedElement = null;

    /**
     * Constructor
     * @param basicGates list of all basic gates
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

        String[] processingArgs = {"Gates-Simulation"};
        this.runSketch(processingArgs);

        ShapeLoader.loadShapes(this);

        buttons.add(new InteractionButton(this));
        buttons.add(new CreateButton(this));
        buttons.add(new ConnectButton(this));
        buttons.add(new DeleteButton(this));
    }

    /**
     * Method called when user wants to exit
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
     * handles adding new gates
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
