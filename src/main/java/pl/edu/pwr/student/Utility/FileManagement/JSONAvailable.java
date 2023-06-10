package pl.edu.pwr.student.Utility.FileManagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.VirtualIO;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.UI.Blocks.CompoundElement;
import pl.edu.pwr.student.UI.Blocks.Drawable;
import processing.core.PVector;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Representation of object based on UIElement that can be safe and create from file
 */
public class JSONAvailable {
    @JsonProperty("message")
    private String message;
    @JsonProperty("swap")
    private boolean swap;
    @JsonProperty("rotation")
    private float rotation;
    @JsonProperty("gateType")
    private String gateType;
    /**
     * The position of this element.
     */
    @JsonProperty("position")
    private PVector position;
    /**
     * The name of this element.
     */
    @JsonProperty("elName")
    private String elName;
    /**
     * Set of outputs hashcode
     */
    @JsonProperty("outputs")
    private LinkedList<Integer> outputs;
    /**
     * Hashcode of source element
     */
    @JsonProperty("hashCode")
    private Integer hashCode;
    /**
     * The color associated with this element.
     */
    @JsonProperty("color")
    private Color color;

    /**
     *  Constructor used by jackson package
     */

    @JsonProperty("logic")
    private LinkedList<JSONAvailable> logic;
    public JSONAvailable(){
        super();
    }

    /**
     * Custom constructor creating object with most important data from UiElement
     * @param element Source UiElement element for new object
     */
    public JSONAvailable(Drawable element){
        Class<?> classType;
        if(element.getGate()==null){
            classType=CompoundGate.class;
        }
        else{
            classType = element.getGate().getClass();
        }
        this.gateType = classType.getSimpleName();
        this.position = element.position;
        this.rotation = element.rotation;
        this.elName = element.elName;
        if(classType.equals(CompoundGate.class)){
            logic = new LinkedList<>();
            for (Compoundable compoundable:((CompoundGate)element.uiElem).getGates()) {
                logic.add(new JSONAvailable(compoundable));
            }
            this.hashCode = element.uiElem.hashCode();
            this.outputs = GetOutputsHashCodes((CompoundElement)element);
            this.swap = ((CompoundElement)element).getSwap();
        }else{
            this.outputs = GetOutputsHashCodes(element.getGate().getOutputs());
            this.hashCode = element.getGate().hashCode();
        }

        if(element.getGate() instanceof Compoundable)
            if(((Compoundable)element.getGate()).isIO())
                this.elName = ((VirtualIO) element.getGate()).name;


        /*TODO
        * Add special fields like color for LED, interval for Clock
        * */
    }

    public JSONAvailable(CompoundGate compoundGate){
        logic = new LinkedList<>();
        for (Compoundable compoundable: compoundGate.getGates()){
            logic.add(new JSONAvailable(compoundable));
        }
        this.elName = compoundGate.name;
        this.hashCode = compoundGate.hashCode();
        this.message = compoundGate.message;
        this.outputs = new LinkedList<>();
    }

    public JSONAvailable(Compoundable compoundable){
        this.gateType = compoundable.getClass().getSimpleName();
        this.elName = compoundable.getClass().getSimpleName();
        if(compoundable.isIO())
            this.elName = ((VirtualIO) compoundable).name;
        this.outputs = GetOutputsHashCodes(compoundable.getOutputs());
        this.hashCode = compoundable.hashCode();
    }

    /**
     * Generate list of hashCodes of CompoundElement outputs
     * @param element Source CompoundElement
     * @return Integer LinkedList with hashCodes of outputs
     */
    private static LinkedList<Integer> GetOutputsHashCodes(CompoundElement element){
        LinkedList<Integer> list = new LinkedList<>();
        for (Drawable virtualIO:element.getElements())
            for (SignalReceiver output:((VirtualIO)virtualIO.uiElem).getOutputs())
                list.add(output.hashCode());
        return list;
    }

    /**
     * Generate list of hashCodes of outputs
     * @param outputs Set of outputs
     * @return Integer LinkedList with hashCodes
     */
    private static LinkedList<Integer> GetOutputsHashCodes(HashSet<SignalReceiver> outputs){
        LinkedList<Integer> list = new LinkedList<>();
        for(SignalReceiver output: outputs)
            list.add(output.hashCode());
        return list;
    }

    public String getGateType() {return gateType;}
    public Integer getHashCode() {return hashCode;}
    public LinkedList<Integer> getOutputs() {return outputs;}
    public String getElName() {return elName;}
    public PVector getPosition() {return position;}
    public Color getColor() {return color; }
    public String getMessage() {return message;}
    public LinkedList<JSONAvailable> getLogic() {return logic;}
    public float getRotation() { return rotation; }
    public boolean getSwap() { return swap; }
}
