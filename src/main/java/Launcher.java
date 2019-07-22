import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Launcher {

    public static Map<String,Integer> pointMap = Collections.synchronizedMap(new HashMap<>());

    public static Map<Integer,Map<String,Integer>> processorsPointMap = Collections.synchronizedMap(new HashMap<>());

    public static AtomicBoolean lockProcessorsPointMap = new AtomicBoolean();
    public static AtomicBoolean lockPointMap = new AtomicBoolean();
    public static AtomicBoolean lockModifier = new AtomicBoolean();



    public static Map<Integer,Integer> lockModifierMapX = Collections.synchronizedMap(new HashMap<>());
    public static Map<Integer,Integer> lockModifierMapY = Collections.synchronizedMap(new HashMap<>());

    public static AtomicBoolean pointQueueLock = new AtomicBoolean();
    public static BlockingQueue<Map<String,Integer>> pointQueue = new LinkedBlockingQueue<>();

    public static void main(String args[]) {

//public SigmoidBiasedDistribution(int min, int max, double minsigmoidscalefactor, double maxsidmoidscalefactor, double maxoffsetfactor) {


        /*
        try {
            SigmoidBiasedDistribution sbd = new SigmoidBiasedDistribution(0, 100,  0, 1.0, 0.0);
            int[] pts = sbd.getPoints(100);
            for (int i = 0; i < pts.length; i++) {
                System.out.println(pts[i]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        */

        WeightedRandomGenerator wrg = new WeightedRandomGenerator(1, 100, 2, 0.95);

        int count = 0;

        for(int i = 0; i < 1000; i++) {
            if(wrg.nextInt() >= 50) {
                count++;
            }
        }

        System.out.println(count);

        System.exit(0);

        System.out.println("height,x,y,set");


        for(int s = 1; s <=10; s++) {

            try {

                ExecutorService es = Executors.newCachedThreadPool();
                for (int i = 0; i < 80; i++)
                    es.execute(new RandomCalc(i));
                es.shutdown();


                //boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);
                while (!es.isTerminated()) {
                    Thread.sleep(1000);
                }

                while(!pointQueue.isEmpty()) {

                    Map<String,Integer> tmpPointMap = pointQueue.poll();

                    for (Map.Entry<String, Integer> entry : tmpPointMap.entrySet()) {

                        String key = entry.getKey();
                        Integer value = entry.getValue();

                        //System.out.println(key + " " + value);
                        //synchronized (Launcher.lockPointMap) {
                            if(pointMap.containsKey(key)) {
                                pointMap.put(key,pointMap.get(key)+value);
                            } else {
                                pointMap.put(key,value);
                            }
                        //}
                    }

                }



            } catch (Exception ex) {
                ex.printStackTrace();
            }

            /*
            for (Map.Entry<String, Integer> entry : pointMap.entrySet()) {

                String key = entry.getKey();
                Integer result = entry.getValue();

                System.out.println(result + "," + key + "," + s);
            }
            */



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