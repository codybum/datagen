import java.util.Random;

public class Launcher {


    public static void main(String args[]) {

        Random r = new Random();
        int low = 10;
        int high = 100;

        System.out.println("height,x,y,set");

        for(int s = 1; s <=25; s++) {
            for (int y = 1; y <= 100; y++) {

                for (int x = 1; x <= 100; x++) {

                    //int result = r.nextInt(high - low) + low;
                    int result = r.nextInt(100 - y) + y;


                    System.out.println(result + "," + x + "," + y + "," + s);
                }

            }
        }
    }
}