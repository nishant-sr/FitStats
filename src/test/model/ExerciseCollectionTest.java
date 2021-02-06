package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseCollectionTest {

    Exercise ex1;
    Exercise ex2;
    Exercise ex3;

    ExerciseCollection exc1;


    @BeforeEach
    void runBefore(){
        ex1 = new Exercise("Bench Press",135,225,135,9,99);
        ex2 = new Exercise("Incline Bench Press",200,500,400,5,67);
        ex3 = new Exercise("Squat",300,405,310,4,25);

        exc1 = new ExerciseCollection();
    }

    @Test
    void testcontainsExEmpty(){
        assertFalse(exc1.containsEx(ex1));
        assertEquals(0,exc1.collectionSize());
    }

    @Test
    void testinsertEx1(){
        exc1.insertEx(ex1);
        assertTrue(exc1.containsEx(ex1));
        assertEquals(1,exc1.collectionSize());
    }

    @Test
    void testremoveEx1(){
        exc1.removeEx(ex1);
        assertFalse(exc1.containsEx(ex1));
        assertEquals(0,exc1.collectionSize());
    }

    @Test
    void testinsertEx123(){
        exc1.insertEx(ex1);
        exc1.insertEx(ex2);
        exc1.insertEx(ex3);
        assertEquals(3,exc1.collectionSize());
        assertTrue(exc1.containsEx(ex1));
        assertTrue(exc1.containsEx(ex2));
        assertTrue(exc1.containsEx(ex3));
    }

    @Test
    void testRemoveExByNumExceptionExpected(){
        exc1.insertEx(ex1);
        exc1.insertEx(ex2);
        try{
            exc1.removeExByNum(0);
            fail("Exception should have been caught!");
        } catch (IndexOutOfBoundsException ex){
            System.out.println("Good catch!");
        }
    }

    @Test
    void testRemoveExByNumExceptionUnexpected(){
        exc1.insertEx(ex1);
        exc1.insertEx(ex2);
        try{
            exc1.removeExByNum(1);
            System.out.println("Working just fine");
        } catch (IndexOutOfBoundsException ex){
            fail("This shouldn't have happened.");
        }
    }

}