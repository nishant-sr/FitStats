package model;

/*Represents an exercise having a name(String), starting weight,
  goal weight and current weight(in lbs)
 */

import persistence.Writable;
import org.json.JSONObject;

import static java.lang.Math.round;

public class Exercise implements Writable {

    protected String exerciseName;
    private Integer startWeight;
    private Integer goalWeight;
    private Integer currentWeight;
    private Integer progress;
    private Integer sets;
    private Integer reps;

    //REQUIRES: name length cannot be 0, startWeight < goalWeight
    /*EFFECTS: name on exercise is set to exerciseName;
               user's starting weight on the exercise is set to startWeight;
               user's goal weight for the exercise is set to goalWeight;
               user's strides towards goalWeight is updated by the user
               as they progress, and set to currentWeight;
               how much user has progressed towards their goal is set to
               progress
     */
    public Exercise(String name, Integer startWeight, Integer goalWeight,
                    Integer currentWeight, Integer sets, Integer reps) {
        this.exerciseName = name;
        this.startWeight = startWeight;
        this.goalWeight = goalWeight;
        this.currentWeight = currentWeight;
        this.progress = round((float)
                ((this.currentWeight - this.startWeight) * 100 / (this.goalWeight - this.startWeight)));
        this.sets = sets;
        this.reps = reps;
    }

    //REQUIRES: newRecord > 0
    //MODIFIES: this
    /*EFFECTS: if the user has hit a new record, it replaces
               their currentWeight value, and also updates their
               progress towards their goal
     */
    public void updateProgress(Integer newRecord) {
        this.currentWeight = newRecord;
        this.progress = round((float)
                ((newRecord - this.startWeight) * 100 / (this.goalWeight - this.startWeight)));
    }

    //REQUIRES: newGoal > 0
    //MODIFIES: this
    //EFFECTS: changes the goal the user would like to achieve
    public void updateGoal(Integer newGoal) {
        this.goalWeight = newGoal;
        updateProgress(currentWeight);
    }

    //REQUIRES: newStart > 0
    //MODIFIES: this
    /*EFFECTS: changes the value of the weight the user is
               starting at in their progress towards their goal
     */
    public void updateStart(Integer newStart) {
        this.startWeight = newStart;
        updateProgress(currentWeight);
    }

    //EFFECTS: returns the progress
    public double getProgress() {
        return this.progress;
    }

    //EFFECTS: returns the goal
    public int getGoal() {
        return this.goalWeight;
    }

    //EFFECTS: returns the start weight
    public int getStartWeight() {
        return this.startWeight;
    }

    //EFFECTS: returns the current weight
    public int getCurrentWeight() {
        return this.currentWeight;
    }

    //EFFECTS: returns the name
    public String getName() {
        return (this.exerciseName + " " + this.sets.toString() + " X " + this.reps.toString());
    }

    @Override
    public JSONObject toJson() {
        JSONObject exercise = new JSONObject();
        exercise.put("name", exerciseName);
        exercise.put("start", startWeight);
        exercise.put("goal", goalWeight);
        exercise.put("current", currentWeight);
        exercise.put("progress", progress);
        exercise.put("sets", sets);
        exercise.put("reps", reps);
        return exercise;
    }
}
