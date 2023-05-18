package pl.edu.pwr.student.UI.Blocks;

import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiAvailable;
import pl.edu.pwr.student.Utility.FileManagement.JSONAvailable;
import processing.core.PVector;

public class CompoundElement extends Drawable {

    public CompoundElement(String type, Canvas s, PVector v, UiAvailable uiElem) {
        super(type, s, v, uiElem);
    }

    public CompoundElement(JSONAvailable jsonAvailable, Canvas s) {
        super(jsonAvailable, s);
    }

    @Override
    public boolean over(PVector v) {
        return false;
    }

    @Override
    public void run(){
        System.out.println("Running compound gate");
    }
}
