
/**
 * Created by norse on 11/4/2016.
 */
public class Goats {

    // the color we can get of the goat
    private String whatsMyColor;
    // all the integers we may need access too such as colored coins, times passed around bridges, priority number
    // as well as the number of goat it is for that color.
    public int howManyCoinsW, coinsB, coinsG, timesIPassed, wGoats, bGoats, gGoats, priorityNum, number;

    // our standard constructor for building a goat, defining its color, times passed and its location in the heap
    public Goats(String color, int timesPassed, int location) {

        // set everything up here and assign values.
        whatsMyColor = color;
        howManyCoinsW = 100;
        coinsB = 100;
        coinsG = 100;
        timesIPassed = timesPassed;
        number = location;

        // determine its color and check how many of each we have by incrementing
        switch (color) {
            case "White":
                wGoats++;
                break;
            case "Black":
                bGoats++;
                break;
            case "Gray":
                gGoats++;
                break;
        }
    }

    // how we determine if a goat pays or not
    public boolean doIPay(Bridges troll) {
        if (troll.getTrollColor() == this.getColor()) {
            return false;
        }
        return true;
    }

    // these get methods all just return certain values, quite self explanatory
    public int getWCoins() {
        return howManyCoinsW;
    }

    public int getCoinsB() {
        return coinsB;
    }

    public int getCoinsG() {
        return coinsG;
    }

    public String getColor() {
        return whatsMyColor;
    }

    // determine if the goat dies, whether or not it has <=0 coins of each color
    private boolean DoIDie() {
        boolean doesDie = true;
        if (howManyCoinsW <= 0 || coinsB <= 0 || coinsG <= 0) {
            return doesDie;
        } else return !doesDie;
    }

    // decrement the values of the certain goat depending on its color as well as seeing if it is supposed to die.
    public boolean KillGoat() {
        if (DoIDie()) {
            switch (whatsMyColor) {
                case "White":
                    wGoats--;
                    return true;
                case "Black":
                    bGoats--;
                    return true;
                case "Gray":
                    gGoats--;
                    return true;
            }
        }
        return false;
    }

    // used for seeing if it crossed the full rotation of bridges, eventually if it equals 4 we instantiate a new goat
    public void cross(Bridges bridgeToAddTo) {
        timesIPassed++;
        if (timesIPassed == 4) {
            timesIPassed = 1;
            Goats newGoat = new Goats(this.getColor(), 0, 0);
        }
    }
}