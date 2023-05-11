package pl.edu.pwr.student.Utility.FileManagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.UI.UiElement;
import processing.core.PVector;

import java.awt.*;
import java.util.HashSet;
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

    public JSONAvailable(){
        super();
    }
    public JSONAvailable(UiElement element){
        this.position = element.position;
        this.outputs = JSONAvailable.GetOutputsHashCodes(element);
        this.elName = element.elName;
        this.hashCode = element.uiElem.hashCode();
    }

    /**
     *
     * @param element
     * @return
     */
    private static LinkedList<Integer> GetOutputsHashCodes(UiElement element){
        LinkedList<Integer> list = new LinkedList<>();
        for (SignalReceiver el:element.uiElem.getOutputs()) {
            list.add(el.hashCode());
        }
        return list;
    }
}
