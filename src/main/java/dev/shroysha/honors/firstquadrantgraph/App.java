package dev.shroysha.honors.firstquadrantgraph;

import dev.shroysha.honors.firstquadrantgraph.view.FirstQuadrantGraphFrame;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private final double[] xPoints = {0, 5, 3, 4};
    private final double[] yPoints = {6, 2, 6, 3};


    public App() {
        init();
    }


    public static void main(String[] args) {
        App frame = new App();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void init() {
        setSize(700, 500);
        FirstQuadrantGraphFrame gp = gpInit();

        this.setLayout(new BorderLayout());
        this.add(gp, BorderLayout.CENTER);

        gp.setVisible(true);
    }

    private FirstQuadrantGraphFrame gpInit() {
        FirstQuadrantGraphFrame gp = new FirstQuadrantGraphFrame(this.getWidth(), this.getHeight());
        gp.setBackgroundColor(Color.blue);
        gp.setDrawColor(Color.white);
        gp.setXPoints(xPoints);
        gp.setYPoints(yPoints);
        gp.setTitle("The Title");
        gp.setXAxisIncrements(1.0);
        gp.setYAxisIncrements(2.0);
        gp.setXAxisLabel("Time (s)");
        gp.setYAxisLabel("Array Size (number of values)");
        return gp;
    }

}
