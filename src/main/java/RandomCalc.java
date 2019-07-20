import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class RandomCalc implements Runnable {
    public void run() {
        //System.out.println("thread is running...");

        Random r = new Random();
        int low = 0;
        int high = 1001;


        Map<String,Integer> pointMap = new HashMap<>();

        for(int i = 0; i < 1000000; i++) {

            int x = r.nextInt(high-low) + Launcher.modifier;
            int y = r.nextInt(high-low) + Launcher.modifier;

            String point = x + "," + y;

            if(pointMap.containsKey(point)) {
                pointMap.put(point,pointMap.get(point) + 1);
            } else {
                pointMap.put(point,1);
            }

        }


        for (Map.Entry<String, Integer> entry : pointMap.entrySet()) {

            String key = entry.getKey();
            Integer value = entry.getValue();

            //System.out.println(key + " " + value);

            synchronized (Launcher.lockPointMap) {
                if(Launcher.pointMap.containsKey(key)) {
                    Launcher.pointMap.put(key,Launcher.pointMap.get(key)+value);
                } else {
                    Launcher.pointMap.put(key,value);
                }
            }
        }


    }
}