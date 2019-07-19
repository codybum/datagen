import java.util.Random;

public class Launcher {


    public static void main(String args[]) {

        Random r = new Random();
        int low = 10;
        int high = 100;

        System.out.println("height,x,y,set");

        for(int s = 1; s <=25; s++) {
            for (int y = 1; y <= 61; y++) {

                for (int x = 1; x <= 87; x++) {
                    //int result = r.nextInt(high - low) + low;

                    System.out.println((x + y) + "," + x + "," + y + "," + s);
                }

            }
        }
    }
}