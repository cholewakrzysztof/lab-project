package pl.edu.pwr.student.UI.Buttons;

import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;
import processing.core.PShape;
import processing.core.PVector;

/**
 * Class representing button for creating new elements
 */
public class CreateButton extends UiElement {

    PShape shape;

    /**
     * Constructor
     * @param s Processing sketch
     */
    public CreateButton(Canvas s) {
        super("CREATE", s, new PVector(70,10), null);
        shape = sketch.loadShape("src/main/resources/buttons/CREATE.svg");
        shape.scale(3);
    }

    /**
     *
     * Draws element
     */
    @Override
    public void run() {
        sketch.stroke(0);
        if (over(new PVector(sketch.mouseX, sketch.mouseY))){
            if (sketch.state == 1){
                sketch.fill(0,255,0,30);
                sketch.square(position.x,position.y,48);
            } else {
                sketch.fill(0, 30);
                sketch.square(position.x,position.y,48);
            }
        } else if(sketch.state == 1){
            sketch.fill(0,255,0,20);
            sketch.square(position.x,position.y,48);
        } else {
            sketch.fill(0, 20);
            sketch.square(position.x,position.y,48);
        }
        sketch.shape(shape, position.x, position.y);
    }
}
