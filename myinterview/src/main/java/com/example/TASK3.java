package com.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Write a list and add an aleatory number of Strings. In the end, print out how
 * many distinct itens exists on the list.
 *
 */

public class TASK3 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();

        Random random = new Random();
        int numStrings = random.nextInt(100);

        for (int i = 0; i < numStrings; i++) {
            list.add("String" + random.nextInt(50));
        }

        Set<String> distinctItems = new HashSet<>(list);
        System.out.println("Number of distinct items in the list: " + distinctItems.size());
    }
}
