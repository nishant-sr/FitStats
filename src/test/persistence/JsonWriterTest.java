//Modeled after JsonSerializationDemo from edX

package persistence;

import model.Exercise;
import model.ExerciseCollection;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {

    @Test
    public void writeInvalidFile() {
        try {
            ExerciseCollection exc = new ExerciseCollection();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.openfile();
            fail("Why no exception?");
        } catch (FileNotFoundException e) {
            System.out.println("Success");
        }
    }

    @Test
    public void writeValidFile() {

        try {
            ExerciseCollection exc = new ExerciseCollection();
            exc.insertEx(new Exercise("DL", 1, 5, 1, 1, 1));
            JsonWriter writer = new JsonWriter("./data/testfile2.json");

            writer.openfile();
            writer.write(exc);
            writer.closefile();

            JsonReader reader1 = new JsonReader("./data/testfile2.json");

            ExerciseCollection exc1 = reader1.read();
            Exercise ex2 = exc1.retrieve(1);
            assertEquals(1, ex2.getStartWeight());

        } catch (FileNotFoundException e) {
            fail("Tsktsk");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
