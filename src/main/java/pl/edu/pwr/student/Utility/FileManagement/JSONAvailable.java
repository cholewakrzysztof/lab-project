package pl.edu.pwr.student.Utility.FileManagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.UI.Blocks.Drawable;
import processing.core.PVector;

import java.awt.*;
import java.util.LinkedList;

/**
 * Representation of object based on UIElement that can be safe and create from file
 */
public class JSONAvailable {
    /**
     * The position of this element.
     */
    @JsonProperty("position")
    public PVector position;
    /**
     * The name of this element.
     */
    @JsonProperty("elName")
    public String elName;
    /**
     * Set of outputs hashcodes
     */
    @JsonProperty("outputs")
    public LinkedList<Integer> outputs;
    /**
     * Hashcode of source element
     */
    @JsonProperty("hashCode")
    public Integer hashCode;
    /**
     * The color associated with this element.
     */
    @JsonProperty("color")
    public Color color;

    /**
     *  Constructor used by jackson package
     */
    public JSONAvailable(){
        super();
    }

    /**
     * Custom constructor creating object with most important data from UiElement
     * @param element Source UiElement element for new object
     */
    public JSONAvailable(Drawable element){
        this.position = element.position;
        this.outputs = JSONAvailable.GetOutputsHashCodes(element);
        this.elName = element.elName;
        this.hashCode = element.uiElem.hashCode();
        /*TODO
        * Add special fields like color for LED, interval for Clock
        * */
    }

    /**
     *  Get all hashCodes of outputs from element
     * @param element Source UiElement
     * @return Linked list of hashCodes
     */
    private static LinkedList<Integer> GetOutputsHashCodes(Drawable element){
        LinkedList<Integer> list = new LinkedList<>();
        for (SignalReceiver output:element.uiElem.getOutputs()) {
            list.add(output.hashCode());
        }
        return list;
    }
}
