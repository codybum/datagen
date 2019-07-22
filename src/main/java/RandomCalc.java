import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class RandomCalc implements Runnable {

    private int id;
    private int modifierx = 0;
    private int modifiery = 0;

    private Map<String,Integer> pointMap;

    public RandomCalc(int id) {
        this.id = id;

        synchronized (Launcher.lockModifier) {
            if(Launcher.lockModifierMapX.containsKey(id)) {
                this.modifierx = Launcher.lockModifierMapX.get(id);
            }

            if(Launcher.lockModifierMapY.containsKey(id)) {
                this.modifiery = Launcher.lockModifierMapY.get(id);
            }
        }

        synchronized (Launcher.lockProcessorsPointMap) {

            if(Launcher.processorsPointMap.containsKey(id)) {

                pointMap = Launcher.processorsPointMap.get(id);

            } else {
                pointMap = new HashMap<>();
            }

        }


    }

    public void run() {
        //System.out.println("thread is running...");

        Random r = new Random();
        int high = 100;


        //Map<String,Integer> pointMap = new HashMap<>();

        for(int i = 0; i < 10000; i++) {

            //X
            if(r.nextInt(1000) == 500) {
               this.modifierx++;
            }

            //X
            if(r.nextInt(1000) == 500) {
                this.modifiery++;
            }


            int x = r.nextInt(high) + 1;

            int y = r.nextInt(high) + 1;


            String point = x + "," + y;

            if(pointMap.containsKey(point)) {
                pointMap.put(point,pointMap.get(point) + 1);
            } else {
                pointMap.put(point,1);
            }

        }

        synchronized (Launcher.lockProcessorsPointMap) {
            //Launcher.processorsPointMap.put(id,pointMap);
        }

        synchronized (Launcher.lockModifier) {
            Launcher.lockModifierMapX.put(id,modifierx);
            Launcher.lockModifierMapY.put(id,modifiery);
        }

        synchronized (Launcher.pointQueueLock) {
            Launcher.pointQueue.offer(pointMap);
        }



    }
}