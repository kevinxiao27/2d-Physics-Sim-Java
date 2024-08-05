package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Simulation that houses all collider information and handles collision detection and handling
public class Simulation implements Writable {
    private ArrayList<Collider> colliders;
    private boolean isPlaying;


    // EFFECTS: creates new Simulation instance
    public Simulation() {
        colliders = new ArrayList<Collider>();
        isPlaying = false;
        EventLog.getInstance().logEvent(new Event("Instantiated new Simulation with isPlaying = false"));
    }

    public Simulation(Boolean play) {
        colliders = new ArrayList<Collider>();
        isPlaying = play;
        EventLog.getInstance().logEvent(new Event("Instantiated new Simulation with isPlaying = "
                + isPlaying));
    }

    // EFFECTS: isPlaying getter;
    public boolean isPlaying() {
        return isPlaying;
    }

    public ArrayList<Collider> getObjects() {
        return colliders;
    }

    // MODIFIES: this
    // EFFECTS: if an object does not clip other objects, it is added and returns true
    //          otherwise it will return false to indicate success or failure
    public boolean addObject(Collider obj) {
        if (isClipping(obj)) {
            EventLog.getInstance().logEvent(new Event("Failed to add collider as it clips"));
            return false;
        }
        EventLog.getInstance().logEvent(new Event("Successfully added new collider:" + obj));
        colliders.add(obj);
        return true;
    }

    // REQUIRES: objectList is not empty, curObj != null
    // EFFECTS: returns true if an object is clipping any objects in the list already
    public boolean isClipping(Collider curObj) {
        for (Collider obj : colliders) {
            if (obj.getPosX() == curObj.getPosX() && obj.getPosY() == curObj.getPosY()) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: curObj exists in objectList
    // MODIFIES: this
    // EFFECTS: removes selected objected from objectList
    public void removeObject(Collider obj) {
        EventLog.getInstance().logEvent(new Event("Removed collider: " + obj));
        colliders.remove(obj);
    }

    // REQUIRES: curObj exists in objectList
    // EFFECTS: return object at a specific index
    public Collider getObject(int index) {
        return colliders.get(index);
    }

    // REQUIRES: ticks >= 0
    // MODIFIES: this
    // EFFECTS: updates all objects by given amount of ticks
    public void tick(int ticks) {
        for (int i = 0; i < ticks; i++) {
            for (int j = 0; j < colliders.size(); j++) {
                Collider colliderJ = colliders.get(j);
                for (int k = j + 1; k < colliders.size(); k++) {
                    if (colliderJ.isColliding(colliders.get(k))) {
                        colliderJ.collide(colliders.get(k));
                    }
                }
            }
            for (Collider c : colliders) {
                c.tick();
            }
        }
        EventLog.getInstance().logEvent(new Event("Ticked Simulation: " + ticks + " times"));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("isPlaying", isPlaying);
        json.put("colliders", collidersToJson());
        return json;
    }

    // EFFECTS: returns colliders in the workroom as a JSON array
    private JSONArray collidersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Collider c : colliders) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
