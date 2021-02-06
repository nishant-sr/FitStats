//Modeled after JsonSerializationDemo from edX

package persistence;

import model.Exercise;
import model.ExerciseCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {

    @Test
    public void readerNonExistentFile() {
        JsonReader reader = new JsonReader("./data/pig.json");
        try {
            ExerciseCollection exc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            System.out.println("Success");
        }
    }

    @Test
    public void readerExistentFile() {
        JsonReader reader1 = new JsonReader("./data/testfile1.json");
        try {
            ExerciseCollection exc = reader1.read();
            Exercise ex1 = exc.retrieve(1);
            assertEquals(1000, ex1.getStartWeight());
        } catch (IOException e) {
            fail("Exception shouldn't have occured");
        }
    }


}
