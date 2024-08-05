package ui;

import model.Collider;
import model.Simulation;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

// SimulationPanel that will house all colliders in a box
public class SimulationPanel extends JPanel {
    private Simulation sim;

    // EFFECTS: instantiates new simulation panel featuring all colliders
    public SimulationPanel(Simulation sim) {
        this.sim = sim;
        setPreferredSize(Constants.SIM_PANEL_SIZE);
        setBackground(Color.gray);
    }

    // EFFECTS: simulation setter
    public void setSim(Simulation sim) {
        this.sim = sim;
    }

    // EFFECTS: custom component painting to draw colliders
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawSim(g);
    }

    // MODIFIES: this
    // EFFECTS: draws colliders onto canvas
    private void drawSim(Graphics g) {
        ArrayList<Collider> colliders = sim.getObjects();
        for (Collider c : colliders) {
            drawCollider(g, c);
        }
    }

    // EFFECTS: draws individual collider onto canvas
    private void drawCollider(Graphics g, Collider c) {
        Color savedCol = g.getColor();
        g.setColor(Color.green);
        g.fillOval((int) c.getPosX() * 10, (int) c.getPosY() * 10, 30, 30);
        g.setColor(savedCol);
    }
}
