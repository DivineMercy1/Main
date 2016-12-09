// Our imports for the arrays and hash we use

import java.util.Arrays;
import java.util.HashSet;

public class GameOf24 {

    private HashSet<String> seenCombos = new HashSet<>(); // This will help us reduce the amount of duplicate numbers
    // we see
    private StackClass numbersToCompute = new StackClass<>(); // our main stack
    private char[] operators = {'+', '-', '*', '/'}; // operators we will use
    private String[] opList = new String[64]; // this will contain ALL combinations of operations
    private Object[] tempHolder = new Object[7]; // this will hold our numbers and operators to evaluate
    private Boolean reached24 = false; // flag to see if we met 24 at least once with a given set of numbers.

    /*
        This method loops through every possible combination of cards and adds it to the hash if it doesn't already
        exist. We also sort the array to ensure that the numerical values will be hashable (prevent dupes). Next we
        create a string that we can add to the combo.
     */
    private void CreateHandsOpsAndCompute() {
        CreateOpList(); // create our operator array

        // all of these for loops are creating every single combination of cards
        for (int firstCard = 1; firstCard < 14; firstCard++) {
            for (int secondCard = 1; secondCard < 14; secondCard++) {
                for (int thirdCard = 1; thirdCard < 14; thirdCard++) {
                    for (int lastCard = 1; lastCard < 14; lastCard++) {
                        // our hand is equivalent to the the first, second, third and fourth card. 4 cards in hand
                        // keep everything in FLOAT type to ensure that we can have fractional values
                        float[] ourHand = {firstCard, secondCard, thirdCard, lastCard};
                        // sort it from lowest to highest value
                        Arrays.sort(ourHand);

                        // here we pass the hand as a string and add it to a hashset
                        StringBuilder handAsAString = new StringBuilder();
                        for (float x : ourHand) {
                            handAsAString.append(x + " ");
                        }
                        // if we do not already have this in the set, add it and solve the 24 puzzle
                        if (!seenCombos.contains(handAsAString.toString())) {
                            seenCombos.add(handAsAString.toString());
                            // loop through every possible postfix notational form
                            for (int post = 1; post <= 5; post++) {
                                PuzzleSolve(ourHand, opList, post);
                            }
                            //reset our flag each time we find a new hand
                            reached24 = false;
                        }
                    }
                }
            }
        }

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

    /*
        In this method we take in three parameters, one which is our hand, the second is the operator list we made,
        and lastly the postfix notational form we are trying to solve with. Initially we break up the operator list into
        a char array in order to do one operation at a time (essentially breaking up the string to append it to the
        tempHolder to evaluate the character i.e. seeing '+', '-', etc. After we set up the postfix formula, do the case
        checks.
     */
    private void PuzzleSolve(float[] arrayToSolve, String[] operatorCombo, int postCase) {
        char[] opsInUse = new char[192];
        int counter = 0;
        for (String s : operatorCombo) {
            String toAddToOps = s;
            for (int i = 0; i < toAddToOps.length(); i++) {
                opsInUse[counter] = toAddToOps.charAt(i);
                counter++;
            }
        }
        // our first two elements are allows numbers.
        tempHolder[0] = arrayToSolve[0];
        tempHolder[1] = arrayToSolve[1];

        // iterating through our char array, opsInUse. possibility of using 3 per calculation, so i+=3
        for (int i = 0; i < opsInUse.length; i += 3) {
            // if we haven't found a solution yet, proceed.
            if (!reached24) {

                // below are all of the cases we can have with postfix
                switch (postCase) {
                    case 1: // 11p1p1p
                        tempHolder[2] = opsInUse[i];
                        tempHolder[3] = arrayToSolve[2];
                        tempHolder[4] = opsInUse[i + 1];
                        tempHolder[5] = arrayToSolve[3];
                        tempHolder[6] = opsInUse[i + 2];
                        break;
                    case 2: // 11p11pp
                        tempHolder[2] = opsInUse[i];
                        tempHolder[3] = arrayToSolve[2];
                        tempHolder[4] = arrayToSolve[3];
                        tempHolder[5] = opsInUse[i + 1];
                        tempHolder[6] = opsInUse[i + 2];
                        break;
                    case 3: // 111pp1p
                        tempHolder[2] = arrayToSolve[2];
                        tempHolder[3] = opsInUse[i];
                        tempHolder[4] = opsInUse[i + 1];
                        tempHolder[5] = arrayToSolve[3];
                        tempHolder[6] = opsInUse[i + 2];
                        break;
                    case 4: // 111p1pp
                        tempHolder[2] = arrayToSolve[2];
                        tempHolder[3] = opsInUse[i];
                        tempHolder[4] = arrayToSolve[3];
                        tempHolder[5] = opsInUse[i + 1];
                        tempHolder[6] = opsInUse[i + 2];
                        break;
                    case 5: // 1111ppp
                        tempHolder[2] = arrayToSolve[2];
                        tempHolder[3] = arrayToSolve[3];
                        tempHolder[4] = opsInUse[i];
                        tempHolder[5] = opsInUse[i + 1];
                        tempHolder[6] = opsInUse[i + 2];
                        break;
                }
                CaseChecks();
            }
        }
    }

    /*
        In this method we check to see what the values are of our tempHolder, arithmetic values specifically.
        Based on their values we will perform some algebra, if we see no operators then we push to the stack.
     */
    private void CaseChecks() {

        // loop through each element to see what values we have and perform appropriate calculations
        for (int i = 0; i <= 6; i++) {
            if (tempHolder[i].equals('+')) {
                Add();
            } else if (tempHolder[i].equals('-')) {
                Subtract();
            } else if (tempHolder[i].equals('*')) {
                Mult();
            } else if (tempHolder[i].equals('/')) {
                Divide();
            } else
                numbersToCompute.push(tempHolder[i]);
        }
        // if we got to 24, then we found a solution so set flag to true.
        if ((float) numbersToCompute.top() == 24) {
            reached24 = true;
            // here we print out our postfix equation solution
            for (int r = 0; r <= 6; r++) {
                System.out.print(tempHolder[r] + " ");
            }
            System.out.print("= " + numbersToCompute.top() + "\n");
        }
        // REMEMBER to dump the stack every time you go around - if not you get overflow error.
        while (!numbersToCompute.isEmpty()) {
            numbersToCompute.pop();
        }
    }

    // all algebraic methods for computations push the result back on the stack and pops the two numbers to evaluate.

    // Performs the addition of two numbers
    private void Add() {
        float a = (float) numbersToCompute.pop();
        float b = (float) numbersToCompute.pop();
        float result = b + a;
        numbersToCompute.push(result);

    }

    // Performs the subtraction of two numbers
    private void Subtract() {
        float a = (float) numbersToCompute.pop();
        float b = (float) numbersToCompute.pop();
        float result = b - a;
        numbersToCompute.push(result);
    }

    // Performs the multiplication of two numbers
    private void Mult() {
        float a = (float) numbersToCompute.pop();
        float b = (float) numbersToCompute.pop();
        float result = b * a;
        numbersToCompute.push(result);
    }

    // Performs the division of two numbers
    private void Divide() {
        float a = (float) numbersToCompute.pop();
        float b = (float) numbersToCompute.pop();
        float result = b / a;
        numbersToCompute.push(result);
    }


    public static void main(String[] args) {
        GameOf24 test = new GameOf24();
        test.CreateHandsOpsAndCompute();
    }
}
