package pl.edu.pwr.student.UI;

import pl.edu.pwr.student.Gates.BasicGates.*;
import pl.edu.pwr.student.Gates.Compoundable;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.UI.Buttons.*;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PApplet;
import processing.core.PVector;
import uibooster.UiBooster;
import uibooster.model.Form;
import uibooster.model.ListElement;

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
     * 2 - moving elements
     * 3 - Add/Remove new output
     * 4 - deleting elements
     * 5 - connecting elements
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
    private HashSet<Compoundable> basicGates = new HashSet<>();

    /**
     * List of all buttons
     */
    private final ArrayList<UiElement> buttons = new ArrayList<>();

    /**
     * Selected element
     */
    public UiElement selectedElement = null;

    /**
     * Constructor
     * @param basicGates list of all basic gates
     */
    public Canvas (HashSet<Compoundable> basicGates) {
        this.basicGates = basicGates;

        String[] processingArgs = {"Gates-Simulation"};
        this.runSketch(processingArgs);

        //TODO: throws Cannot parse "currentcolor".
        buttons.add(new InteractionButton(this));
        buttons.add(new CreateButton(this));
        buttons.add(new EditButton(this));
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
            new UiBooster().showConfirmDialog(
                "Do you want to save your work?",
                "Exiting",
                () -> {
                    //TODO: saving
                    super.exit();
                },
                super::exit);
        }
    }

    public void settings() {
        booster = new UiBooster();
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
                new ListElement("NOT", null, "")
            ).run().hide();

        booster.createNotification(
            "Started",
            "Gates-Simulation");
    }

    public void draw() {
        background(255);

        for (UiElement g : buttons) {
            g.run();
        }

        for (UiElement g : elements) {
            g.run();
        }
        if (elements.isEmpty()) {
            fill(0);
            textAlign(CENTER);
            textSize(32);
            text("Select gate and place it", width/2, height/2);
        }
    }

    /**
     * Method called when user clicks mouse
     * handles adding new gates
     */
    public void mousePressed() {
        if (mousePressed && (mouseButton == LEFT)) {
            for (int i = 0; i < buttons.size(); i++) {
                if(buttons.get(i).over(new PVector(mouseX, mouseY))){
                    lastState = state;
                    state = i;
                    if (state == 1) {
                        form.show();
                    } else {
                        form.hide();
                    }
                    return;
                }
            }

            if(state == 1){
                ListElement selected = (ListElement) form.getByLabel("Select Gate").getValue();
                if(selected != null){
                    //TODO: make it added automatically (created new gates by user are now a problem)
                    switch (selected.getTitle()) {
                        case "AND" -> {
                            Compoundable temp = new AND();
                            basicGates.add(temp);
                            elements.add(new UiElement("AND", this, new PVector(mouseX, mouseY), temp));
                        }
                        case "NAND" -> {
                            Compoundable temp = new NAND();
                            basicGates.add(temp);
                            elements.add(new UiElement("NAND", this, new PVector(mouseX, mouseY), temp));
                        }
                        case "OR" -> {
                            Compoundable temp = new OR();
                            basicGates.add(temp);
                            elements.add(new UiElement("OR", this, new PVector(mouseX, mouseY), temp));
                        }
                        case "NOR" -> {
                            Compoundable temp = new NOR();
                            basicGates.add(temp);
                            elements.add(new UiElement("NOR", this, new PVector(mouseX, mouseY), temp));
                        }
                        case "XOR" -> {
                            Compoundable temp = new XOR();
                            basicGates.add(temp);
                            elements.add(new UiElement("XOR", this, new PVector(mouseX, mouseY), temp));
                        }
                        case "XNOR" -> {
                            Compoundable temp = new XNOR();
                            basicGates.add(temp);
                            elements.add(new UiElement("XNOR", this, new PVector(mouseX, mouseY), temp));
                        }
                        case "NOT" -> {
                            Compoundable temp = new NOT();
                            basicGates.add(temp);
                            elements.add(new UiElement("NOT", this, new PVector(mouseX, mouseY), temp));
                        }
                    }
                }
            } else if (state == 2) {
                for (UiElement g : elements) {
                    if(g.over(new PVector(mouseX, mouseY))){
                        if(selectedElement != null){
                            selectedElement = g;
                        }
                        break;
                    }
                }
            } else if (state == 3) {
                for (UiElement g : elements) {
                    if(g.over(new PVector(mouseX, mouseY))){
                        lastState = state;
                        state = 5;
                        selectedElement = g;
                        break;
                    }
                }
            } else if (state == 4) {
                for (UiElement g : elements) {
                    if(g.over(new PVector(mouseX, mouseY))){
                        g.gate.fullDisconnect();
                        basicGates.remove(g.gate);
                        elements.remove(g);
                        break;
                    }
                }
            } else if (state == 5) {
                for (UiElement g : elements) {
                    if(g.over(new PVector(mouseX, mouseY))){
                        if(selectedElement != null){
                            selectedElement.gate.connection((SignalReceiver) g.gate);
                            selectedElement = null;
                            state = lastState;
                        }
                        break;
                    }
                }
            }
        }
    }

    public void mouseDragged() {
        if (mousePressed && (mouseButton == LEFT)) {
            if (state == 2) {
                for (UiElement g : elements) {
                    if(g.over(new PVector(mouseX, mouseY))){
                        g.position = new PVector(mouseX - 256 * ShapeLoader.scale, mouseY- 256 * ShapeLoader.scale);
                        break;
                    }
                }
            }
        }
    }
}
