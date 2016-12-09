import java.util.Scanner;

/**
 * Created by norse on 10/23/2016.
 */
public class GameOf24 {
    private Scanner userIO = new Scanner(System.in); // our way of communicating with user.
    private String handToEvaluate = "";
    private float[] numbers = new float[4];

    private char[] operators = {'+', '-', '*', '/'}; // operators we will use
    private String[] opList = new String[64]; // this will contain ALL combinations of operations
    private LinkedBinaryTree<Object> binTree;

    // here we simply welcome the user to the game and begin the game.
    private void Intro() {
        System.out.println("Hello and welcome to 24! Today I'll be giving you solutions to get to 24" +
                " with 4 cards in a deck of 52. Remember that A = 1, J = 11, Q = 12, and K = 13");
        StartGame();
    }

    // Here we ask if they want to play again, also do checks to see if they want to play or quit.
    private void PlayAgain() {
        System.out.println("\nWould you like to check another hand for a solution to 24? If yes, enter 'y'." +
                " If no, enter 'bye'.");
        String input = userIO.nextLine().toLowerCase();
        if (input.equals("y")) {
            StartGame();
        } else if (input.equals("bye")) {
            System.exit(0);
        } else {
            System.out.println("I could not understand that input. Try again please.");
            PlayAgain();
        }

    }

    // If no matches or solutions for 24, then we ask to play again
    private void NoMatch() {
        System.out.println("I'm sorry to tell you this, but... there are no solutions for this hand :/");
        PlayAgain();
    }

    // If our input was found to be invalid, then we start the game over
    private void AskForInputAgain() {
        System.out.println("Sorry, that input wasn't quite right. Please Try again.");
        StartGame();
    }

    // Here we convert the hand from all strings to ints and store it. If the values seem too small or large then we ask
    // for the input again. Also check to see if our input is too long or short to prevent errors from occurring.
    private boolean IsValidInput(String hand) {
        String[] cardsInHand = hand.split(" ");
        float[] numHand = new float[4];
        if (cardsInHand.length > 4 || cardsInHand.length < 4) {
            AskForInputAgain();
            return false;
        }
        for (int i = 0; i < cardsInHand.length; i++) {
            String s = cardsInHand[i];
            switch (s) {
                case "A":
                    cardsInHand[i] = "1";
                    break;
                case "J":
                    cardsInHand[i] = "11";
                    break;
                case "Q":
                    cardsInHand[i] = "12";
                    break;
                case "K":
                    cardsInHand[i] = "13";
                    break;
            }
            if (cardsInHand[i].matches(".*[a-zA-Z]+.*")) {
                AskForInputAgain();
                return false;
            } else {
                numHand[i] = Integer.parseInt(cardsInHand[i]);
                if (numHand[i] > 13 || numHand[i] < 1) {
                    AskForInputAgain();
                    return false;
                }
            }
        }
        numbers = numHand;
        return true;
    }

    // Start our the game by asking for input and doing the evaluations and restarting. Pretty much handle main functions.
    private void StartGame() {
        System.out.println("\nLet's begin, shall we? Please enter a hand of size 4. Example = A 10 2 Q.");
        handToEvaluate = userIO.nextLine().toUpperCase();
        if (IsValidInput(handToEvaluate)) {
            Evaluate();
        }
        if (numSolsFound == 0) {
            NoMatch();
        } else
            PlayAgain();
    }

    // we will need to finish swapping the numbers to create our permutation of numbers.
    private static final void Swap(float[] a, int i, int j) {
        // temp float to hold value
        float f = a[i];
        // swap the two values that we pass in.
        a[i] = a[j];
        a[j] = f;
    }

    // this will be our temp hand to test with
    private float[] tempHand = new float[4];

    /*
        With this method we swap every element and create every combination possible and before we go to a new combo we
        evaluate the possible arithmetic expressions.
     */
    private void Permutate(float[] arr, int k) {
        for (int i = k; i < arr.length; i++) {
            Swap(arr, i, k);
            Permutate(arr, k + 1);
            Swap(arr, k, i);
        }
        if (k == arr.length - 1) {
            tempHand[0] = arr[0];
            tempHand[1] = arr[1];
            tempHand[2] = arr[2];
            tempHand[3] = arr[3];
            for (int i = 1; i <= 5; i++) {
                PuzzleSolve(tempHand, opList, i);
            }
        }
    }

    /*
        This method loops through every possible combination of cards and then performs the evaluation for each
        combination. Then we go through the 5 different trees we can evaluate.
     */
    private void Evaluate() {
        CreateOpList(); // create our operator array
        Permutate(numbers, 0);
    }

    // In this method we will create all possibly combinations of operators.
    private void CreateOpList() {
        int comboNumber = 0; // we will use this integer as a counter in order to reset our string array to empty
        // It is also used for appropriate indexing of the operationCombos string array.
        opList = new String[64]; // initially starts as all null elements
        for (String s : opList) { // so we will reset it to empty
            opList[comboNumber] = ""; // "" is empty string.
            comboNumber++;
        }
        comboNumber = 0; // Reset our comboNumber to go back to the start of the array.

        /*
            Find every combination of operators with length of three (4^3). Char[] newOpSet will contain these operators
            and then for each character in that array we will append it to the string array opList[] at our
            index of comboNumber.
         */
        for (char operator : operators) {
            for (char operator1 : operators) {
                for (char operator2 : operators) {
                    char[] newOpSet = new char[]{operator, operator1, operator2};
                    for (char c : newOpSet) {
                        opList[comboNumber] += c;
                    }
                    comboNumber++;
                }
            }
        }
    }

    // the string we will use to print the evaluations out
    private String result = "";

    // This method recursively calls itself to go to the bottom of the tree and evaluate it from bottom up.
    // Whenever we see an operator we will perform the evaluation of the two numbers we checked before.
    private float Checking(LinkedBinaryTree t, Position pos) {
        float a = 0, b = 0;
        if (t.left(pos) != null) {
            a = Checking(t, t.left(pos));
        }
        if (t.right(pos) != null) {
            b = Checking(t, t.right(pos));
        } else {
            float x = (float) t.validate(pos).getElement();
            return x;
        }
        char c = (char) t.validate(pos).getElement();
        if (c == '+') {
            result += " (" + a + " + " + b + ")";
            return a + b;
        } else if (c == '-') {
            result += " (" + b + " - " + a + ")";
            return b - a;
        } else if (c == '*') {
            result += " (" + a + " * " + b + ")";
            return a * b;
        } else if (c == '/') {
            result += " (" + b + " / " + a + ")";
            return b / a;
        }
        return 0f;
    }

    /*
        In this method we take in three parameters, one which is our hand, the second is the operator list we made,
        and lastly the postfix notational form we are trying to solve with. Initially we break up the operator list into
        a char array in order to do one operation at a time (essentially breaking up the string to append it to the
        tempHolder to evaluate the character i.e. seeing '+', '-', etc. After we set up the infix formula, and then do
        evaluations in another method.
     */

    int numSolsFound = 0;

    private void PuzzleSolve(float[] arrayToSolve, String[] operatorCombo, int infix) {
        char[] opsInUse = new char[192];
        int counter = 0;
        for (String s : operatorCombo) {
            String toAddToOps = s;
            for (int i = 0; i < toAddToOps.length(); i++) {
                opsInUse[counter] = toAddToOps.charAt(i);
                counter++;
            }
        }

        // iterating through our char array, opsInUse. possibility of using 3 per calculation, so i+=3
        for (int i = 0; i < opsInUse.length; i += 3) {
            binTree = new LinkedBinaryTree<>();
            // below are all of the cases we can have with infix
            switch (infix) {
                /*
                            op[0]
                            |    |
                         op[1]    op[2]
                         |  |       |   |
                       i[0] i[1]    i[2]  i[3]
                 */
                case 1:
                    binTree.addRoot(opsInUse[i]);
                    binTree.addLeft(binTree.root(), opsInUse[i + 1]);
                    binTree.addRight(binTree.root(), opsInUse[i + 2]);
                    binTree.addLeft(binTree.left(binTree.root()), arrayToSolve[0]);
                    binTree.addRight(binTree.left(binTree.root()), arrayToSolve[1]);
                    binTree.addLeft(binTree.right(binTree.root()), arrayToSolve[2]);
                    binTree.addRight(binTree.right(binTree.root()), arrayToSolve[3]);

                    // To account for rounding errors I include a .005 error - just to be sure.
                    if (Checking(binTree, binTree.root()) <= 24.005f) {
                        result = "";
                        if (Checking(binTree, binTree.root()) >= 23.995f) {
                            result += " = 24";
                            numSolsFound++;
                            System.out.println(result);
                        }
                    }
                    result = "";
                    break;

                /*
                            op[0]
                            |    |
                         op[1]    i[0]
                         |  |
                       op[2] i[1]
                      |    |
                     i[2]  i[3]
                 */
                case 2:
                    binTree.addRoot(opsInUse[i]);
                    binTree.addLeft(binTree.root(), opsInUse[i + 1]);
                    binTree.addRight(binTree.root(), arrayToSolve[0]);
                    binTree.addLeft(binTree.left(binTree.root()), opsInUse[i + 2]);
                    binTree.addRight(binTree.left(binTree.root()), arrayToSolve[1]);
                    binTree.addLeft(binTree.left(binTree.left(binTree.root())), arrayToSolve[2]);
                    binTree.addRight(binTree.left(binTree.left(binTree.root())), arrayToSolve[3]);
                    if (Checking(binTree, binTree.root()) <= 24.005f) {
                        result = "";
                        if (Checking(binTree, binTree.root()) >= 23.995f) {
                            result += " = 24";
                            numSolsFound++;
                            System.out.println(result);
                        }
                    }
                    result = "";
                    break;

                /*
                            op[0]
                            |    |
                         op[1]    i[0]
                         |  |
                       i[1] op[2]
                            |    |
                           i[2]   i[3]
                 */
                case 3:
                    binTree.addRoot(opsInUse[i]);
                    binTree.addLeft(binTree.root(), opsInUse[i + 1]);
                    binTree.addRight(binTree.root(), arrayToSolve[0]);
                    binTree.addLeft(binTree.left(binTree.root()), arrayToSolve[1]);
                    binTree.addRight(binTree.left(binTree.root()), opsInUse[i + 2]);
                    binTree.addLeft(binTree.right(binTree.left(binTree.root())), arrayToSolve[2]);
                    binTree.addRight(binTree.right(binTree.left(binTree.root())), arrayToSolve[3]);
                    if (Checking(binTree, binTree.root()) <= 24.005f) {
                        result = "";
                        if (Checking(binTree, binTree.root()) >= 23.995f) {
                            result += " = 24";
                            numSolsFound++;
                            System.out.println(result);
                        }
                    }
                    result = "";
                    break;
                /*
                            op[0]
                            |    |
                         i[0]    op[1]
                                 |    |
                               op[2] i[1]
                                |  |
                              i[2] i[3]
                 */
                case 4:
                    binTree.addRoot(opsInUse[i]);
                    binTree.addLeft(binTree.root(), arrayToSolve[0]);
                    binTree.addRight(binTree.root(), opsInUse[i + 1]);
                    binTree.addLeft(binTree.right(binTree.root()), opsInUse[i + 2]);
                    binTree.addRight(binTree.right(binTree.root()), arrayToSolve[1]);
                    binTree.addLeft(binTree.left(binTree.right(binTree.root())), arrayToSolve[2]);
                    binTree.addRight(binTree.left(binTree.right(binTree.root())), arrayToSolve[3]);
                    if (Checking(binTree, binTree.root()) <= 24.005f) {
                        result = "";
                        if (Checking(binTree, binTree.root()) >= 23.995f) {
                            result += " = 24";
                            numSolsFound++;
                            System.out.println(result);
                        }
                    }
                    result = "";
                    break;
                /*
                            op[0]
                            |    |
                         i[0]    op[1]
                                 |   |
                                i[1] op[2]
                                      |    |
                                      i[2]   i[3]
                 */
                case 5:
                    binTree.addRoot(opsInUse[i]);
                    binTree.addLeft(binTree.root(), arrayToSolve[0]);
                    binTree.addRight(binTree.root(), opsInUse[i + 1]);
                    binTree.addLeft(binTree.right(binTree.root()), arrayToSolve[1]);
                    binTree.addRight(binTree.right(binTree.root()), opsInUse[i + 2]);
                    binTree.addLeft(binTree.right(binTree.right(binTree.root())), arrayToSolve[2]);
                    binTree.addRight(binTree.right(binTree.right(binTree.root())), arrayToSolve[3]);
                    if (Checking(binTree, binTree.root()) <= 24.005f) {
                        result = "";
                        if (Checking(binTree, binTree.root()) >= 23.995f) {
                            result += " = 24";
                            numSolsFound++;
                            System.out.println(result);
                        }
                    }
                    result = "";
                    break;
            }
        }
    }

    public static void main(String[] args) {
        GameOf24 newGame = new GameOf24();
        newGame.Intro();
    }
}