package persistence;

import model.ExerciseCollection;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JsonWriter {

    private PrintWriter fileeditor;
    private String savefile;

    //EFFECTS: constructs writer to write to the file where
    //it saves (savefile)
    public JsonWriter(String savefile) {
        this.savefile = savefile;
    }

    //Modifies: this
    //Effects: opens the writer
    //throws an exception if the json file can't be found
    public void openfile() throws FileNotFoundException {
        fileeditor = new PrintWriter(new File(savefile));
    }

    //Modifies: this
    //Effects: writes JSON representation of Exercise Collection to file
    public void write(ExerciseCollection exc) {
        JSONObject json = exc.toJson();
        saveToFile(json.toString(4));
    }

    //Modifies: this
    //Effects: saves string to file
    private void saveToFile(String json) {
        fileeditor.print(json);
    }

    //Modifies: this
    //Effects: closes writer for json file
    public void closefile() {
        fileeditor.close();
    }

}
