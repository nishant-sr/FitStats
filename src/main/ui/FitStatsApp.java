package ui;

import model.Exercise;
import model.ExerciseCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FitStatsApp {
    private static final String JSON_FILE = "./data/fitstats.json";
    private static JsonWriter jsonWriter;
    private static JsonReader jsonReader;

    private static ExerciseCollection exc1;
    private static Scanner input;

    static JFrame frame;

    private static JTable table;
    private static DefaultTableModel model;

    //Effects: constructs class FitStatsApp
    public FitStatsApp() {
        input = new Scanner(System.in);
        exc1 = new ExerciseCollection();
        jsonReader = new JsonReader(JSON_FILE);
        jsonWriter = new JsonWriter(JSON_FILE);
        runFitStats();
    }

    //Effects: runs the whole main menu for the application
    public void runFitStats() {
        table = new JTable(new DefaultTableModel());
        model = (DefaultTableModel) table.getModel();
        model.addColumn("Exercise");
        model.addColumn("Start");
        model.addColumn("Goal");
        model.addColumn("Current");
        model.addColumn("Progress");
        createAndShowGUI();
    }

//-----------------------------------------------SAVE/LOAD-----------------------------------------------

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private static void loadFitStats() {
        try {
            exc1 = jsonReader.read();
            displayStats();
        } catch (IOException e) {
            System.out.println("Trouble loading your file");
        }
    }

    // EFFECTS: saves the workroom to file
    private static void saveFitStats() {
        try {
            jsonWriter.openfile();
            jsonWriter.write(exc1);
            jsonWriter.closefile();
            System.out.println("Progress saved to:" + JSON_FILE);
            displayStats();
        } catch (FileNotFoundException e) {
            System.out.println("Trouble saving your progress");
        }
    }

//-----------------------------------------------EXERCISES-----------------------------------------------

    //Effects: prints out the options for the user if they choose to update any of the exercises
    private static void updateForm() throws InputMismatchException {
        System.out.println("From the top(1), which number is the exercise you want to update?");
        int modifyex = input.nextInt();
        if ((modifyex - 1 <= exc1.collectionSize()) && (modifyex - 1 >= 0)) {
            System.out.println("Would you like to: ");
            System.out.println("p : Update your Progress");
            System.out.println("g : Update your goal");
            System.out.println("s : Update your starting point");
            String update = input.next();
            update = update.toLowerCase();
            updateExercise(modifyex, update);
            displayStats();
        } else {
            System.out.println("Are you sure you entered the right number? Try again!");
            updateForm();
        }
    }

    //Requires: exnum is the numerical positioning of the exercises form the top
    //as viewed on the interface, exnum > 0
    //operation must be one of the options provided to the user
    //Modifies: Exercise
    //Effects: updates the user's choice of the exercise's aspect
    private static void updateExercise(int exnum, String operation) {
        Exercise upex = exc1.retrieve(exnum);
        switch (operation) {
            case "p":
                System.out.println("How much weight did you hit recently?");
                int newp = input.nextInt();
                upex.updateProgress(newp);
                break;
            case "g":
                System.out.println("What is your new goal?");
                int newg = input.nextInt();
                upex.updateGoal(newg);
                break;
            case "s":
                System.out.println("What do you want to change your starting point to?");
                int news = input.nextInt();
                upex.updateStart(news);
                break;
            default:
                System.out.println("That was not one of the options, please try again!");
                break;
        }
    }


    //Modifies: ExerciseCollection
    //Effects: removes the user's choice of exercise
    private static void removeExercise() {
        try {
            System.out.println("From the top(1), which number is the exercise you want to remove?");
            int remove = input.nextInt();
            exc1.removeExByNum(remove);
            saveFitStats();
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Oops, looks like you've entered an invalid number! Try again!");
            removeExercise();
        }
    }

    //Modifies: ExerciseCollection
    //Effects: Helps user add a new Exercise
    public static void newExerciseForm() {
        System.out.println("What is the name of the exercise you want to add?");
        String name = input.next();
        System.out.println("What is your starting weight on the exercise?");
        int sw = input.nextInt();
        System.out.println("What is your goal weight for the exercise?");
        int gw = input.nextInt();
        System.out.println("What is your current weight for the exercise?");
        int cw = input.nextInt();
        System.out.println("How many sets for this exercise?");
        int sets = input.nextInt();
        System.out.println("How many reps for each set?");
        int reps = input.nextInt();

        Exercise newx = new Exercise(name, sw, gw, cw, sets, reps);
        exc1.insertEx(newx);
        displayStats();
    }

//-----------------------------------------------GUI-----------------------------------------------

    //Effects: Creates the GUI window itself and displays it
    private static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("FitStats");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = createUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    //Effects: Forms the GUI by creating and placing all the components onto a panel
    public static JPanel createUI() {
        displayStats();
        JScrollPane collection = new JScrollPane(table);
        final JButton add = new JButton("Add Exercise");
        final JButton remove = new JButton("Remove Exercise");
        final JButton update = new JButton("Update Exercise");
        final JButton save = new JButton("Save");
        final JButton load = new JButton("Load");
        configureAddButton(add);
        configureRemoveButton(remove);
        configureUpdateButton(update);
        configureSaveButton(save);
        configureLoadButton(load);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(Color.BLACK);
        panel.add(collection);
        collection.setBackground(Color.BLACK);
        panel.add(add);
        panel.add(remove);
        panel.add(update);
        panel.add(save);
        panel.add(load);
        return panel;
    }

    //Effects: Assigns operations for the add button
    private static void configureAddButton(JButton add) {
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newExerciseForm();
            }
        });
    }

    //Effects: Displays the list of exercises on a table with the data for each sorted categorically
    public static void displayStats() {
        model = (DefaultTableModel) table.getModel();
        table.setBackground(Color.BLACK);
        table.setForeground(Color.WHITE);
        model.setRowCount(0);
        for (int i = 1; i <= exc1.collectionSize(); i++) {
            String[] objs = {exc1.retrieve(i).getName(),
                    String.valueOf(exc1.retrieve(i).getStartWeight()),
                    String.valueOf(exc1.retrieve(i).getGoal()),
                    String.valueOf(exc1.retrieve(i).getCurrentWeight()),
                    String.valueOf(exc1.retrieve(i).getProgress())};
            model.addRow(objs);
        }
    }

    //Effects: Assigns operations for the remove button
    private static void configureRemoveButton(JButton remove) {
        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeExercise();
            }
        });
    }

    //Effects: Assigns operations for the update button
    private static void configureUpdateButton(JButton update) {
        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    updateForm();
                } catch (InputMismatchException exception) {
                    System.out.println("Make sure you enter a Number");
                }
            }
        });
    }

    //Effects: Assigns operations for the load button
    private static void configureLoadButton(JButton load) {
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadFitStats();
            }
        });
    }

    //Effects: Assigns operations for the save button
    private static void configureSaveButton(JButton save) {
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFitStats();
                playSound("./data/sound1.wav");
            }
        });
    }

    //Effects: Plays the sound of the audio file specified
    public static void playSound(String filename) {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename).getAbsoluteFile());
            Clip audio = AudioSystem.getClip();
            audio.open(stream);
            audio.start();
        } catch (Exception ex) {
            System.out.println("There was a problem with playing the sound.");
        }
    }

}
