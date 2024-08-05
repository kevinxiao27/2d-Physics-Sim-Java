package ui;

import model.Collider;
import model.Simulation;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

// console based ui: credit to TellerApp for inspiration
public class SimulationApp {
    private Simulation sim;
    private Scanner input;
    private String cmd;

    //EFFECTS: instantiates new App instance
    public SimulationApp() {
        sim = new Simulation();
        runSimulation();
    }

    // EFFECTS: begins running command line simulation app based on user inputs
    public void runSimulation() {
        Boolean isRunning = true;
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        while (isRunning) {
            displayOptions();
            cmd = input.next().toLowerCase();

            if (cmd.equals("q")) {
                isRunning = false;
            } else {
                handleInput(cmd);
            }
        }

        System.out.println("Simulation Ended");
    }

    // EFFECTS: provides decision tree logic based on user input
    private void handleInput(String input) {
        switch (input) {
            case "l":
                loadSimulation();
                break;
            case "w":
                writeSimulation();
                break;
            case "e":
                editSimulation();
                break;
            case "p":
                runScene();
                break;
            default:
                System.out.println("Not a valid input");
        }
    }

    // EFFECTS: provides editor decision tree logic based on user input
    private void editSimulation() {
        displayEditorOptions();
        cmd = input.next().toLowerCase();
        switch (cmd) {
            case "a":
                handleAddObject();
                break;
            case "e":
                handleEditObject();
                break;
            case "r":
                handleRemoveObject();
                break;
            case "b":
                System.out.println("Back to Main Menu");
                return;
            default:
                System.out.println("Not a valid input");
        }
        editSimulation();

    }

    // MODIFIES: this
    // EFFECTS: adds a new object to simulation being used in the app
    private void handleAddObject() {
        System.out.println("Enter your Object Parameters in the order x, y, dx, dy");
        try {
            System.out.println("Enter your X value:");
            float x = input.nextFloat();
            System.out.println("Enter your Y value:");
            float y = input.nextFloat();
            System.out.println("Enter your DX value:");
            float dx = input.nextFloat();
            System.out.println("Enter your DY value:");
            float dy = input.nextFloat();

            Collider tempCollider = new Collider(x, y, dx, dy);
            if (sim.addObject(tempCollider)) {
                System.out.println("Object Added Successfully");
            } else {
                System.out.println("Object is clipping an existing object");
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
        }
    }

    // REQUIRES: sim.getObjects().size() > 0
    // EFFECTS: returns a selected object from index or null
    private Collider selectObject() throws Exception {
        int objSelect;
        int max = sim.getObjects().size();
        if (max == 0) {
            System.out.println("No Objects in Simulation");
            throw new Exception("Null Object");
        }
        System.out.println("Select any object from the following by typing their index in the range [1," + max + "]");
        System.out.println("Type 0 to Cancel");
        printObjects();

        while (true) {
            Collider currentCollider;
            objSelect = input.nextInt();
            if (1 <= objSelect && objSelect <= max) {
                --objSelect; // -- to account for zero indexing
                currentCollider = sim.getObject(objSelect);
                return currentCollider;
            } else if (objSelect == 0) {
                throw new Exception("Null Object");
            }
            System.out.println("Invalid Selection");
        }
    }

    // MODIFIES: this
    // EFFECTS: handles when edit object option is selected
    private void handleEditObject() {
        Collider currentCollider;
        try {
            currentCollider = selectObject();
        } catch (Exception e) {
            System.out.println("Back to main menu.");
            return;
        }
        editObject(currentCollider);
    }

    // MODIFIES: this
    // EFFECTS: handles when remove object option is selected
    private void handleRemoveObject() {
        Collider currentCollider;
        try {
            currentCollider = selectObject();
        } catch (Exception e) {
            return;
        }
        sim.removeObject(currentCollider);
        System.out.println("Object Successfully Removed");
    }

    // REQUIRES: Object is not null
    // MODIFIES: this, obj
    // EFFECTS: changes position of object or accelerates it
    private void editObject(Collider obj) {
        System.out.println("Enter your X value:");
        float x = input.nextFloat();
        System.out.println("Enter your Y value:");
        float y = input.nextFloat();
        System.out.println("Enter your AX value:");
        float ax = input.nextFloat();
        System.out.println("Enter your AY value:");
        float ay = input.nextFloat();
        obj.move(x, y);
        obj.accelerate(ax, ay);
        System.out.println("Editing Complete");
    }

    // MODIFIES: this
    // EFFECTS: ticks the objects for given amount of ticks, and prints out changes to objects with each iteration
    private void runScene() {
        System.out.println("Enter the amount of cycles to tick your simulation");
        input.nextLine();
        int numObjects = sim.getObjects().size();
        if (numObjects == 0) {
            System.out.println("No objects in simulation to run");
            return;
        }
        int x = input.nextInt();
        for (int i = 0; i < x; i++) {
            System.out.println("Iteration: " + (i + 1));
            sim.tick(1); // This will probably refactored into improved functionality in the future with a ui
            printObjects();
        }
    }

    // EFFECTS: prints out options for console UI on home page
    private void displayOptions() {
        System.out.println("\nOptions:");
        System.out.println("\tl -> Load From Save");
        System.out.println("\tw -> Write To Save");
        System.out.println("\te -> Simulation Editor");
        System.out.println("\tp -> Play Simulation");
        System.out.println("\tq -> Quit");
    }

    // EFFECTS: prints out options for console UI on editor page
    private void displayEditorOptions() {
        System.out.println("\nOptions:");
        System.out.println("\ta -> Add Object");
        System.out.println("\te -> Edit Object");
        System.out.println("\tr -> Remove Object");
        System.out.println("\tb -> Back to Main Menu");
    }

    // EFFECTS: prints out formatted version of all objects in console
    private void printObjects() {
        int i = 1;
        for (Collider obj : sim.getObjects()) {
            System.out.println("\t" + i + ".   Pos-X: " + obj.getPosX() + " Pos-Y: " + obj.getPosY()
                    + "\t Delta X: " + obj.getDeltaX() + " Delta Y: " + obj.getDeltaY());
            i++;
        }
    }

    // MODIFIES: this
    // EFFECTS: loads data from JSON file and sets simulation in file to load state
    private void loadSimulation() {
        JsonReader reader = new JsonReader("./data/simulationData.json");
        try {
            sim = reader.read();
            System.out.println("Data Successfully loaded");
        } catch (Exception e) {
            System.out.println("Failed to load simulation");
        }
    }

    private void writeSimulation() {
        JsonWriter writer = new JsonWriter("./data/simulationData.json");
        try {
            writer.open();
            writer.write(sim);
            writer.close();
            System.out.println("Data successfully written to file");
        } catch (Exception e) {
            System.out.println("Failed to save simulation");
        }
    }
}
