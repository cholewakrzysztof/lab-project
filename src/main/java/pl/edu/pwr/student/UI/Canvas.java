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
    public ArrayList<UiElement> gates = new ArrayList<UiElement>();
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
                new ListElement("XNOR", null,""),
                new ListElement("NOT", null, "")
            ).run();

        booster.createNotification(
                "Started",
                "Gates-Simulation");
    }

    public void draw(){
        background(255);

        for (UiElement g : gates) {
            g.run();
        }
        if (gates.isEmpty()) {
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
                case "AND" -> gates.add(new AND("AND", this, new PVector(mouseX, mouseY)));
                case "NAND" -> gates.add(new NAND("NAND", this, new PVector(mouseX, mouseY)));
                case "OR" -> gates.add(new OR("OR", this, new PVector(mouseX, mouseY)));
                case "NOR" -> gates.add(new NOR("NOR", this, new PVector(mouseX, mouseY)));
                case "XOR" -> gates.add(new XOR("XOR",  this, new PVector(mouseX, mouseY)));
                case "XNOR" -> gates.add(new XNOR("XNOR",  this, new PVector(mouseX, mouseY)));
                case "NOT" -> gates.add(new NOT("NOT",  this, new PVector(mouseX, mouseY)));
            }
        }
    }
}
