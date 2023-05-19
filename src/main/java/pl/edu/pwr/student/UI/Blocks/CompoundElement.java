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
                        position.y + ShapeLoader.size * (i+1) - ShapeLoader.size * outs.length/2f
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
                        position.y + ShapeLoader.size * (i+1) - ShapeLoader.size * ins.length/2f
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
        return false;
    }

    @Override
    public void run(){
        for (Drawable d : elements){
            d.run();
        }
    }

    @Override
    public void updatePosition(PVector v) {
        List<String> outs = List.of(((CompoundGate) uiElem).getOutputKeys());
        List<String> ins = List.of(((CompoundGate) uiElem).getInputKeys());
        for (Drawable d : elements){
            int out = outs.indexOf(d.elName);
            if (out >= 0) {
                d.updatePosition(
                    new PVector(
                        v.x + ShapeLoader.size,
                        v.y + ShapeLoader.size * (out+1) - ShapeLoader.size * outs.size()/2f
                    )
                );
                continue;
            }
            int in = ins.indexOf(d.elName);
            if (in >= 0) {
                d.updatePosition(
                    new PVector(
                        v.x - ShapeLoader.size,
                        v.y + ShapeLoader.size * (in+1) - ShapeLoader.size * ins.size()/2f
                    )
                );
            }

        }
    }
}