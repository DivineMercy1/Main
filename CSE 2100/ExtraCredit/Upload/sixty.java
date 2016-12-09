import java.util.ArrayList;

/**
 * Created by simon on 11/11/2016.
 */
public class sixty {

    // Each of the letters will be assigned a value
    private int FORTY, SIXTY, TEN, f, o, r, t, y, s, i, x, e, n;

    // The numbers array list is our default values to test
    private ArrayList<Integer> numbers = new ArrayList<>();

    // The empty array list we use in puzzle solve
    private ArrayList<Integer> empty = new ArrayList<>();

    // In this method we will assign each value from the arraylist to each variable with its corresponding letter value
    // After that we will do an evaluation
    private boolean Equals(ArrayList<Integer> list) {
        f = list.get(0);
        o = list.get(1);
        r = list.get(2);
        t = list.get(3);
        y = list.get(4);
        s = list.get(5);
        i = list.get(6);
        x = list.get(7);
        e = list.get(8);
        n = list.get(9);
        // Y is in 1's place, T in 10's, R in 100's, O in 1000's place and finally F in 10000's place. assign these
        // values approprately for each number.
        FORTY = (f * 10000 + o * 1000 + r * 100 + t * 10 + y * 1);
        SIXTY = (s * 10000 + i * 1000 + x * 100 + t * 10 + y * 1);
        TEN = (t * 100 + e * 10 + n * 1);
        int sum = FORTY + TEN + TEN;

        // if our values match up correctly, then we will show the values of each.
        if (sum == SIXTY) {
            System.out.printf("Forty: %d,\nTen: %d,\nSixty: %d\n", FORTY, TEN, SIXTY);
            System.out.printf("The values of each letters are as follows:\nF = %d,\nO = %d,\nR = %d,\nT = %d,\nY = %d,\n" +
                    "S = %d,\nI = %d,\nX = %d,\nE = %d,\nand N = %d.", f, o, r, t, y, s, i, x, e, n);
            return true;
        }
        return false;
    }

    // Add the values 0-9 to the array list and then pass it to the puzzle solve algorithm.
    private void SetUp() {
        numbers.add(0);
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.add(6);
        numbers.add(7);
        numbers.add(8);
        numbers.add(9);

        PuzzleSolve(10, empty, numbers);
    }

    // Puzzle solve abstract method from the book to implementation.
    private void PuzzleSolve(int k, ArrayList<Integer> S, ArrayList<Integer> U) {
        // for every integer value in the array passed in U we will add it to the empty array and remove it from the
        // new array made called list.
        for (Integer s : U) {
            ArrayList<Integer> list = new ArrayList<>(U);
            S.add(s);
            list.remove(s);
            // If we have removed all numbers in the list and S is now full, check the evaluation.
            if (k == 1) {
                Equals(S);
            }
            // else we keep calling this method until U is empty.
            else {
                PuzzleSolve(k - 1, S, list);
            }
            // remove the value from s and then add it back to the list to test a new variation at the end.
            S.remove(s);
            list.add(s);
        }
    }

    public static void main(String[] args) {
        sixty test = new sixty();
        test.SetUp();
    }
}