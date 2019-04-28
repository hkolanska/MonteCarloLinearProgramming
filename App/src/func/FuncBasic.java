package func;
public class FuncBasic {
    private double[] tabcoeL;
    private int n;
    private sign signP;
    private double valueR;

    public FuncBasic(double[] tab,String sig,double val){
        tabcoeL = tab;
        n = tabcoeL.length;
        valueR = val;
        switch (sig){
            case ">":
                signP = sign.B;
                break;
            case "<":
                signP = sign.S;
                break;
            case "=":
                signP=sign.E;
                break;
            case "<=":
                signP=sign.SE;
                break;
            case ">=":
                signP=sign.BE;
                break;        }
    }

    public int getN() {
        return n;
    }
    public double[] getTabcoeL() {
        return tabcoeL;
    }
    public double getValueR() {
        return valueR;
    }
    public sign getSignP() {
        return signP;
    }
}

