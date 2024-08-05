package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {

    private Simulation simulation;
    private Simulation constTest;
    private Collider obj1;
    private Collider obj2;

    @BeforeEach
    public void setUp() {
        simulation = new Simulation();
        constTest = new Simulation(true);
        obj1 = new Collider(10, 20, 3, 5);
        obj2 = new Collider(5, 10, 2, 4);
    }

    protected void checkCollider(Collider obj, float x, float y, float dx, float dy) {
        assertEquals(obj.getPosX(), x);
        assertEquals(obj.getPosY(), y);
        assertEquals(obj.getDeltaX(), dx);
        assertEquals(obj.getDeltaY(), dy);
    }

    @Test
    public void testConstructor() {
        assertNotNull(simulation.getObjects());
        assertTrue(simulation.getObjects().isEmpty());
        assertFalse(simulation.isPlaying());
        assertTrue(constTest.isPlaying());
    }

    @Test
    public void testGetObject() {
        simulation.addObject(obj1);
        simulation.addObject(obj2);
        assertEquals(obj1, simulation.getObject(0));
        assertEquals(obj2, simulation.getObject(1));
    }

    @Test
    public void testAddObject() {
        // Adding an object with the same position should fail
        Collider obj3 = new Collider(10, 20, 1, 1);
        assertTrue(simulation.addObject(obj3));
        assertFalse(simulation.addObject(obj3));
        assertEquals(1, simulation.getObjects().size()); // Size should remain the same
    }

    @Test
    public void testIsClipping() {
        simulation.addObject(obj1);
        simulation.addObject(obj2);
        // Test for an object with the same position
        Collider obj4 = new Collider(10, 20, 1, 1);
        assertTrue(simulation.isClipping(obj4));

        // Test for an object with different position
        Collider obj5 = new Collider(5, 15, 1, 1);
        assertFalse(simulation.isClipping(obj5));
    }

    @Test
    public void testRemoveObject() {
        simulation.addObject(obj1);
        simulation.addObject(obj2);
        simulation.removeObject(obj1);
        assertEquals(1, simulation.getObjects().size());
    }

    @Test
    public void testTick() {
        simulation.addObject(obj1);
        simulation.addObject(obj2);
        simulation.tick(1);

        assertEquals(13, obj1.getPosX());
        assertEquals(25, obj1.getPosY());
        assertEquals(7, obj2.getPosX());
        assertEquals(14, obj2.getPosY());

        simulation.tick(2);

        assertEquals(19, obj1.getPosX());
        assertEquals(30, obj1.getPosY());
        assertEquals(11, obj2.getPosX());
        assertEquals(22, obj2.getPosY());
    }

    @Test public void testCollision() {
        Collider collider1 = new Collider(1, 3, 1, 0);
        Collider collider2 = new Collider(2, 2, -1, 1);

        simulation.addObject(collider1);
        simulation.addObject(collider2);

        simulation.tick(1);

        assertEquals(-0.5, collider1.getDeltaX());
        assertEquals(1.5, collider1.getDeltaY());
        assertEquals(0.5, collider2.getDeltaX());
        assertEquals(-0.5, collider2.getDeltaY());

        checkCollider(collider1, 0.5f, 4.5f, -0.5f, 1.5f);
        checkCollider(collider2, 2.5f, 1.5f, 0.5f, -0.5f);
    }

    @Test
    public void testToJson() {
        simulation.addObject(obj1);
        simulation.addObject(obj2);
        JSONObject json = simulation.toJson();

        assertFalse(json.getBoolean("isPlaying"));

        JSONArray collidersJson = json.getJSONArray("colliders");
        assertEquals(2, collidersJson.length());

        JSONObject collider1Json = collidersJson.getJSONObject(0);
        assertEquals(10, collider1Json.getInt("posX"));
        assertEquals(20, collider1Json.getInt("posY"));
        assertEquals(3, collider1Json.getInt("deltaX"));
        assertEquals(5, collider1Json.getInt("deltaY"));

        JSONObject collider2Json = collidersJson.getJSONObject(1);
        assertEquals(5, collider2Json.getInt("posX"));
        assertEquals(10, collider2Json.getInt("posY"));
        assertEquals(2, collider2Json.getInt("deltaX"));
        assertEquals(4, collider2Json.getInt("deltaY"));
    }
}
