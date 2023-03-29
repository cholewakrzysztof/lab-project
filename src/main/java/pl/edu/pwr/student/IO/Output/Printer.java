package pl.edu.pwr.student.IO.Output;

public class Printer extends BasicReceiver {
    private final String name;
    protected void react() {
        System.out.println(name + " state changed to: " + state);
    }
    public Printer(String name) {
        this.name = name;
    }
}
