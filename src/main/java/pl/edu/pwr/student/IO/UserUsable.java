package pl.edu.pwr.student.IO;

/**
 * Implemented by UI elements that can be interacted with by the user.
 * Interactions include but are not limited to: toggling on and off, or changing the properties of the element(such as color, pitch, frequency, etc.)
 */
public interface UserUsable {

    /**
     * Reacts to the user interaction with the element.
     */
    void react();
}
