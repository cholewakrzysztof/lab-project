package pl.edu.pwr.student.UI.Handlers;

import pl.edu.pwr.student.Gates.BasicGates.*;
import pl.edu.pwr.student.IO.Input.Clock;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.IO.Output.Speaker;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;
import processing.core.PVector;
import uibooster.model.ListElement;

public class MousePressedHandler {
    public static void mousePressed(Canvas sketch){
        if (sketch.mousePressed && (sketch.mouseButton == sketch.LEFT)) {
            for (int i = 0; i < sketch.buttons.size(); i++) {
                if(sketch.buttons.get(i).over(new PVector(sketch.mouseX, sketch.mouseY))){
                    sketch.lastState = sketch.state;
                    sketch.state = i;
                    if (sketch.state == 1) {
                        sketch.form.show();
                    } else {
                        sketch.form.hide();
                    }
                    return;
                }
            }

            if(sketch.state == 1){
                ListElement selected = (ListElement) sketch.form.getByLabel("Select Gate").getValue();
                if(selected != null){
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
                            Speaker temp = new Speaker("",1000);
                            temp.toggle();
                            sketch.systemOutputs.add(temp);
                            sketch.elements.add(new UiElement("SPEAKER", sketch, new PVector(sketch.mouseX, sketch.mouseY), temp));
                        }
                        case "LED" -> {
                            LED temp = new LED("",1000);
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
                            Clock temp = new Clock(1000,1000);
                            temp.toggle();
                            sketch.userInputs.add(temp);
                            sketch.elements.add(new UiElement("CLOCK", sketch, new PVector(sketch.mouseX, sketch.mouseY), temp));
                        }
                    }
                }
            } else if (sketch.state == 2) {
                for (UiElement g : sketch.elements) {
                    if(g.over(new PVector(sketch.mouseX, sketch.mouseY))){
                        if(sketch.selectedElement != null){
                            sketch.selectedElement = g;
                        }
                        break;
                    }
                }
            } else if (sketch.state == 3) {
                for (UiElement g : sketch.elements) {
                    if(g.over(new PVector(sketch.mouseX, sketch.mouseY))){
                        sketch.lastState = sketch.state;
                        sketch.state = 5;
                        sketch.selectedElement = g;
                        break;
                    }
                }
            } else if (sketch.state == 4) {
                for (UiElement g : sketch.elements) {
                    if(g.over(new PVector(sketch.mouseX, sketch.mouseY))){
                        g.uiElem.fullDisconnect();
                        sketch.basicGates.remove(g.uiElem);
                        sketch.elements.remove(g);
                        break;
                    }
                }
            } else if (sketch.state == 5) {
                for (UiElement g : sketch.elements) {
                    if(g.over(new PVector(sketch.mouseX, sketch.mouseY))){
                        if(sketch.selectedElement != null){
                            try{
                                sketch.selectedElement.uiElem.connection((SignalReceiver) g.uiElem);
                                sketch.selectedElement = null;
                                sketch.state = sketch.lastState;
                            } catch (Exception e){
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
