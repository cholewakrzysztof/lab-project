package pl.edu.pwr.student.UI.Handlers;

import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.*;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.Delay;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.NOT;
import pl.edu.pwr.student.IO.Input.Clock;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.IO.Output.Speaker;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;
import processing.core.PVector;
import uibooster.model.ListElement;

/**
 * Handles mouse pressed event
 */
public class MousePressedHandler {

    /**
     * Private constructor to prevent creating instances of this class
     */
    private MousePressedHandler() {}

    /**
     * Handles mouse pressed event
     * @param sketch - sketch to handle mouse pressed event for
     */
    public static void mousePressed(Canvas sketch){
        for (int i = 0; i < sketch.buttons.size(); i++) {
            if(sketch.buttons.get(i).over(new PVector(sketch.mouseX, sketch.mouseY))) {
                sketch.buttons.get(i).click();
                return;
            }
        }


        switch (sketch.state) {
            case 0 -> {
                for (UiElement g : sketch.elements) {
                    if (g.over(new PVector(sketch.mouseX, sketch.mouseY))) {
                        sketch.selectedElement = g;
                        break;
                    }
                }
            }
            case 1 -> {
                ListElement selected = (ListElement) sketch.form.getByLabel("Select Gate").getValue();
                if (selected != null) {
                    //TODO: make it added automatically (created new gates by user are now a problem)
                    switch (selected.getTitle()) {
                        case "AND" -> {
                            AND temp = new AND();
                            sketch.basicGates.add(temp);
                            sketch.elements.add(new UiElement("AND", sketch, new PVector(sketch.mouseX, sketch.mouseY), temp));
                        }
                        case "NAND" -> {
                            NAND temp = new NAND();
                            sketch.basicGates.add(temp);
                            sketch.elements.add(new UiElement("NAND", sketch, new PVector(sketch.mouseX, sketch.mouseY), temp));
                        }
                        case "OR" -> {
                            OR temp = new OR();
                            sketch.basicGates.add(temp);
                            sketch.elements.add(new UiElement("OR", sketch, new PVector(sketch.mouseX, sketch.mouseY), temp));
                        }
                        case "NOR" -> {
                            NOR temp = new NOR();
                            sketch.basicGates.add(temp);
                            sketch.elements.add(new UiElement("NOR", sketch, new PVector(sketch.mouseX, sketch.mouseY), temp));
                        }
                        case "XOR" -> {
                            XOR temp = new XOR();
                            sketch.basicGates.add(temp);
                            sketch.elements.add(new UiElement("XOR", sketch, new PVector(sketch.mouseX, sketch.mouseY), temp));
                        }
                        case "XNOR" -> {
                            XNOR temp = new XNOR();
                            sketch.basicGates.add(temp);
                            sketch.elements.add(new UiElement("XNOR", sketch, new PVector(sketch.mouseX, sketch.mouseY), temp));
                        }
                        case "NOT" -> {
                            NOT temp = new NOT();
                            sketch.basicGates.add(temp);
                            sketch.elements.add(new UiElement("NOT", sketch, new PVector(sketch.mouseX, sketch.mouseY), temp));
                        }
                        case "SPEAKER" -> {
                            Speaker temp = new Speaker();
                            sketch.systemOutputs.add(temp);
                            sketch.elements.add(new UiElement("SPEAKER", sketch, new PVector(sketch.mouseX, sketch.mouseY), temp));
                        }
                        case "LED" -> {
                            LED temp = new LED("", 0);
                            temp.toggle();
                            sketch.systemOutputs.add(temp);
                            sketch.elements.add(new UiElement("LED", sketch, new PVector(sketch.mouseX, sketch.mouseY), temp));
                        }
                        case "SWITCH" -> {
                            Switch temp = new Switch();
                            temp.toggle();
                            sketch.userInputs.add(temp);
                            sketch.elements.add(new UiElement("SWITCH", sketch, new PVector(sketch.mouseX, sketch.mouseY), temp));
                        }
                        case "CLOCK" -> {
                            Clock temp = new Clock(1000, 1000);
                            temp.toggle();
                            sketch.userInputs.add(temp);
                            sketch.elements.add(new UiElement("CLOCK", sketch, new PVector(sketch.mouseX, sketch.mouseY), temp));
                        }
                        case "DELAY" -> {
                            Delay temp = new Delay(1000);
                            sketch.userInputs.add(temp);
                            sketch.elements.add(new UiElement("DELAY", sketch, new PVector(sketch.mouseX, sketch.mouseY), temp));
                        }
                    }
                }
            }
            case 2 -> {
                for (UiElement g : sketch.elements) {
                    if (g.over(new PVector(sketch.mouseX, sketch.mouseY))) {
                        sketch.lastState = sketch.state;
                        sketch.state = 4;
                        sketch.selectedElement = g;
                        break;
                    }
                }
            }
            case 3 -> {
                for (UiElement g : sketch.elements) {
                    if (g.over(new PVector(sketch.mouseX, sketch.mouseY))) {
                        g.uiElem.fullDisconnect();
                        sketch.basicGates.remove(g.uiElem);
                        sketch.elements.remove(g);
                        break;
                    }
                }
            }
            case 4 -> {
                for (UiElement g : sketch.elements) {
                    if (g.over(new PVector(sketch.mouseX, sketch.mouseY))) {
                        if (sketch.selectedElement != null) {
                            try {
                                sketch.selectedElement.uiElem.connection((SignalReceiver) g.uiElem);
                                sketch.selectedElement = null;
                                sketch.state = sketch.lastState;
                            } catch (Exception e) {
                                sketch.booster.createNotification(
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
}
