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
        int high = 1001;


        System.out.println("pointMap Start ID: " + id);

        Map<String,Integer> pointMap = new HashMap<>();

        for(int i = 0; i < 100000; i++) {

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

        System.out.println("pointMap END ID: " + id);


        System.out.println("LOCK X Y ID: " + id);

        synchronized (Launcher.lockModifier) {
            Launcher.lockModifierMapX.put(id,modifierx);
            Launcher.lockModifierMapY.put(id,modifiery);
        }

        System.out.println("SCAN ID: " + id);

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