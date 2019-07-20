import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Launcher {

    public static Map<String,Integer> pointMap = Collections.synchronizedMap(new HashMap<>());

    public static AtomicBoolean lockPointMap = new AtomicBoolean();

    public static AtomicBoolean lockModifier = new AtomicBoolean();

    public static Integer modifier = 0;


    public static void main(String args[]) {

        System.out.println("height,x,y,set");


        for(int s = 1; s <=100; s++) {

            try {
                ExecutorService es = Executors.newCachedThreadPool();
                for (int i = 0; i < 100; i++)
                    es.execute(new RandomCalc());
                es.shutdown();
                boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);

            } catch (Exception ex) {
                ex.printStackTrace();
            }


            for(int y = 1; y <= 100; y++) {

                for (int x = 1; x <= 100; x++) {

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