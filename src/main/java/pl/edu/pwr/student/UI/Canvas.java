package pl.edu.pwr.student.UI;

import pl.edu.pwr.student.Gates.BasicGates.*;
import pl.edu.pwr.student.Gates.Compoundable;
import processing.core.PApplet;
import processing.core.PVector;
import uibooster.UiBooster;
import uibooster.model.Form;
import uibooster.model.ListElement;

import java.util.ArrayList;
import java.util.HashSet;

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
     * 2 - editing elements
     */
    public int state;

    /**
     * UiBooster object
     */
    public UiBooster booster;

    /**
     * List of all elements on canvas
     */
    public ArrayList<UiElement> elements = new ArrayList<UiElement>();

    /**
     * List of all basic gates
     */
    private HashSet<Compoundable> basicGates = new HashSet<>();

    /**
     * List of all buttons
     */
    private final ArrayList<UiElement> buttons = new ArrayList<>();

    /**
     * Constructor
     * @param basicGates list of all basic gates
     */
    public Canvas (HashSet<Compoundable> basicGates) {
        String[] processingArgs = {"Gates-Simulation"};
        PApplet.runSketch(processingArgs, this);

        this.basicGates = basicGates;


        //TODO: throws Cannot parse "currentcolor".
        buttons.add(new CreateButton(this));
        buttons.add(new EditButton(this));
        buttons.add(new InteractionButton(this));
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

    public void settings(){
        booster = new UiBooster();
        size(800, 800);
        //TODO: make id added automatically (created new gates by user are now a problem)
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

    public void draw(){
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
            if(buttons.get(0).over(new PVector(mouseX, mouseY))){
                state = 1;
                form.show();
                return;
            }

            if(buttons.get(1).over(new PVector(mouseX, mouseY))){
                state = 2;
                form.hide();
                return;
            }

            if(buttons.get(2).over(new PVector(mouseX, mouseY))){
                state = 0;
                form.hide();
                return;
            }

            if(state == 1){
                ListElement selected = (ListElement) form.getByLabel("Select Gate").getValue();
                if(selected != null){
                    //TODO: make id added automatically (created new gates by user are now a problem)
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
            }
        } else if (mousePressed && (mouseButton == RIGHT)) {
            for (UiElement g : elements) {
                if(g.over(new PVector(mouseX, mouseY))){
                    booster.showList(
                        "",
                        "",
                        element -> {
                            if (element.getTitle().equals("Add new output")){
                                //TODO: add new output
                            } else if (element.getTitle().equals( "Add new input")){
                                //TODO: add new input
                            } else if(element.getTitle().equals("Delete")){
                                basicGates.remove(g.gate);
                                elements.remove(g);
                            }
                        },
                        new ListElement("Add new output", null),
                        new ListElement("Add new input", null),
                        new ListElement("Delete", null)
                    );
                    break;
                }
            }
        }
    }
}
