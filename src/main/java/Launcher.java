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

    public static Map<Integer,Integer> lockModifierMapX = Collections.synchronizedMap(new HashMap<>());
    public static Map<Integer,Integer> lockModifierMapY = Collections.synchronizedMap(new HashMap<>());


    public static void main(String args[]) {

        System.out.println("height,x,y,set");


        for(int s = 1; s <=25; s++) {

            try {
                ExecutorService es = Executors.newCachedThreadPool();
                for (int i = 0; i < 40; i++)
                    es.execute(new RandomCalc(i));
                es.shutdown();
                boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);

            } catch (Exception ex) {
                ex.printStackTrace();
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

            pointMap.clear();

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