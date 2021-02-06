
package persistence;

import model.Exercise;
import model.ExerciseCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {

    private String source;

    //EFFECTS: make a reader to read the json file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: read and return the ExerciseColl. from the json file
    public ExerciseCollection read() throws IOException {
        String jsonContents = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonContents);
        return parseExerciseCollection(jsonObject);
    }

    //EFFECTS: read and return the json file as a String
    private String readFile(String source) throws IOException {
        StringBuilder fileContent = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> fileContent.append(s));
        }

        return fileContent.toString();
    }

    //Effects: parses ExcerciseCol. from JSON object and returns it
    private ExerciseCollection parseExerciseCollection(JSONObject jsonObject) {
        String name = jsonObject.getString("Name");
        ExerciseCollection exc = new ExerciseCollection();
        addExercises(exc, jsonObject);
        return exc;
    }

    //MODIFIES: exc
    //Effects: adds Exercises to the collection
    private void addExercises(ExerciseCollection exc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Exercises");
        for (Object json : jsonArray) {
            JSONObject nextExercise = (JSONObject) json;
            addExercise(exc, nextExercise);
        }
    }

    //Modifies: exc
    //Effects: adds exercise to the collection
    private void addExercise(ExerciseCollection exc, JSONObject nextExercise) {
        String name = nextExercise.getString("name");
        Integer sw = nextExercise.getInt("start");
        Integer gw = nextExercise.getInt("goal");
        Integer cw = nextExercise.getInt("current");
        Integer sets = nextExercise.getInt("sets");
        Integer reps = nextExercise.getInt("reps");
        Exercise exercise = new Exercise(name, sw, gw, cw, sets, reps);
        exc.insertEx(exercise);
    }


}
