package edu.elon.cs.scrabblecheck;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *  Holds a list of valid Scrabble words.
 *
 *  @author J. Hollingsworth and CSC 130 - Fall 2015
 */

public class Dictionary {

    // instance variable -- the list of words
    private ArrayList<String> words;

    public Dictionary(Context context)  {
        words = new ArrayList<String>();

        // open the file and read the words into the list
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            context.getAssets().open("dictionary.txt"), "UTF-8"));

            // read the words
            String line = reader.readLine();
            while (line != null) {
                words.add(line);
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // is the user's word in the dictionary?
    public boolean isMatch(String usersWord) {
        return words.contains(usersWord);
    }
}










