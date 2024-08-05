package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColliderTest {
    private Collider defaultObj;
    private Collider collider;
    private Collider collider2;
    private Collider collider3;

    @BeforeEach
    public void setUp() {
        defaultObj = new Collider();
        collider = new Collider(10, 20, 3.1f, 5);
        collider2 = new Collider(10.5f, 20.5f, 3.2f, 5.7f);
        collider3 = new Collider();
    }

    @Test
    public void testSetVelocity() {
        collider.setVelocity(3,-4.2f);
        assertEquals(3, collider.getDeltaX(), 0.02);
        assertEquals(-4.2, collider.getDeltaY(), 0.02);
    }

    @Test
    public void testTick() {
        collider.tick();
        assertEquals(13.1, collider.getPosX(), 0.002);
        assertEquals(25, collider.getPosY());
    }

    @Test
    public void testMove() {
        collider.move(2, 4);
        assertEquals(2, collider.getPosX(), 0.002);
        assertEquals(4, collider.getPosY(), 0.002);

        collider.move(0, -4.3f);
        assertEquals(0, collider.getPosX(), 0.002);
        assertEquals(-4.3, collider.getPosY(), 0.002);
    }

    @Test
    public void testIsColliding() {
        Collider collider1 = new Collider(0, 0, 0, 0);
        Collider collider2 = new Collider(6, 0, 0, 0); // Colliders with a distance of 6, but radius is 3

        assertTrue(collider1.isColliding(collider2));
    }

    @Test
    public void testTick_PositiveVelocity() {
        collider3.setVelocity(2, -3); // Setting positive velocity
        collider3.move(-1, -1); // Move collider3 to position (-1, -1)

        // Expected behavior: posX and posY should be 0 after tick()
        collider3.tick();
        assertEquals(1, collider3.getPosX());
        assertEquals(0, collider3.getPosY());

        // Expected behavior: deltaX and deltaY should be negated after collision
        assertEquals(2, collider3.getDeltaX());
        assertEquals(3, collider3.getDeltaY());
    }

    @Test
    public void testTick_NegativePosX() {
        collider3.setVelocity(-2, -3); // Setting positive velocity
        collider3.move(-1, -1); // Move collider3 to position (-1, -1)

        // Expected behavior: posX and posY should be 0 after tick()
        collider3.tick();
        assertEquals(0, collider3.getPosX());
        assertEquals(0, collider3.getPosY());

        // Expected behavior: deltaX and deltaY should be negated after collision
        assertEquals(2, collider3.getDeltaX());
        assertEquals(3, collider3.getDeltaY());
    }

    @Test
    public void testTick_NegativeVelocity() {
        collider3.setVelocity(2, 3); // Setting negative velocity
        collider3.move(405, 305); // Move collider3 to position (405, 305) beyond bounds

        // Expected behavior: posX and posY should be WIDTH_BOUND and HEIGHT_BOUND respectively after tick()
        collider3.tick();
        assertEquals(40, collider3.getPosX());
        assertEquals(30, collider3.getPosY());

        // Expected behavior: deltaX and deltaY should be negated after collision
        assertEquals(-2, collider3.getDeltaX());
        assertEquals(-3, collider3.getDeltaY());
    }

    @Test
    public void testTick_NoCollision() {
        collider3.setVelocity(2, 3); // Setting positive velocity
        collider3.move(50, 50); // Move collider3 to position (50, 50)

        // Expected behavior: posX and posY should be incremented by deltaX and deltaY respectively
        collider3.tick();
        assertEquals(40, collider3.getPosX());
        assertEquals(30, collider3.getPosY());
    }

    @Test
    public void testCollide() {
        Collider collider1 = new Collider(1, 3, 1, 0); // Moving right with velocity 1
        Collider collider2 = new Collider(2, 2, -1, 1); // Stationary at x=4

        collider1.collide(collider2);

        assertEquals(-0.5, collider1.getDeltaX());
        assertEquals(1.5, collider1.getDeltaY());
        assertEquals(0.5, collider2.getDeltaX());
        assertEquals(-0.5, collider2.getDeltaY());
    }

    @Test
    public void testAccelerate() {
        collider.accelerate(2, 4);
        assertEquals(5.1, collider.getDeltaX(), 0.002);
        assertEquals(9, collider.getDeltaY(), 0.002);
    }

    @Test
    public void testToJson() {
        JSONObject json = collider2.toJson();

        assertEquals(10.5f, json.getFloat("posX"));
        assertEquals(20.5f, json.getFloat("posY"));
        assertEquals(3.2f, json.getFloat("deltaX"));
        assertEquals(5.7f, json.getFloat("deltaY"));
    }
}