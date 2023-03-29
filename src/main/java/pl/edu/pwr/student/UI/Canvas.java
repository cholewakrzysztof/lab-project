package pl.edu.pwr.student.UI;

import pl.edu.pwr.student.Gates.BasicGates.*;
import processing.core.PApplet;
import processing.core.PVector;
import uibooster.UiBooster;
import uibooster.model.Form;
import uibooster.model.ListElement;

import java.util.ArrayList;

public class Canvas extends PApplet {

    public Form form;
    public UiBooster booster;
    public ArrayList<UiElement> elements = new ArrayList<UiElement>();
    public void settings(){
        booster = new UiBooster();
        size(800, 800);

        form = booster
            .createForm("Gates")
            .addList("Select Gate",
                new ListElement("AND", null,""),
                new ListElement("NAND", null,""),
                new ListElement("OR", null, ""),
                new ListElement("NOR", null,""),
                new ListElement("XOR", null,""),
                new ListElement("XNOR", null,"")
//                new ListElement("NOT", null, "")
            ).run();

        booster.createNotification(
                "Started",
                "Gates-Simulation");
    }

    public void draw(){
        background(255);

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

    public void mousePressed() {
        ListElement selected = (ListElement) form.getByLabel("Select Gate").getValue();
        if(selected != null){
            switch (selected.getTitle()) {
                case "AND" -> elements.add(new UiElement("AND", this, new PVector(mouseX, mouseY), new AND()));
                case "NAND" -> elements.add(new UiElement("NAND", this, new PVector(mouseX, mouseY), new NAND()));
                case "OR" -> elements.add(new UiElement("OR", this, new PVector(mouseX, mouseY), new OR()));
                case "NOR" -> elements.add(new UiElement("NOR", this, new PVector(mouseX, mouseY), new NOR()));
                case "XOR" -> elements.add(new UiElement("XOR", this, new PVector(mouseX, mouseY), new XOR()));
                case "XNOR" -> elements.add(new UiElement("XNOR", this, new PVector(mouseX, mouseY), new XNOR()));
//                case "NOT" -> elements.add(new UiElement("NOT", this, new PVector(mouseX, mouseY), new NOT()));
            }
        }
    }
}
