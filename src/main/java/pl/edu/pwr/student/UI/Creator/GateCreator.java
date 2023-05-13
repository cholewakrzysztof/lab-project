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
    public static void create(String type, PVector mouse, Canvas canvas) {
        Set<UiElement> elements = canvas.getElements();
        switch (type) {
            case "AND" -> {
                AND temp =  new AND();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "NAND" -> {
                NAND temp = new NAND();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "OR" -> {
                OR temp = new OR();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "NOR" -> {
                NOR temp = new NOR();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "XOR" -> {
                XOR temp = new XOR();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "XNOR" -> {
                XNOR temp = new XNOR();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "NOT" -> {
                NOT temp = new NOT();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "SPEAKER" -> {
                Speaker temp = new Speaker();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "LED" -> {
                LED temp = new LED("", 0);
                temp.toggle();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "SWITCH" -> {
                Switch temp = new Switch();
                temp.toggle();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "CLOCK" -> {
                Clock temp = new Clock(1000, 1000);
                temp.toggle();
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "DELAY" -> {
                Delay temp = new Delay(1000);
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
            case "VIRTUALIO" -> {
                VirtualIO temp = new VirtualIO("IO");
                elements.add(new UiElement(type, canvas, mouse, temp));
            }
        }
    }
}