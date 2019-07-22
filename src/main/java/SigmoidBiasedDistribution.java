public class SigmoidBiasedDistribution {
    private MersenneTwisterFast mtf;
    private int min;
    private int max;
    private double minsigmoidscalefactor;
    private double maxsidmoidscalefactor;
    private double maxoffsetfactor;

    public SigmoidBiasedDistribution(int min, int max, double minsigmoidscalefactor, double maxsidmoidscalefactor, double maxoffsetfactor) {
        this.mtf = new MersenneTwisterFast();
        this.minsigmoidscalefactor = minsigmoidscalefactor;
        this.maxsidmoidscalefactor = maxsidmoidscalefactor;
        this.maxoffsetfactor = maxoffsetfactor;
        this.min = min;
        this.max = max;
    }

    public int[] getPoints(int points) {
        int[] pts = new int[points];
        double lowoffset = mtf.nextDouble(true, true) * maxoffsetfactor;
        double highoffset = mtf.nextDouble(true, true) * maxoffsetfactor;
        double randomsigmoidscalefactor = mtf.nextDouble(true, true) * (maxsidmoidscalefactor - minsigmoidscalefactor) + minsigmoidscalefactor;
        //System.out.println(randomsigmoidscalefactor);
        double pointsoffset = ((1 - highoffset) - lowoffset) / (points - 1);
        for(int i = 0; i < points; i++) {
            double offset = (i * pointsoffset) + lowoffset;
            double scalefactor = getHalfSigmoid(offset, randomsigmoidscalefactor);
            pts[i] = (int) (scalefactor * (max - min)) + min;
        }
        return pts;
    }

    //sigmoid scale factor can range from -1 to 1 (-1 represents most aggressive initial slope, 1 represents least aggressive initial slope, 0 is linear)
//https://www.desmos.com/calculator/tswgrnoosy
    public double getHalfSigmoid(double value, double sigmoidscalefactor) {
        return (value - value * sigmoidscalefactor) / (sigmoidscalefactor - 2 * Math.abs(value) * sigmoidscalefactor + 1);
    }

    /*
    public static void main(String[] args) {
        SigmoidBiasedDistribution sbd = new SigmoidBiasedDistribution(5, 80, 5, 0, 0.5, 0.2);
        int[] pts = sbd.getPoints(3);
        for(int i = 0; i < pts.length; i++) {
            System.out.println(pts[i]);
        }
    }
    */
}