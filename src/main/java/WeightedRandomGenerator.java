
import java.util.ArrayList;
import java.util.List;


public class WeightedRandomGenerator {
    private double sigmoidscalefactor;
    private MersenneTwisterFast mtf;
    private List<Integer> points;
    private int min;
    private int max;
    private int numcompletelyrandom;

    public WeightedRandomGenerator(int min, int max, int numcompletelyrandom, double sigmoidscalefactor) {
        this.mtf = new MersenneTwisterFast();
        this.points = new ArrayList<Integer>();
        this.numcompletelyrandom = numcompletelyrandom;
        this.sigmoidscalefactor = sigmoidscalefactor;
        this.min = min;
        this.max = max;
        clear();
    }

    public int nextInt() {
        if(points.size() - 2 < numcompletelyrandom) {
            int nextint = mtf.nextInt(max - min + 1) + min;
            points.add(nextint);
            return nextint;
        }
        else {
            int maxsep = getMaxSeparation();
            while(true) {
                int nextint = mtf.nextInt(max - min + 1) + min;
                int nearestneighbor = getNearestNeighbor(nextint);
                double sepfrac = (double) nearestneighbor / (double) maxsep;
                if(mtf.nextBoolean(getHalfSigmoid(sepfrac))) {
                    points.add(nextint);
                    return nextint;
                }
            }
        }
    }

    private int getNearestNeighbor(int nextint) {
        int delta = Integer.MAX_VALUE;
        for(int i = 0; i < points.size(); i++) {
            delta = Math.min(delta, Math.abs(nextint - points.get(i)));
        }
        return delta;
    }

    private int getMaxSeparation() {
        int maxsep = 0;
        for(int i = 1; i < points.size(); i++) {
            int delta = points.get(i) - points.get(i-1);
            maxsep = Math.max(delta, maxsep);
        }
        return maxsep;
    }

    private void clear() {
        points.clear();
        points.add(min);
        points.add(max);
    }

    //sigmoid scale factor can range from -1 to 1 (-1 represents most aggressive initial slope, 1 represents least aggressive initial slope, 0 is linear)
//https://www.desmos.com/calculator/tswgrnoosy
    public double getHalfSigmoid(double value) {
        return (value - value * sigmoidscalefactor) / (sigmoidscalefactor - 2 * Math.abs(value) * sigmoidscalefactor + 1);
    }

    public static void main(String[] args) {
        WeightedRandomGenerator wrg = new WeightedRandomGenerator(1, 80, 2, 0.95);
        for(int i = 0; i < 5; i++) {
            System.out.println(wrg.nextInt());
        }
    }
}