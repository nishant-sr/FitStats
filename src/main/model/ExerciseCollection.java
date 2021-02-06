package model;

import persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;

public class ExerciseCollection extends Component implements Writable {

    public String colname = "FitStats";
    private ArrayList<Exercise> internalArray;

    //Effects: constructs A collection of the exercises being kept track of
    public ExerciseCollection() {
        internalArray = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: inserts an exercise ex to the collection
    public void insertEx(Exercise ex) {
        if (!internalArray.contains(ex)) {
            internalArray.add(ex);
        }
    }

    //MODIFIES: this
    //EFFECTS: removes an exercise ex from the collection
    public void removeEx(Exercise ex) {
        internalArray.remove(ex);
    }

    //MODIFIES: this
    //EFFECTS: removes the exercise at posiiton i in the collection
    // when viewed in the UI
    public void removeExByNum(int i) throws IndexOutOfBoundsException {
        internalArray.remove(i - 1);
    }

    //EFFECTS: returns whether or not the exercise ex is in the collection
    public boolean containsEx(Exercise ex) {
        return internalArray.contains(ex);
    }

    //EFFECTS: returns the size of the collection
    public Integer collectionSize() {
        return internalArray.size();
    }

    //EFFECTS: provides a visualization for the exercises in the collection
    public void display() {
        for (Exercise e : internalArray) {
            System.out.println(" ");
            System.out.println(e.getName() + ":");
            System.out.println("Start Weight: " + e.getStartWeight());
            System.out.println("Goal Weight: " + e.getGoal());
            System.out.println("Current Weight: " + e.getCurrentWeight());
            System.out.println("Progress: " + e.getProgress() + "%");
        }
    }

    //EFFECTS: retrieves the Exercise at position i (UI)
    public Exercise retrieve(int i) {
        return internalArray.get(i - 1);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Name", colname);
        json.put("Exercises", collectionToJson());
        return json;
    }

    //Effects: returns the exercises in the collection as a JSON array
    private JSONArray collectionToJson() {
        JSONArray allprogress = new JSONArray();
        for (Exercise ex : internalArray) {
            allprogress.put(ex.toJson());
        }
        return allprogress;
    }
}
