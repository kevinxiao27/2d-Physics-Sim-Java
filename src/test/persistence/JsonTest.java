package persistence;

import model.Collider;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkCollider(Collider obj, float x, float y, float dx, float dy) {
        assertEquals(obj.getPosX(), x);
        assertEquals(obj.getPosY(), y);
        assertEquals(obj.getDeltaX(), dx);
        assertEquals(obj.getDeltaY(), dy);
    }
}
