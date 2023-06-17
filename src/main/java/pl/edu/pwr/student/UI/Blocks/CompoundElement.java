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

/**
 * Represents a compound element on the {@link Canvas}.
 * Extends the abstract {@link Drawable} class.
 */
public class CompoundElement extends Drawable {

    /**
     * If true, the output and input elements will be swapped.
     */
    private boolean swap = false;

    /**
     * The set of all elements of the CompoundGate.
     */
    private final Set<Drawable> elements = new HashSet<>();

    /**
     * Constructs a new {@link CompoundElement} object.
     *
     * @param type The name of the {@link CompoundGate}.
     * @param s The {@link Canvas} used to render the {@link CompoundElement}.
     * @param v The position of the {@link CompoundElement} on the canvas, specified as a {@link PVector} object.
     * @param uiElem The gate represented by the {@link CompoundElement}, specified as a {@link UiAvailable} object.
     */
    public CompoundElement(String type, Canvas s, PVector v, UiAvailable uiElem) {
        super(type, s, v, uiElem, 0f);
        initElements();
    }

    /**
     * Constructs a new {@link CompoundElement} object.
     *
     * @param jsonAvailable The {@link JSONAvailable} object used to load the {@link CompoundGate}.
     * @param s The {@link Canvas} used to render the {@link CompoundElement}.
     */
    public CompoundElement(JSONAvailable jsonAvailable, Canvas s) {
        super(jsonAvailable, s);
    }

    /**
     * Initializes the elements of the {@link CompoundGate}.
     */
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

    /**
     * Gets true if mouse is over the {@link CompoundElement}
     *
     * @return true if any of the mouse is over the {@link Drawable} elements, false otherwise.
     */
    @Override
    public boolean over(PVector v) {
        for (Drawable d : elements){
            if (d.over(v)){
                return true;
            }
        }
        return super.over(v);
    }

    /**
     * Draws the {@link CompoundElement} and all the lines.
     */
    @Override
    public void run(){
        sketch.stroke(0, 0, 0);
        if (over(new PVector(sketch.mouseX, sketch.mouseY))){
            sketch.fill(0, 30);
            sketch.square((position.x-sketch.getOffset().x)*ShapeLoader.scale,(position.y-sketch.getOffset().y)*ShapeLoader.scale,ShapeLoader.size*ShapeLoader.scale);
        }

        int out;
        int in;

        if (swap){
            in = ((CompoundGate)uiElem).getOutputKeys().length;
            out = ((CompoundGate)uiElem).getInputKeys().length;
        } else {
            out = ((CompoundGate)uiElem).getOutputKeys().length;
            in = ((CompoundGate)uiElem).getInputKeys().length;
        }

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

    /**
     * Updates the position of the {@link CompoundElement} and all the lines.
     *
     * @param v The new position of the {@link CompoundElement}, specified as a {@link PVector} object.
     */
    @Override
    public void updatePosition(PVector v) {
        position = v;

        List<String> outs;
        List<String> ins;


        if (swap){
            ins = List.of(((CompoundGate) uiElem).getOutputKeys());
            outs = List.of(((CompoundGate) uiElem).getInputKeys());
        } else {
            outs = List.of(((CompoundGate) uiElem).getOutputKeys());
            ins = List.of(((CompoundGate) uiElem).getInputKeys());
        }

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

    /**
     * Gets the {@link UiAvailable} that is under the mouse.
     *
     * @return The {@link UiAvailable} that is under the mouse, null if none.
     */
    @Override
    public UiAvailable getGate() {
        PVector v = new PVector(sketch.mouseX, sketch.mouseY);
        for (Drawable d : elements){
            if (d.over(v)){
                return d.getGate();
            }
        }
        return uiElem;
    }

    /**
     * Gets the input {@link UiAvailable} that is under the mouse.
     *
     * @return The {@link UiAvailable} that is under the mouse, null if none.
     */
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

    /**
     * Gets the output {@link UiAvailable} that is under the mouse.
     *
     * @return The {@link UiAvailable} that is under the mouse, null if none.
     */
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

    /**
     * Gets the set of all {@link Drawable}
     *
     * @return The set of all {@link Drawable}
     */
    public Set<Drawable> getElements() {
        return elements;
    }

    /**
     * Toggles the IO side of the {@link CompoundElement}
     */
    public void toggleIOSide() {
        for(Drawable el : elements){
            el.rotation = el.rotation%2;
        }

        swap = !swap;
        updatePosition(position);
    }

    /**
     * Gets the IO side of the {@link CompoundElement}
     *
     * @return The IO side of the {@link CompoundElement}
     */
    public boolean getSwap() {
        return swap;
    }
}