package pl.edu.pwr.student.Utility.FileManagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.UI.UiElement;
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
    public JSONAvailable(UiElement element){
        this.position = element.position;
        this.outputs = JSONAvailable.GetOutputsHashCodes(element);
        this.elName = element.elName;
        this.hashCode = element.uiElem.hashCode();
    }

    /**
     *  Get all hashCodes of outputs from element
     * @param element Source UiElement
     * @return Linked list of hashCodes
     */
    private static LinkedList<Integer> GetOutputsHashCodes(UiElement element){
        LinkedList<Integer> list = new LinkedList<>();
        for (SignalReceiver el:element.uiElem.getOutputs()) {
            list.add(el.hashCode());
        }
        return list;
    }
}
