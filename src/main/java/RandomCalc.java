import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class RandomCalc implements Runnable {

    private int id;
    private int modifierx = 0;
    private int modifiery = 0;


    public RandomCalc(int id) {
        this.id = id;

        synchronized (Launcher.lockModifier) {
            if(Launcher.lockModifierMapX.containsKey(id)) {
                this.modifierx = Launcher.lockModifierMapX.get(id);
            }

            if(Launcher.lockModifierMapY.containsKey(id)) {
                this.modifierx = Launcher.lockModifierMapY.get(id);
            }
        }
    }

    public void run() {
        //System.out.println("thread is running...");

        Random r = new Random();
        int low = 0;
        int high = 101;


        Map<String,Integer> pointMap = new HashMap<>();

        for(int i = 0; i < 1000000; i++) {

            //X
            if(r.nextInt(10000) == 10000) {
               this.modifierx++;
            }

            //Y
            if(r.nextInt(10000) == 10000) {
                this.modifiery++;
            }

            int x = r.nextInt(high-low) + modifierx;
            int y = r.nextInt(high-low) + modifiery;

            String point = x + "," + y;

            if(pointMap.containsKey(point)) {
                pointMap.put(point,pointMap.get(point) + 1);
            } else {
                pointMap.put(point,1);
            }

        }

        synchronized (Launcher.lockModifier) {
            Launcher.lockModifierMapX.put(id,modifierx);
            Launcher.lockModifierMapY.put(id,modifiery);
        }

        synchronized (Launcher.pointQueueLock) {
            Launcher.pointQueue.offer(pointMap);
        }

        /*
        for (Map.Entry<String, Integer> entry : pointMap.entrySet()) {

            String key = entry.getKey();
            Integer value = entry.getValue();

            //System.out.println(key + " " + value);
            System.out.println("LOCKED ID: " + id);
            synchronized (Launcher.lockPointMap) {
                if(Launcher.pointMap.containsKey(key)) {
                    Launcher.pointMap.put(key,Launcher.pointMap.get(key)+value);
                } else {
                    Launcher.pointMap.put(key,value);
                }
            }
            System.out.println("UNLOCKED ID: " + id);
        }
        */


    }
}