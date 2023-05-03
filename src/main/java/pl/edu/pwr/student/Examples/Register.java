package pl.edu.pwr.student.Examples;

import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.AND;
import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.NOR;
import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.OR;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.NOT;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.IO.Input.Clock;
import pl.edu.pwr.student.IO.Input.DebugButton;
import pl.edu.pwr.student.IO.Output.DebugLED;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.VirtualIO;
import pl.edu.pwr.student.Simulation;

import java.util.HashSet;

public class Register {
    public static void simulate() {
//        // Test SR Latch
//        Button set = new Button();
//        Button reset = new Button();
//        CompoundGate srLatch = createSRLatch();
//        Printer printer = new Printer("SR Latch");
//        set.connection(srLatch.input("set"));
//        reset.connection(srLatch.input("reset"));
//        srLatch.output("out").connection(printer);
//
//        Simulation.simWait(2500);
//
//        System.out.println("Pressed set");
//        set.press(100);
//        Simulation.simWait(2000);
//
//        System.out.println("Pressed set");
//        set.press(100);
//        Simulation.simWait(2000);
//
//        System.out.println("Pressed reset");
//        reset.press(100);
//        Simulation.simWait(2000);
//
//        System.out.println("Pressed reset");
//        reset.press(100);
//        Simulation.simWait(2000);
//
//        System.out.println("Pressed set");
//        set.press(100);
//        Simulation.simWait(2000);
//
//        System.out.println("Pressed set");
//        set.press(100);
//        Simulation.simWait(2000);
//
//        // WORKS

//        // Testing D Latch
//        Button data = new Button();
//        Button store = new Button();
//
//        CompoundGate dLatch = createDLatch();
//
//        Printer printer = new Printer("D Latch");
//
//        data.connection(dLatch.input("data"));
//        store.connection(dLatch.input("store"));
//
//        store.press(100);
//
//        dLatch.output("out").connection(printer);
//
//        Simulation.simWait(2500);
//
//        System.out.println("Pressed data + store");
//        data.press(200);
//        store.press(150);
//        Simulation.simWait(2000);
//
//        System.out.println("Pressed store");
//        store.press(150);
//        Simulation.simWait(2000);
//
//        System.out.println("Pressed data + store");
//        data.press(200);
//        store.press(150);
//        Simulation.simWait(2000);
//        // WORKS

//        // Testing edge triggered d latch
//        LED clockLED = new LED("Clock", 500);
//        Clock clock = new Clock(1000, 1000);
//        Button data = new Button();
//        Printer printer = new Printer("======= ET D Latch =======");
//        CompoundGate etDLatch = createETDFlipFlop();
//
//        clock.connection(etDLatch.input("clock"));
//        clock.connection(clockLED);
//
//        data.connection(etDLatch.input("data"));
//
//        etDLatch.output("out").connection(printer);
//
//        Simulation.simWait(2500);
//        if (!clock.getState())
//            clock.toggle();
//        if (!clockLED.getState())
//            clockLED.toggle();
//        System.out.println("Start");
//
//        // Making sure clock is on
//        while (!clock.getState()) {}
//        Simulation.simWait(800);
//        System.out.println("Pressed data");
//        data.press(1300);
//        Simulation.simWait(1300);
//        System.out.println("Depressed data");
//
//        Simulation.simWait(2000);
//
//        clock.toggle();
//        clockLED.toggle();
//        // WORKS

//        // Testing complete 1 bit register
//
//        Button data = new Button();
//        Button store = new Button();
//        Clock clock = new Clock(10, 10);
//
//        CompoundGate register = createRegister();
//
//        Printer printer = new Printer("Register");
//
//        data.connection(register.input("data"));
//        store.connection(register.input("store"));
//        clock.connection(register.input("clock"));
//
//        register.output("out").connection(printer);
//
//        Simulation.simWait(2500);
//        clock.toggle();
//
//        data.press(200);
//        store.press(150);
//        Simulation.simWait(2000);
//
//        data.press(200);
//        store.press(150);
//        Simulation.simWait(2000);
//
//        store.press(150);
//        Simulation.simWait(2000);
//
//        store.press(150);
//        Simulation.simWait(2000);
//
//        data.press(200);
//        store.press(150);
//        Simulation.simWait(2000);
//
//        data.press(200);
//        store.press(150);
//        Simulation.simWait(2000);
//
//        store.press(150);
//        Simulation.simWait(2000);
//
//        store.press(150);
//        Simulation.simWait(2000);
//
//        clock.toggle();
//        // WORKS

        // Testing complete 4 bit register

        System.out.println("4 BIT REGISTER\n");

        DebugButton b0 = new DebugButton();
        DebugButton b1 = new DebugButton();
        DebugButton b2 = new DebugButton();
        DebugButton b3 = new DebugButton();

        DebugButton store = new DebugButton();

        Clock clock = new Clock(10, 10);

        CompoundGate register = create4bitRegister();

        DebugLED o0 = new DebugLED("0", 1000);
        DebugLED o1 = new DebugLED("1", 1000);
        DebugLED o2 = new DebugLED("2", 1000);
        DebugLED o3 = new DebugLED("3", 1000);

        DebugLED separator = new DebugLED("=============================", 1000);

        b0.connection(register.input("input0"));
        b1.connection(register.input("input1"));
        b2.connection(register.input("input2"));
        b3.connection(register.input("input3"));

        store.connection(register.input("store"));
        clock.connection(register.input("clock"));

        register.output("output0").connection(o0);
        register.output("output1").connection(o1);
        register.output("output2").connection(o2);
        register.output("output3").connection(o3);

        clock.toggle();

        store.press(250);
        Simulation.simWait(500);

        o3.toggle();
        Simulation.simWait(25);
        o2.toggle();
        Simulation.simWait(25);
        o1.toggle();
        Simulation.simWait(25);
        o0.toggle();
        Simulation.simWait(25);
        separator.toggle();

        Simulation.simWait(2500);

        b1.press(100);
        b3.press(100);
        System.out.println("Pressed 1 & 3");

        store.press(50);
        System.out.println("Pressed store");

        Simulation.simWait(50);
        System.out.println("Let go of store");
        Simulation.simWait(50);
        System.out.println("Let go of 1 & 3");

        Simulation.simWait(2000);

        store.press(50);
        System.out.println("Pressed store");
        Simulation.simWait(50);
        System.out.println("Let go of store");

        Simulation.simWait(2000);

        separator.toggle();
        o0.toggle();
        o1.toggle();
        o2.toggle();
        o3.toggle();

        clock.toggle();
    }

    private static CompoundGate create4bitRegister() {
        HashSet<Compoundable> gates = new HashSet<>();
        HashSet<CompoundGate> compoundGates = new HashSet<>();

        VirtualIO i1 = new VirtualIO("input0");
        VirtualIO i2 = new VirtualIO("input1");
        VirtualIO i3 = new VirtualIO("input2");
        VirtualIO i4 = new VirtualIO("input3");

        VirtualIO store = new VirtualIO("store");
        VirtualIO clock = new VirtualIO("clock");

        CompoundGate r1 = createRegister();
        CompoundGate r2 = createRegister();
        CompoundGate r3 = createRegister();
        CompoundGate r4 = createRegister();

        VirtualIO o1 = new VirtualIO("output0");
        VirtualIO o2 = new VirtualIO("output1");
        VirtualIO o3 = new VirtualIO("output2");
        VirtualIO o4 = new VirtualIO("output3");

        store.connection(r1.input("store"));
        store.connection(r2.input("store"));
        store.connection(r3.input("store"));
        store.connection(r4.input("store"));

        clock.connection(r1.input("clock"));
        clock.connection(r2.input("clock"));
        clock.connection(r3.input("clock"));
        clock.connection(r4.input("clock"));

        i1.connection(r1.input("data"));
        i2.connection(r2.input("data"));
        i3.connection(r3.input("data"));
        i4.connection(r4.input("data"));

        r1.output("out").connection(o1);
        r2.output("out").connection(o2);
        r3.output("out").connection(o3);
        r4.output("out").connection(o4);

        gates.add(i1);
        gates.add(i2);
        gates.add(i3);
        gates.add(i4);

        gates.add(store);
        gates.add(clock);

        gates.add(o1);
        gates.add(o2);
        gates.add(o3);
        gates.add(o4);

        compoundGates.add(r1);
        compoundGates.add(r2);
        compoundGates.add(r3);
        compoundGates.add(r4);

        return new CompoundGate(gates, compoundGates);
    }

    private static CompoundGate createRegister() {
        HashSet<CompoundGate> compoundGates = new HashSet<>();
        HashSet<Compoundable> gates = new HashSet<>();

        VirtualIO data = new VirtualIO("data");
        VirtualIO store = new VirtualIO("store");
        VirtualIO clock = new VirtualIO("clock");

        NOT not = new NOT();

        AND and1 = new AND();
        AND and2 = new AND();

        OR or = new OR();

        CompoundGate dFlipFlop = createETDFlipFlop();

        VirtualIO out = new VirtualIO("out");

        data.connection(and2);

        store.connection(and2);
        store.connection(not);

        clock.connection(dFlipFlop.input("clock"));

        not.connection(and1);

        and1.connection(or);
        and2.connection(or);

        or.connection(dFlipFlop.input("data"));

        dFlipFlop.output("out").connection(and1);
        dFlipFlop.output("out").connection(out);

        gates.add(data);
        gates.add(store);
        gates.add(clock);
        gates.add(not);
        gates.add(and1);
        gates.add(and2);
        gates.add(or);
        gates.add(out);

        compoundGates.add(dFlipFlop);

        return new CompoundGate(gates, compoundGates);
    }

    private static CompoundGate createETDFlipFlop() {
        HashSet<Compoundable> gates = new HashSet<>();
        HashSet<CompoundGate> compoundGates = new HashSet<>();

        VirtualIO data = new VirtualIO("data");
        VirtualIO clock = new VirtualIO("clock");
        VirtualIO out = new VirtualIO("out");
        NOT not = new NOT();

        CompoundGate dLatch1 = createDLatch();
        CompoundGate dLatch2 = createDLatch();

        data.connection(dLatch1.input("data"));

        not.connection(dLatch1.input("store"));

        dLatch1.output("out").connection(dLatch2.input("data"));

        clock.connection(not);
        clock.connection(dLatch2.input("store"));

        dLatch2.output("out").connection(out);

        gates.add(data);
        gates.add(clock);
        gates.add(out);
        gates.add(not);

        compoundGates.add(dLatch1);
        compoundGates.add(dLatch2);

        return new CompoundGate(gates, compoundGates);
    }

    private static CompoundGate createDLatch() {
        HashSet<Compoundable> gates = new HashSet<>();
        HashSet<CompoundGate> compoundGates = new HashSet<>();

        AND top = new AND();
        AND bottom = new AND();
        NOT not = new NOT();

        CompoundGate srLatch = createSRLatch();

        VirtualIO data = new VirtualIO("data");
        VirtualIO store = new VirtualIO("store");

        VirtualIO out = new VirtualIO("out");

        srLatch.output("out").connection(out);
        top.connection(srLatch.input("set"));
        bottom.connection(srLatch.input("reset"));
        not.connection(bottom);
        store.connection(top);
        store.connection(bottom);
        data.connection(top);
        data.connection(not);

        gates.add(top);
        gates.add(bottom);
        gates.add(not);
        gates.add(data);
        gates.add(store);
        gates.add(out);
        compoundGates.add(srLatch);

        return new CompoundGate(gates, compoundGates);
    }

    private static CompoundGate createSRLatch() {
        HashSet<Compoundable> gates = new HashSet<>();

        NOR top = new NOR();
        NOR bottom = new NOR();
        VirtualIO set = new VirtualIO("set");
        VirtualIO reset = new VirtualIO("reset");
        VirtualIO out = new VirtualIO("out");

        top.connection(bottom);
        bottom.connection(top);

        set.connection(top);
        reset.connection(bottom);

        bottom.connection(out);

        gates.add(top);
        gates.add(bottom);
        gates.add(set);
        gates.add(reset);
        gates.add(out);

        return new CompoundGate(gates);
    }
}
