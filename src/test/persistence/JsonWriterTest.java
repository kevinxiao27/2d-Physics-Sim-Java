package persistence;

import org.junit.jupiter.api.Test;

import model.Collider;
import model.Simulation;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest{
    @Test
    void testWriterInvalidFile() {
        try {
            Simulation sim = new Simulation();
            Collider obj = new Collider(0,1,3,4.33f);
            sim.addObject(obj);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Simulation sim = new Simulation(true);
            Simulation sim2;
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySimulation.json");
            writer.open();
            writer.write(sim);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySimulation.json");
            sim2 = reader.read();
            assertEquals(0, sim2.getObjects().size());
            assertTrue(sim2.isPlaying());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Simulation sim = new Simulation();
            Simulation sim2;
            Collider obj1 = new Collider(0,1,3,4.33f);
            Collider obj2 = new Collider(1,1,3,4.33f);

            sim.addObject(obj1);
            sim.addObject(obj2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSimulation.json");
            writer.open();
            writer.write(sim);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralSimulation.json");
            sim2 = reader.read();
            assertEquals(2, sim2.getObjects().size());
            assertFalse(sim2.isPlaying());
            checkCollider(sim2.getObjects().get(0), 0,1,3,4.33f);
            checkCollider(sim2.getObjects().get(1), 1,1,3,4.33f);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
