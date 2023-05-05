package pl.edu.pwr.student.Utility.FileManagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.UI.UiElement;
import processing.core.PVector;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Representation of object based on UIElement that can be safe and create from file
 */
public class JSONAvaible {
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
     * Outputs of element
     */
    @JsonProperty("outputs")
    public HashSet<SignalReceiver> outputs;
    /**
     * Inputs of element
     */
    @JsonProperty("intputs")
    public HashSet<SignalSender> intputs;
    /**
     * The color associated with this element.
     */
    @JsonProperty("color")
    public Color color;

    public JSONAvaible(){
        super();
    }
    public JSONAvaible(UiElement element){
        this.position = element.position;
        this.outputs = element.uiElem.getOutputs();
        this.intputs = element.uiElem.getInputs();
        this.elName = element.elName;

    }
}
