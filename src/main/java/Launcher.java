import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Launcher {


    public static void main(String args[]) {

        System.out.println("height,x,y,set");

        Random r = new Random();
        int low = 0;
        int high = 1000;

        for(int s = 1; s <=1000; s++) {

            Map<String,Integer> pointMap = new HashMap<>();

            for(int i = 0; i < 10000000; i++) {
            //for(int i = 0; i < 10000; i++) {

                //int x = r.nextInt(high-low) + low;
                //int y = r.nextInt(high-low) + low;
                int x = r.nextInt(high-low) + s;
                int y = r.nextInt(high-low) + s;

                String point = x + "," + y;

                if(pointMap.containsKey(point)) {
                    pointMap.put(point,pointMap.get(point) + 1);
                } else {
                    pointMap.put(point,1);
                }

            }

            for(int y = 1; y <= 1000; y++) {

                for (int x = 1; x <= 1000; x++) {

                    int result = 0;
                    String point = x + "," + y;
                    if(pointMap.containsKey(point)) {
                        result = pointMap.get(point);
                    }
                        System.out.println(result + "," + x + "," + y + "," + s);
                }

            }

            /*
            for (Map.Entry<String, Integer> entry : pointMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                System.out.println(value + "," + key + "," + s);
            }
            */



        }


    }
}