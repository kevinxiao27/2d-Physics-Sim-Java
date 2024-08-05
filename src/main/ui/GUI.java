package ui;

import model.Collider;
import model.Event;
import model.EventLog;
import model.Simulation;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

// Graphical User Interface for Simulation built using Swing
public class GUI extends JFrame implements ActionListener {
    private Simulation sim;
    private JPanel listPanel;
    private JPanel simPanel;
    private JPanel persistence;
    private JPanel collidersPanel;
    private JPanel collidersComponentPanel;
    private SimulationPanel sm;

    // EFFECTS: instantiates new GUI
    public GUI() {
        super("Colliders 2d Simulation App");
        sim = new Simulation();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(Constants.GUI_SIZE);
        pack();
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        addGuiComponents();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Iterator<Event> events = EventLog.getInstance().iterator();
                for (Iterator<Event> it = events; it.hasNext(); ) {
                    Event ev = it.next();
                    System.out.println(ev.getDate() + " at: " + ev.getDescription());

                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: sets up all GUI components in JFrame
    private void addGuiComponents() {
        setUpListPanel();
        setUpBanner();
        setUpCollidersList();
        setUpAddColliderButton();
        setUpSimulationPanel();
        setUpUtilityButtons();
    }

    // MODIFIES: this
    // EFFECTS: sets up simulation panel on right side of GUI
    private void setUpSimulationPanel() {
        simPanel = new JPanel();
        simPanel.setPreferredSize(Constants.LIST_COMPONENT_SIZE);
        simPanel.setLayout(new BoxLayout(simPanel, BoxLayout.Y_AXIS));
        JPanel filler = new JPanel();
        filler.setPreferredSize(Constants.COLLIDER_FIELD_SIZE);
        simPanel.add(filler);
        sm = new SimulationPanel(sim);
        simPanel.add(sm);
        this.getContentPane().add(simPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets up simulation utility panel buttons
    private void setUpUtilityButtons() {
        persistence = new JPanel();
        persistence.setPreferredSize(Constants.COLLIDER_PERSISTENCE);
        persistence.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        simPanel.add(persistence);

        addButtons();
    }

    // MODIFIES: this
    // EFFECTS: draws buttons involving persistence and playing the simulation
    private void addButtons() {
        JButton save = new JButton("Save Simulation");
        JButton load = new JButton("Load Simulation");
        JButton tickButton = new JButton("Tick Simulation");
        save.setBounds(0,
                10,
                Constants.TICK_SIMULATION_BUTTON.width / 2,
                Constants.TICK_SIMULATION_BUTTON.height / 2);
        load.setBounds(10,
                10,
                Constants.TICK_SIMULATION_BUTTON.width / 2,
                10);
        tickButton.setBounds((Constants.GUI_SIZE.width - Constants.LIST_COMPONENT_SIZE.width
                        - Constants.TICK_SIMULATION_BUTTON.width) / 2,
                80,
                Constants.TICK_SIMULATION_BUTTON.width,
                Constants.TICK_SIMULATION_BUTTON.height);
        save.addActionListener(this);
        load.addActionListener(this);
        tickButton.addActionListener(this);
        persistence.add(save);
        persistence.add(load);
        persistence.add(tickButton);
    }

    // MODIFIES: this
    // EFFECTS: sets up listPanel for viewing colliders
    private void setUpListPanel() {
        listPanel = new JPanel();
        listPanel.setPreferredSize(Constants.LIST_COMPONENT_SIZE);
        listPanel.setLayout(null);
        this.getContentPane().add(listPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets up add collider button after values are parsed
    private void setUpAddColliderButton() {
        JButton addColliderButton = new JButton("Add Collider");
        addColliderButton.setBounds((Constants.LIST_COMPONENT_SIZE.width - Constants.ADD_COLLIDER_BUTTON.width) / 2,
                Constants.LIST_COMPONENT_SIZE.height - 88,
                Constants.ADD_COLLIDER_BUTTON.width,
                Constants.ADD_COLLIDER_BUTTON.height);
        listPanel.add(addColliderButton);
        addColliderButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: sets up UI for adding/editing/deleting colliders
    private void setUpCollidersList() {
        collidersPanel = new JPanel();
        collidersComponentPanel = new JPanel();
        collidersComponentPanel.setLayout(new BoxLayout(collidersComponentPanel, BoxLayout.Y_AXIS));
        collidersPanel.add(collidersComponentPanel);

        JScrollPane scrollPane = new JScrollPane(collidersPanel);
        scrollPane.setBounds((Constants.LIST_COMPONENT_SIZE.width - Constants.COLLIDER_PANEL_SIZE.width) / 2, 70,
                Constants.COLLIDER_PANEL_SIZE.width,
                Constants.COLLIDER_PANEL_SIZE.height);
        scrollPane.setMaximumSize(Constants.COLLIDER_PANEL_SIZE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listPanel.add(scrollPane);
    }

    // MODIFIES: this
    // EFFECTS: prints banner
    private void setUpBanner() {
        JLabel bannerLabel = new JLabel("Colliders List");
        bannerLabel.setBounds(
                (Constants.LIST_COMPONENT_SIZE.width - bannerLabel.getPreferredSize().width) / 2,
                15,
                Constants.BANNER_SIZE.width,
                Constants.BANNER_SIZE.height
        );
        listPanel.add(bannerLabel);
    }

    // MODIFIES: this
    // EFFECT: handles action events
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand().toLowerCase();
        switch (command) {
            case "add collider":
                ColliderComponent colliderComponent = new ColliderComponent(collidersComponentPanel, sim, sm);
                collidersComponentPanel.add(colliderComponent);
                colliderComponent.getPosX().requestFocus();
                repaint();
                revalidate();
                break;
            case "load simulation":
                loadSimulation();
                sm.repaint();
                repaint();
                revalidate();
                break;
            case "save simulation":
                saveSimulation();
                break;
            case "tick simulation":
                tick();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: ticks simulation and rerenders simulation
    private void tick() {
        sim.tick(1);
        for (Component c : collidersComponentPanel.getComponents()) {
            collidersComponentPanel.remove(c);
            collidersComponentPanel.repaint();
            collidersComponentPanel.revalidate();
        }
        ArrayList<Collider> colliders = sim.getObjects();
        for (Collider c : colliders) {
            ColliderComponent colComponent = new ColliderComponent(collidersComponentPanel, sim, sm);
            collidersComponentPanel.add(colComponent);
            colComponent.getPosX().setText(String.valueOf(c.getPosX()));
            colComponent.getPosY().setText(String.valueOf(c.getPosY()));
            colComponent.getDeltaX().setText(String.valueOf(c.getDeltaX()));
            colComponent.getDeltaY().setText(String.valueOf(c.getDeltaY()));
            colComponent.setCollider(c);
        }
        sm.repaint();
        revalidate();
    }

    // MODIFIES: none
    // EFFECTS: saves to json file
    private void saveSimulation() {
        JsonWriter writer = new JsonWriter("./data/simulationData.json");
        try {
            writer.open();
            writer.write(sim);
            writer.close();
            System.out.println("Data successfully written to file");
        } catch (Exception ex2) {
            System.out.println("Failed to save simulation");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads from json file data
    private void loadSimulation() {
        JsonReader reader = new JsonReader("./data/simulationData.json");
        try {
            sim = reader.read();
            System.out.println("Data Successfully loaded");
        } catch (Exception ex) {
            System.out.println("Failed to load simulation");
        }

        for (Component c : collidersComponentPanel.getComponents()) {
            collidersComponentPanel.remove(c);
            collidersComponentPanel.repaint();
            collidersComponentPanel.revalidate();
        }
        ArrayList<Collider> colliders = sim.getObjects();
        for (Collider c : colliders) {
            ColliderComponent colComponent = new ColliderComponent(collidersComponentPanel, sim, sm);
            collidersComponentPanel.add(colComponent);
            colComponent.getPosX().setText(String.valueOf(c.getPosX()));
            colComponent.getPosY().setText(String.valueOf(c.getPosY()));
            colComponent.getDeltaX().setText(String.valueOf(c.getDeltaX()));
            colComponent.getDeltaY().setText(String.valueOf(c.getDeltaY()));
            colComponent.setCollider(c);
        }

        sm.setSim(sim);
    }
}