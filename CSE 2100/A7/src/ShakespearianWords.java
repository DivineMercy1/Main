//These imports are required for the decoding the text file as well as getting use of reading through the file

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;

/**
 * Created by simon on 11/13/2016.
 */
public class ShakespearianWords {
    public static void main(String[] args) throws FileNotFoundException {
        // We're going to use this array to see the amount of times we see each word
        ArrayList<Integer> frequencies = new ArrayList();

        // Use this chain map to assign each word with an integer - which will be the amount of times we see it
        Map<String, Integer> map = new ChainHashMap<>();

        // the location of the file - PLEASE CHANGE THIS WHEN YOU ARE GRADING, IT REQUIRES THE FULL PATH FILE
        File file = new File("C:\\Users\\simon\\Dropbox\\Fall 2016\\CSE 2100\\A7\\src\\shakespeare.txt");

        // Scanner will be able to read the file, using a delimiter to get each word that contains a-z, no punctuation
        Scanner docToRead = new Scanner(file).useDelimiter("[^a-zA-Z]+");

        // so long as there are still more lines to read
        while (docToRead.hasNext()) {
            // each word will be added to the map and will be stored as a lowercase word
            String word = docToRead.next().toLowerCase();
            Integer wordCount = map.get(word);
            // assign the string and integer value for the map so long as the word hasn't been seen before
            if (wordCount == null) {
                wordCount = 0;
            }
            map.put(word, 1 + wordCount);
        }
        // close the document
        docToRead.close();
        // for every entry in the map we're going to get the total amount of times we've seen that word and add to a list
        for (Entry<String, Integer> entry : map.entrySet()) {
            frequencies.add(entry.getValue());
        }

        // sort from least to greatest amount
        Collections.sort(frequencies);

        // start at most frequent word
        int counter = 1;

        // now that all the words were read, detect collisions and print it out, refer to method for further comments.
        map.collisions();

        // start at the most frequent words until we get to the first
        for (int i = frequencies.size() - 1; i > 0; ) {

            // and for each entry we will test to see if the frequencies match
            for (Entry<String, Integer> ent : map.entrySet()) {

                // if they do then we will print out the most frequent words up to 1000. if we do then decrement i to go
                // to the next highest word
                if (ent.getValue() == frequencies.get(i) && counter < 1001) {
                    System.out.println(counter + ". " + ent.getKey() + " was found: " + ent.getValue() + " times.");
                    i--;
                    counter++;
                } // once we print out 1000 we quit the loop
                if (counter == 1001) {
                    return;
                }
            }
        }
    }
}
