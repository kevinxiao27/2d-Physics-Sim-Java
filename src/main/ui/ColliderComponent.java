package ui;

import model.Collider;
import model.Simulation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// component that will enable edit and delete functionality to the application
public class ColliderComponent extends JPanel implements ActionListener {
    private Simulation sim;
    private Collider collider;
    private JButton editOrAddButton;
    private JTextPane posX;
    private JTextPane posY;
    private JTextPane deltaX;
    private JTextPane deltaY;
    private JButton deleteButton;
    private JPanel parentPanel;
    private SimulationPanel sm;

    // EFFECTS: constructs new ColliderComponent
    public ColliderComponent(JPanel parentPanel, Simulation parentSim, SimulationPanel sm) {
        this.parentPanel = parentPanel;
        this.sim = parentSim;
        this.sm = sm;

        setUpFields();
        setUpEditAddButton();
        setUpDeleteButton();
    }

    // MODIFIES: this
    // EFFECTS: adds delete button to component gui
    private void setUpDeleteButton() {
        deleteButton = new JButton("del");
        deleteButton.setPreferredSize(Constants.DELETE_BUTTON_SIZE);
        deleteButton.addActionListener(this);
        add(deleteButton);
    }

    // MODIFIES: this
    // EFFECTS: adds edit/add button to component gui
    private void setUpEditAddButton() {
        editOrAddButton = new JButton("A/E");
        editOrAddButton.setPreferredSize(Constants.ADD_CHECK_SIZE);
        editOrAddButton.addActionListener(this);
        add(editOrAddButton);
    }

    private void setUpFields() {
        posX = new JTextPane();
        posX.setPreferredSize(Constants.COLLIDER_FIELD_SIZE);
        posX.setContentType("text/plain");
        posY = new JTextPane();
        posY.setPreferredSize(Constants.COLLIDER_FIELD_SIZE);
        posY.setContentType("text/plain");
        deltaX = new JTextPane();
        deltaX.setPreferredSize(Constants.COLLIDER_FIELD_SIZE);
        deltaX.setContentType("text/plain");
        deltaY = new JTextPane();
        deltaY.setPreferredSize(Constants.COLLIDER_FIELD_SIZE);
        deltaY.setContentType("text/plain");
        add(posX);
        add(posY);
        add(deltaX);
        add(deltaY);
    }

    public JTextPane getPosX() {
        return posX;
    }

    public JTextPane getPosY() {
        return posY;
    }

    public JTextPane getDeltaX() {
        return deltaX;
    }

    public JTextPane getDeltaY() {
        return deltaY;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    // MODIFIES: this
    // EFFECT: case handling of actions being performed that will enable model functionality
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand().toLowerCase()) {
            case "a/e":
                String xpos = posX.getText();
                String ypos = posY.getText();
                String dx = deltaX.getText();
                String dy = deltaY.getText();
                if (collider != null) {
                    editCollider(xpos, ypos, dx, dy);
                } else {
                    addCollider(xpos, ypos, dx, dy);
                }
                break;

            case "del":
                if (collider != null) {
                    sim.removeObject(collider);
                }
                parentPanel.remove(this);
                parentPanel.repaint();
                parentPanel.revalidate();
                sm.repaint();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: instantiates new collider to be added to simulation based on GUI inputs
    private void addCollider(String x, String y, String deltx, String delty) {
        try {
            float xpos = Float.parseFloat(x);
            float ypos = Float.parseFloat(y);
            float dx = Float.parseFloat(deltx);
            float dy = Float.parseFloat(delty);

            collider = new Collider(xpos, ypos, dx, dy);

            if (sim.addObject(collider)) {
                JOptionPane.showMessageDialog(this, "Collider added successfully");
            } else {
                JOptionPane.showMessageDialog(this, "Collider is clipping existing collider");
                collider = null;
            }
            sm.repaint();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Collider not added");
        }
    }

    // REQUIRES: collider != null
    // MODIFIES: this
    // EFFECTS: instantiates new collider to be added to simulation based on GUI inputs
    private void editCollider(String x, String y, String deltx, String delty) {
        try {
            float xpos = Float.parseFloat(x);
            float ypos = Float.parseFloat(y);
            float dx = Float.parseFloat(deltx);
            float dy = Float.parseFloat(delty);

            collider.move(xpos, ypos);
            collider.setVelocity(dx, dy);
            JOptionPane.showMessageDialog(this, "Successfully edited collider");
            sm.repaint();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Collider not added");
        }
    }
}
