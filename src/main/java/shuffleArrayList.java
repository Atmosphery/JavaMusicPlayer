package main.java;
import java.util.ArrayList;
import java.util.Collections;

public class shuffleArrayList<T> {

    public ArrayList<T> shuffleList(ArrayList<T> input){
        Collections.shuffle(input);
        return input;
    }

}
