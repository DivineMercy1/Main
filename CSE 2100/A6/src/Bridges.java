/**
 * Created by norse on 11/5/2016.
 */
public class Bridges extends HeapAdaptablePriorityQueue<Integer, Goats> {

    // troll color identification as well as its total money to keep track of.
    private String trollColor;
    private int totalMoney, trialSize;

    // default arrays for the total number of each colored goat we have.
    private int[] wTot = new int[55];
    private int[] bTot = new int[55];
    private int[] gTot = new int[55];

    // standard constructor we use for the bridge.
    public Bridges(String color, int money) {
        trollColor = color;
        totalMoney = money;
    }

    // here are all of our priority checks to see which can go over first or second
    public void priorityCheck(Goats first, Goats second, Bridges nextTroll) {
        if (first.doIPay(this) && second.doIPay(this)) {
            first.priorityNum = 0;
            second.priorityNum = 0;
        } else if (!first.doIPay(this) && second.doIPay(this)) {
            second.priorityNum = 0;
            first.priorityNum = 1;
        } else if (!first.doIPay(this) && !second.doIPay(this)) {
            first.priorityNum = 1;
            second.priorityNum = 1;
        }
        if (first.priorityNum == 0) {
            // set the goat to the next bridge
        }
        if (second.priorityNum == 0) {
            // set the goat to the right bridge
        }
    }

    /*
    These three cross(W,B,G) methods simply decrement the coin values of the goat crossing by one as well as making
    the payment. moreover, they insert the appropriate goat into the geap if it passed through enough times.
     */
    private void crossW(Entry<Goats, Integer> entry) {
        Goats temp = entry.getKey();
        if (temp.getColor() != "White") {
            temp.howManyCoinsW -= 20;
        }
        temp.coinsG--;
        temp.coinsB--;
        temp.howManyCoinsW--;
        if (!temp.KillGoat()) {
            this.insert(this.size(), temp);
            temp.cross();
        }

        if (temp.timesIPassed == 4) {
            switch (temp.getColor()) {
                case "White":
                    this.insert(this.size(), new Goats("White", 0, 0));
                    wTot[trialSize] = wTot[trialSize - 1] + 1;
                    break;
                case "Black":
                    this.insert(this.size(), new Goats("Black", 0, 0));
                    bTot[trialSize] = bTot[trialSize - 1] + 1;
                    break;
                case "Gray":
                    this.insert(this.size(), new Goats("Gray", 0, 0));
                    gTot[trialSize] = gTot[trialSize - 1] + 1;
                    break;
            }
        }
    }

    private void crossB(Entry<Goats, Integer> entry) {
        Goats temp = entry.getKey();
        if (temp.getColor() != "Black") {
            temp.coinsB -= 20;
        }
        temp.coinsG--;
        temp.coinsB--;
        temp.howManyCoinsW--;
        if (!temp.KillGoat()) {
            this.insert(this.size(), temp);
            temp.cross();
        }

        if (temp.timesIPassed == 3) {
            switch (temp.getColor()) {
                case "White":
                    this.insert(this.size(), new Goats("White", 0, 0));
                    wTot[trialSize] = wTot[trialSize - 1] + 1;
                    break;
                case "Black":
                    this.insert(this.size(), new Goats("Black", 0, 0));
                    bTot[trialSize] = bTot[trialSize - 1] + 1;
                    break;
                case "Gray":
                    this.insert(this.size(), new Goats("Gray", 0, 0));
                    gTot[trialSize] = gTot[trialSize - 1] + 1;
                    break;
            }
        }
    }

    private void crossG(Entry<Goats, Integer> entry) {
        Goats temp = entry.getKey();
        if (temp.getColor() != "Gray") {
            temp.coinsB -= 20;
        }
        temp.coinsG--;
        temp.coinsB--;
        temp.howManyCoinsW--;
        if (!temp.KillGoat()) {
            this.insert(this.size(), temp);
            temp.cross();
        }

        if (temp.timesIPassed == 3) {
            switch (temp.getColor()) {
                case "White":
                    this.insert(this.size(), new Goats("White", 0, 0));
                    wTot[trialSize] = wTot[trialSize - 1] + 1;
                    break;
                case "Black":
                    this.insert(this.size(), new Goats("Black", 0, 0));
                    bTot[trialSize] = bTot[trialSize - 1] + 1;
                    break;
                case "Gray":
                    this.insert(this.size(), new Goats("Gray", 0, 0));
                    gTot[trialSize] = gTot[trialSize - 1] + 1;
                    break;
            }
        }
    }

    // these methods simply either add the money to the troll, see the money it has and see its color.
    public void AddCoins() {
        totalMoney += 20;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public String getTrollColor() {
        return trollColor;
    }
}
