import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;

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



        int[] numsToGenerate = new int[100];
        for(int i = 0; i < 100; i++) {
            numsToGenerate[i] = i + 1;
        }

        double[] discreteProbabilities = new double[100];
        for(int i = 0; i < 100; i++) {
            discreteProbabilities[i] = 0.1;
        }

        //discreteProbabilities[99] = 1.0;

        //System.out.println(numsToGenerate[99]);
        //System.out.println(discreteProbabilities[99]);

        EnumeratedIntegerDistribution distribution =
                new EnumeratedIntegerDistribution(numsToGenerate, discreteProbabilities);

        int numSamples = 10000;
        int[] x = distribution.sample(numSamples);
        int[] y = distribution.sample(numSamples);

        for(int i = 0; i < y.length; i++) {


            String point = x[i] + "," + y[i];

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
/*
    public void run() {
        //System.out.println("thread is running...");

        Random r = new Random();
        int high = 100;

        int mid = 25;

        //Map<String,Integer> pointMap = new HashMap<>();

        for(int i = 0; i < 1000000; i++) {

            //X
            if(r.nextInt(10000000) == 500) {
               this.modifierx++;
               //System.out.println("X");
            }

            //X
            if(r.nextInt(10000000) == 500) {
                this.modifiery++;
                //System.out.println("Y");
            }


            int x = r.nextInt(high) + 1;
            if((x < mid) && (this.modifierx > 0)) {

                for(int xx=0; xx <= this.modifierx; xx++) {
                    x = r.nextInt(high) + 1;
                    if(x >= mid) {
                        xx = this.modifierx;
                    }
                }
            }

            int y = r.nextInt(high) + 1;
            if((y < mid) && (this.modifiery > 0)) {

                for(int yy=0; yy <= this.modifiery; yy++) {
                    y = r.nextInt(high) + 1;
                    if(y >= mid) {
                        yy = this.modifiery;
                    }
                }
            }

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
*/
}