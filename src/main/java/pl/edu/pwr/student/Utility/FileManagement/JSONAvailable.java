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
     * Set of outputs indexes
     */
    @JsonProperty("outputs")
    public LinkedList<Integer> outputs;
    /**
     * Set of inputs indexes
     */
    @JsonProperty("inputs")
    public LinkedList<Integer> inputs;
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
        this.inputs = JSONAvailable.GetInputsIndexes(element);
        this.outputs = JSONAvailable.GetOutputsIndexes(element);
        this.elName = element.elName;

    }

    /**
     * Return indexes from canva set of all inputs of this element
     * @param element element with inputs
     * @return HashSet of this element inputs indexes
     */
    private static LinkedList<Integer> GetInputsIndexes(UiElement element){
        LinkedList<Integer> list = new LinkedList<>();
        HashSet<SignalSender> elementInputs = element.uiElem.getInputs();
        HashSet<SignalSender> allInputs = element.sketch.userInputs;
        Integer index = 0;
        System.out.println("Wczytuję indexy elementu");
        for (SignalSender candidate:allInputs) {
            if(elementInputs.contains(candidate)){
                for (SignalSender obj : elementInputs) {
                    if (obj.equals(candidate))
                        list.add(index);
                        System.out.println("Dodaję index "+index );
                }
            }
            index++;
        }
        return list;
    }

    /**
     * Return indexes from canva set of all outputs of this element
     * @param element element with outputs
     * @return HashSet of this element outputs indexes
     */
    private static LinkedList<Integer> GetOutputsIndexes(UiElement element){
        LinkedList<Integer> list = new LinkedList<>();
        HashSet<SignalReceiver> elementOutputs = element.uiElem.getOutputs();
        HashSet<SignalReceiver> allOutputs = element.sketch.systemOutputs;
        Integer index = 0;
        for (SignalReceiver candidate:allOutputs) {
            if(elementOutputs.contains(candidate)){
                for (SignalReceiver obj : elementOutputs) {
                    if (obj.equals(candidate))
                        list.add(index);
                }
            }
            index++;
        }
        return list;
    }
}
