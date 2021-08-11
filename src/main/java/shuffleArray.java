package main.java;

import java.util.Random;

public class shuffleArray<T> {

    public T[] shuffleGivenArray(T[] input){
        Random random = new Random();
        for(int i = input.length - 1; i > 0; i--){
            int index = random.nextInt(i + 1);
            T temp = input[index];
            input[index] = input[i];
            input[i] = temp;
        }
        return input;
    }


}
