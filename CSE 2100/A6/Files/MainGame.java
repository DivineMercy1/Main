import java.util.ArrayList;

/**
 * Created by norse on 11/5/2016.
 */
public class MainGame {

    public static void main(String args[]) {
        // I was going to use an arraylist instead but that's not the requirement.
        ArrayList<Goats> arrGoats = new ArrayList<>();
        // Here we make our initial goats and trolls
        Goats wGoat = new Goats("White", 0, 0);
        Goats bGoat = new Goats("Black", 0, 0);
        Bridges wTroll = new Bridges("White", 0);
        Bridges bTroll = new Bridges("Black", 0);
        Bridges gTroll = new Bridges("Gray", 0);
        wTroll.insert(wGoat, 0);
        wTroll.insert(bGoat, 0);
        wTroll.priorityCheck(wGoat, bGoat, bTroll);
        for (int i = 0; i < 10001; i++) {
            // Do all stuff here
        }
    }
}
