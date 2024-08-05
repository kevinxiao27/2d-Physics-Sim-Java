package persistence;

import model.Collider;
import model.Simulation;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentPath() {
        JsonReader reader = new JsonReader("./data/doesnotexist.json");
        try {
            Simulation sim = reader.read();
            fail("IOException was not thrown");
        } catch (IOException e) {
        }
    }

    @Test
    void testReaderEmptySimulation() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySimulation.json");
        try {
            Simulation sim = reader.read();
            assertFalse(sim.isPlaying());
            List<Collider> colliders = sim.getObjects();
            assertEquals(colliders.size(), 0);
        } catch (IOException e) {
            fail("Unable to read from the file");
        }
    }

    @Test
    void testReaderGeneralSimulation() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSimulation.json");
        try {
            Simulation sim = reader.read();
            assertFalse(sim.isPlaying());
            List<Collider> colliders = sim.getObjects();
            assertEquals(colliders.size(), 2);
            checkCollider(colliders.get(0), 1.03f,-1.32f,1.30f,2.3f);
            checkCollider(colliders.get(1), 1.0f,-1.0f,1.34f,2.35f);
        } catch (IOException e) {
            fail("Unable to read from the file");
        }
    }
}
