package persistence;

import model.Collider;
import model.Event;
import model.EventLog;
import model.Simulation;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads simulation from JSON data
// Credit: JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Simulation read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loaded Simulation"));
        return parseSimulation(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses simulation from JSON object and returns it
    private Simulation parseSimulation(JSONObject jsonObject) {
        Boolean isPlaying = jsonObject.getBoolean("isPlaying");
        Simulation sim = new Simulation(isPlaying);
        addColliders(sim, jsonObject);
        return sim;
    }

    // MODIFIES: sim
    // EFFECTS: parses colliders from JSON object and adds them to workroom
    private void addColliders(Simulation sim, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("colliders");
        for (Object json : jsonArray) {
            JSONObject nextCollider = (JSONObject) json;
            addCollider(sim, nextCollider);
        }
    }

    // MODIFIES: sim
    // EFFECTS: parses collider from JSON object and adds it to workroom
    private void addCollider(Simulation sim, JSONObject jsonObject) {
        float posX = jsonObject.getFloat("posX");
        float posY = jsonObject.getFloat("posY");
        float deltaX = jsonObject.getFloat("deltaX");
        float deltaY = jsonObject.getFloat("deltaY");
        Collider obj = new Collider(posX, posY, deltaX, deltaY);
        sim.addObject(obj);
    }
}
