/**
 * Created by simon on 10/21/2016.
 */
public class test {

    public int returnNums(int num) {
        if (num > 0) {
            System.out.println(num);
            return returnNums(num-1);
        }
        return 0;
    }
    public static void main(String args[]) {
        test newTest = new test();
        newTest.returnNums(10);
    }
}
