package pl.edu.pwr.student.UI.Creator;

import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.*;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.Delay;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.NOT;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.VirtualIO;
import pl.edu.pwr.student.IO.Input.Clock;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;
import pl.edu.pwr.student.IO.Output.Speaker;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiAvailable;
import pl.edu.pwr.student.UI.UiElement;
import processing.core.PVector;

import java.util.Set;

public class GateCreator {

    /**
     * Creates gate, saves it to proper place and performs necessary actions
     *
     * @param type - type of gate
     * @param mouse - vector of mouse
     */
    public static UiAvailable create(String type, PVector mouse, Canvas canvas) {
        Set<UiElement> elements = canvas.getElements();
        UiAvailable temp = null;
        switch (type) {
            case "AND" -> {
                temp =  new AND();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "NAND" -> {
                temp = new NAND();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "OR" -> {
                temp = new OR();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "NOR" -> {
                temp = new NOR();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "XOR" -> {
                temp = new XOR();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "XNOR" -> {
                temp = new XNOR();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "NOT" -> {
                temp = new NOT();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "SPEAKER" -> {
                temp = new Speaker();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "LED" -> {
                temp = new LED("", 0);
                ((LED)temp).toggle();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "SWITCH" -> {
                temp = new Switch();
                ((Switch)temp).toggle();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "CLOCK" -> {
                temp = new Clock(1000, 1000);
                ((Clock)temp).toggle();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "DELAY" -> {
                temp = new Delay(1000);
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "VIRTUALIO" -> {
                temp = new VirtualIO("IO");
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
        }
        return temp;
    }
}