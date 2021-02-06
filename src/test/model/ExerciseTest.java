package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {

    Exercise ex1;
    Exercise ex2;

    @BeforeEach
    void runBefore(){
        ex1 = new Exercise("Bench Press",135,225,135,20,5);
        ex2 = new Exercise("Squat",315,405,320,1,1);
    }

    @Test
    void testupdateProgressIncrease(){
        ex1.updateProgress(150);
        assertEquals(150,ex1.getCurrentWeight());
    }

    @Test
    void testupdateProgressDecrease(){
        ex1.updateProgress(120);
        assertEquals(120,ex1.getCurrentWeight());
    }

    @Test
    void testupdateGoalDecrease(){
        ex1.updateGoal(100);
        assertEquals(100,ex1.getGoal());
    }

    @Test
    void testupdateGoalIncrease(){
        ex1.updateGoal(315);
        assertEquals(315,ex1.getGoal());
    }

    @Test
    void testupdateStartDecrease(){
        assertEquals(135,ex1.getStartWeight());
        ex1.updateStart(95);
        assertEquals(95,ex1.getStartWeight());
    }

    @Test
    void testupdateStartIncrease(){
        assertEquals(135,ex1.getStartWeight());
        ex1.updateStart(400);
        assertEquals(400,ex1.getStartWeight());
    }

    @Test
    void testgetProgressIncrease(){
        ex1.updateProgress(220);
        assertEquals(94.0,ex1.getProgress());
    }

    @Test
    void testgetProgressDecrease(){
        ex1.updateProgress(120);
        assertEquals(-16.0,ex1.getProgress());
    }

}