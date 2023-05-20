package pl.edu.pwr.student.UI.Blocks;

import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiAvailable;
import pl.edu.pwr.student.Utility.FileManagement.JSONAvailable;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PVector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompoundElement extends Drawable {

    /**
     * The set of all elements of the CompoundGate.
     */
    private final Set<Drawable> elements = new HashSet<>();

    public CompoundElement(String type, Canvas s, PVector v, UiAvailable uiElem) {
        super(type, s, v, uiElem);
        initElements();
    }

    public CompoundElement(JSONAvailable jsonAvailable, Canvas s) {
        super(jsonAvailable, s);
    }

    private void initElements(){
        String[] outs = ((CompoundGate)uiElem).getOutputKeys();
        String[] ins = ((CompoundGate)uiElem).getInputKeys();
        for (int i = 0; i < outs.length; i++){
            elements.add(
                new UiElement(
                    outs[i],
                    sketch,
                    new PVector(
                        position.x + ShapeLoader.size,
                        position.y + ShapeLoader.size * i - ShapeLoader.size * (outs.length-1)/2f
                    ),
                    (UiAvailable) ((CompoundGate)uiElem).output(outs[i])
                )
            );
        }
        for (int i = 0; i < ins.length; i++){
            elements.add(
                new UiElement(
                    ins[i],
                    sketch,
                    new PVector(
                        position.x - ShapeLoader.size,
                        position.y + ShapeLoader.size * i - ShapeLoader.size * (ins.length-1)/2f
                    ),
                    (UiAvailable) ((CompoundGate)uiElem).input(ins[i])
                )
            );
        }
    }

    @Override
    public boolean over(PVector v) {
        for (Drawable d : elements){
            if (d.over(v)){
                return true;
            }
        }
        return super.over(v);
    }

    @Override
    public void run(){
        sketch.stroke(0, 0, 0);
        if (over(new PVector(sketch.mouseX, sketch.mouseY))){
            sketch.fill(0, 30);
            sketch.square((position.x-sketch.getOffset().x)*ShapeLoader.scale,(position.y-sketch.getOffset().y)*ShapeLoader.scale,ShapeLoader.size*ShapeLoader.scale);
        }

        int out = ((CompoundGate)uiElem).getOutputKeys().length;
        int in = ((CompoundGate)uiElem).getInputKeys().length;

        sketch.line(
        (position.x-sketch.getOffset().x + ShapeLoader.size)*ShapeLoader.scale,
        (position.y - sketch.getOffset().y + ShapeLoader.size * (out-1)/2f + ShapeLoader.size)*ShapeLoader.scale,
        (position.x-sketch.getOffset().x + ShapeLoader.size)*ShapeLoader.scale,
        (position.y - sketch.getOffset().y - ShapeLoader.size * (out-1)/2f)*ShapeLoader.scale
        );

        sketch.line(
        (position.x-sketch.getOffset().x)*ShapeLoader.scale,
        (position.y - sketch.getOffset().y + ShapeLoader.size * (in-1)/2f + ShapeLoader.size)*ShapeLoader.scale,
        (position.x-sketch.getOffset().x)*ShapeLoader.scale,
        (position.y - sketch.getOffset().y - ShapeLoader.size * (in-1)/2f)*ShapeLoader.scale
        );

        sketch.textSize(32);
        sketch.fill(0);
        sketch.textAlign(sketch.CENTER, sketch.CENTER);
        String temp = ((CompoundGate) uiElem).name;

        float width = sketch.textWidth(temp);
        if (width > 50){
            sketch.textSize(32*ShapeLoader.scale*ShapeLoader.size/width);
        } else {
            sketch.textSize(32*ShapeLoader.scale);
        }
        sketch.text(
                temp,
                (position.x-sketch.getOffset().x+ShapeLoader.size/2f)*ShapeLoader.scale,
                (position.y-sketch.getOffset().y+ShapeLoader.size/2f)*ShapeLoader.scale
        );

        for (Drawable d : elements){
            d.run();
        }
    }

    @Override
    public void updatePosition(PVector v) {
        position = v;

        List<String> outs = List.of(((CompoundGate) uiElem).getOutputKeys());
        List<String> ins = List.of(((CompoundGate) uiElem).getInputKeys());
        for (Drawable d : elements){
            int out = outs.indexOf(d.elName);
            if (out >= 0) {
                d.updatePosition(
                    new PVector(
                        v.x + ShapeLoader.size,
                        v.y + ShapeLoader.size * out - ShapeLoader.size * (outs.size()-1)/2f
                    )
                );
                continue;
            }
            int in = ins.indexOf(d.elName);
            if (in >= 0) {
                d.updatePosition(
                    new PVector(
                        v.x - ShapeLoader.size,
                        v.y + ShapeLoader.size * in - ShapeLoader.size * (ins.size()-1)/2f
                    )
                );
            }

        }
    }

    @Override
    public UiAvailable getGate() {
        PVector v = new PVector(sketch.mouseX, sketch.mouseY);
        for (Drawable d : elements){
            if (d.over(v)){
                return d.getGate();
            }
        }

        if (super.over(v)){
            return uiElem;
        }

        return null;
    }

    @Override
    public UiAvailable getInput() {
        PVector v = new PVector(sketch.mouseX, sketch.mouseY);
        for (Drawable d : elements){
            if (d.over(v)){
                return (UiAvailable) ((CompoundGate) uiElem).input(d.elName);
            }
        }

        if (super.over(v)){
            return uiElem;
        }

        return null;
    }

    @Override
    public UiAvailable getOutput() {
        PVector v = new PVector(sketch.mouseX, sketch.mouseY);
        for (Drawable d : elements){
            if (d.over(v)){
                return (UiAvailable) ((CompoundGate) uiElem).output(d.elName);
            }
        }

        if (super.over(v)){
            return uiElem;
        }

        return null;
    }

    public Set<Drawable> getElements() {
        return elements;
    }
}