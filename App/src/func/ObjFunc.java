package func;

public class ObjFunc {
    private double[] tabcoeL;
    private int n;
    private Do sth;

    public ObjFunc(double[] tab,String dosth){
        tabcoeL = tab;
        n = tabcoeL.length;
        switch (dosth){
            case "min":
                sth = Do.Min;
                break;
            case "max":
                sth = Do.Max;
                break;
        }
    }

    public double[] getTabcoeL() {
        return tabcoeL;
    }
    public int getN() {
        return n;
    }
    public Do getSth() {
        return sth;
    }
}
