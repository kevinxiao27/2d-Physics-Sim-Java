package model;

import org.joml.Vector2d;
import org.json.JSONObject;
import persistence.Writable;
import ui.Constants;

// An object is a class that has preset functionality as follows
// An object will have a x and y position, and a respective x and y velocity; all are integers
// An object will have a mass attribute, which is an integer
// An object will have a canBreak attribute, which is a boolean
// NOTE: colliders have assumed same mass, which helps simplify new velocity calculations
public class Collider implements Writable {
    private static final int RADIUS = 3;
    private float posX;
    private float posY;
    private float deltaX;
    private float deltaY;
    private static final float WIDTH_BOUND = (float) Constants.SIM_PANEL_SIZE.width / 10;
    private static final float HEIGHT_BOUND = (float) Constants.SIM_PANEL_SIZE.height / 10;

    // EFFECTS: instantiates new Object instance with default fields
    public Collider() {
        posX = 0;
        posY = 0;
        deltaX = 0;
        deltaY = 0;
    }

    // REQUIRES: id has to be unique
    // EFFECTS: instantiates new Object instance with inputted arguments
    public Collider(float x, float y, float dx, float dy) {
        posX = x;
        posY = y;
        deltaX = dx;
        deltaY = dy;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getDeltaX() {
        return deltaX;
    }

    public float getDeltaY() {
        return deltaY;
    }

    // MODIFIES: this
    // EFFECTS: updates position of object for one tick, x += dx and y += dy
    public void tick() {
        posX += deltaX;
        posY += deltaY;
        handleCollision();
    }

    public void handleCollision() {
        if (posX < 0) {
            posX = 0;
            deltaX *= -1;
            EventLog.getInstance().logEvent(new Event("Handled left boundary collision of: "
                    + this));
        } else if (posX > WIDTH_BOUND) {
            posX = WIDTH_BOUND;
            deltaX *= -1;
            EventLog.getInstance().logEvent(new Event("Handled right boundary collision of: "
                    + this));
        }

        if (posY < 0) {
            posY = 0;
            deltaY *= -1;
            EventLog.getInstance().logEvent(new Event("Handled top boundary collision of: "
                    + this));
        } else if (posY > HEIGHT_BOUND) {
            posY = HEIGHT_BOUND;
            deltaY *= -1;
            EventLog.getInstance().logEvent(new Event("Handled bottom boundary collision of: "
                    + this));
        }
    }

    // MODIFIES: this
    // EFFECTS: alters position of an object to x and y
    public void move(float ax, float ay) {
        posX = ax;
        posY = ay;
        EventLog.getInstance().logEvent(new Event("Changed position of: "
                + this + "to x:" + ax + " to y: " + ay));
    }

    public void setVelocity(float dx, float dy) {
        deltaX = dx;
        deltaY = dy;
        EventLog.getInstance().logEvent(new Event("Changed velocity of: "
                + this + "to dx:" + dx + " to dy: " + dy));
    }

    // MODIFIES: this
    // EFFECTS: alters velocity of an object by ax and ay
    public void accelerate(float ax, float ay) {
        deltaX += ax;
        deltaY += ay;
    }

    public Boolean isColliding(Collider c) {
        double distance = Math.sqrt(Math.pow((c.getPosX() - posX), 2) + Math.pow((c.getPosY() - posY), 2));
        return distance <= RADIUS * 2;
    }

    // REQUIRES: isColliding()
    // MODIFIES: this and collider
    // EFFECTS: approximates new collision velocities of two colliders
    public void collide(Collider c) {
        Vector2d c1 = new Vector2d(posX, posY);
        Vector2d c2 = new Vector2d(c.getPosX(), c.getPosY());
        Vector2d tempVec1 = new Vector2d();
        Vector2d tempVec2 = new Vector2d();
        Vector2d tempVec3 = new Vector2d();
        Vector2d v1 = new Vector2d(deltaX, deltaY);
        Vector2d v2 = new Vector2d(c.getDeltaX(), c.getDeltaY());


        v1.sub(v2, tempVec1);
        c1.sub(c2, tempVec2);

        Vector2d v1n = new Vector2d();
        tempVec2.mul((tempVec1.dot(tempVec2) / tempVec2.lengthSquared()), tempVec3);
        v1.sub(tempVec3, v1n);
        deltaX = (float) v1n.x;
        deltaY = (float) v1n.y;

        v2.sub(v1, tempVec1);
        c2.sub(c1, tempVec2);
        Vector2d v2n = new Vector2d();
        tempVec2.mul((tempVec1.dot(tempVec2) / tempVec2.lengthSquared()), tempVec3);
        v2.sub(tempVec3, v2n);
        c.setVelocity((float) v2n.x, (float) v2n.y);
        EventLog.getInstance().logEvent(new Event("Handled collision of " + c + " and " + this));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("posX", posX);
        json.put("posY", posY);
        json.put("deltaX", deltaX);
        json.put("deltaY", deltaY);
        return json;
    }
}
