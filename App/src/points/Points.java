package points;
import func.FuncBasic;
import func.ObjFunc;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Points {
    private  ArrayList <Pair<double[],Double>> listOfPoints = new ArrayList<>();
    public List<Pair<double[],Double>> getListOfPoints() {
        return listOfPoints;
    }
    private  double[] drawRandomPoint (ArrayList<FuncBasic> allf, double min, double max){
        int n = allf.get(0).getN();
        double[] tab = new double[n];
        for (int i=0;i<n;i++){
           tab[i]= ThreadLocalRandom.current().nextDouble(min, max);
        }
        return tab;
    }
    private  double drawRandomCord (double min, double max){
        double cord= ThreadLocalRandom.current().nextDouble(min, max);
        return cord;
    }
    private void cleanLists(){
        listOfPoints.clear();
    }
    private boolean check(FuncBasic func, double[] point){
        double sum =0;
        for(int i=0;i<func.getN();i++){
            sum=sum+func.getTabcoeL()[i]*point[i];
        }
        switch (func.getSignP()){
            case B:
                if (sum>func.getValueR()){return true;}
                else{return false;}
            case BE:
                if (sum>=func.getValueR()){return true;}
                else{return false;}
            case S:
                if (sum<func.getValueR())
                {return true;}
                else{return false;}
            case SE:
                if (sum<=func.getValueR())
                {return true;}
                else{return false;}
            case E:
                if (sum==func.getValueR())
                {return true;}
                else{return false;}

        }
    return false;
    };
    private  boolean chckAllFunc(ArrayList<FuncBasic> allFunc, double[] point){
        for (int i=0;i<allFunc.size();i++){
            if (!check(allFunc.get(i),point))
                return false;
        }
        return true;
    }
    private  Double getObjVal(ObjFunc func, double[] point){
        double sum =0;
        for(int i=0;i<func.getN();i++){
            sum=sum+func.getTabcoeL()[i]*point[i];
        }
      return sum;
    }
    private  double  findMin(ArrayList<FuncBasic> allFunc){
        return 0;
    };
    private  double findMax(ArrayList<FuncBasic> allFunc){
        double max = 0;
        for (int i=0;i<allFunc.size();i++)
        {
            if (allFunc.get(i).getValueR()>max)
                max = allFunc.get(i).getValueR();
        }
        return max*10;
    };
    private  void findOkPoints(ArrayList<FuncBasic> allFunc, ObjFunc objf){

        double min = findMin(allFunc);
        double max = findMax(allFunc);
        for(int i=0;i<100000;i++) {
            double[] point = drawRandomPoint(allFunc, min, max);
            if (chckAllFunc(allFunc,point)){
                Pair p = new Pair(point,getObjVal(objf, point));
                listOfPoints.add(p);
            }
        }
    }
    private  void findOkPointsSph(ArrayList<FuncBasic> allFunc, ObjFunc objf,double[] point, double dis){
        for(int i=0;i<10000;i++) {
            double[] point2 = drawPointinSph(point,dis);
            if (chckAllFunc(allFunc,point2)){
                Pair p = new Pair(point2,getObjVal(objf, point2));
                listOfPoints.add(p);
            }
        }
    }
    private ArrayList<Pair<double[],Double>> findTheSmallest(){
        ArrayList<Pair<double[],Double>> bestPoints = new ArrayList<>();
        bestPoints.add(listOfPoints.get(0));
        for (int i=1;i<listOfPoints.size();i++){
//            System.out.println(listOfPoints.get(i).getValue() + "  "+ bestPoints.get(0).getValue());
            if(listOfPoints.get(i).getValue()<bestPoints.get(0).getValue()){
                bestPoints.clear();
                bestPoints.add(listOfPoints.get(i)); }
            else{
                if (listOfPoints.get(i).getValue()==bestPoints.get(0).getValue()) {
                    bestPoints.add(listOfPoints.get(i));
                }
            }
            listOfPoints.removeAll(bestPoints);
        }

        return bestPoints;
    }
    private ArrayList<Pair<double[],Double>> findTheBiggest(){
        ArrayList<Pair<double[],Double>> bestPoints = new ArrayList<>();
        bestPoints.add(listOfPoints.get(0));
        for (int i=1;i<listOfPoints.size();i++){
//            System.out.println(listOfPoints.get(i).getValue() + "  "+ bestPoints.get(0).getValue());
            if(listOfPoints.get(i).getValue()>bestPoints.get(0).getValue()){
                bestPoints.clear();
                bestPoints.add(listOfPoints.get(i)); }
            else{
                if (listOfPoints.get(i).getValue()==bestPoints.get(0).getValue()) {
                    bestPoints.add(listOfPoints.get(i));
                }
            }
            listOfPoints.removeAll(bestPoints);
        }

        return bestPoints;
    }
    private ArrayList<Pair<double[],Double>> findTheBest(ObjFunc objf) {
        ArrayList<Pair<double[],Double>> bestPoints = new ArrayList<>();
        switch (objf.getSth()) {
            case Min:
                bestPoints = findTheSmallest();
                break;
            case Max:
                bestPoints = findTheBiggest();
                break;
        }
        return bestPoints;
    }
    private double distance (double[] point, double[] point2){
        double sum =0;
        for(int i=0;i<point.length;i++){
            sum= sum+Math.pow(point[i]-point2[i],2);
        }
        sum = Math.sqrt(sum);
        return sum;
    }
    private double findNearesrDis (double[] point,ObjFunc objf){
        double[] point2 = findTheBest(objf).get(0).getKey();
        double bestDis = distance(point,point2);


        return bestDis;
    }
    private double[] drawPointinSph(double[] point, double dis){
        double[] point2 = new double[point.length];
        for (int i=0;i<point.length;i++){
            point2[i] = ThreadLocalRandom.current().nextDouble(point[i]-dis, point[i]+dis);
        }
        return point2;
    }
    public static ArrayList<Pair<double[], Double>> mainApp(ArrayList<FuncBasic> allf, ObjFunc objf){
        Points obj = new Points();
        obj.findOkPoints(allf,objf);
        ArrayList<Pair<double[],Double>> bests = obj.findTheBest(objf);
        double dis = obj.findNearesrDis(bests.get(0).getKey(),objf);
        double eps = 0.000000000001;
        while (dis > eps){
            obj.cleanLists();
            for (int i =0; i<bests.size();i++){
                obj.findOkPointsSph(allf,objf,bests.get(i).getKey(),dis);
            }
            bests.clear();
            bests = obj.findTheBest(objf);
            dis = obj.findNearesrDis(bests.get(0).getKey(),objf);

        }
        return bests;



    }

    public static void main(String[] args){
        double[] tab1 = {30.0,20.0};
        double[] tab2 = {2.0,1.0};
        double[] tab3 = {1.5,0};
        double[] tab4 = {3.0,3.0};

        ArrayList<FuncBasic> allf = new ArrayList<>();

        FuncBasic func1 = new FuncBasic(tab2,"<=",1000);
        FuncBasic func2 = new FuncBasic(tab4,"<=",2400);
        FuncBasic func4 = new FuncBasic(tab3,"<=",600);
        ObjFunc funcobj = new ObjFunc(tab1,"max");
        allf.add(func1);allf.add(func2);allf.add(func4);
        Points obj = new Points();

        ArrayList<Pair<double[],Double>> list = mainApp(allf,funcobj);
      //  System.out.println(list.size());
        System.out.println(list.get(0).getKey()[0] +" "+ list.get(0).getKey()[1] + " " + list.get(0).getValue());
        //System.out.println(obj.getObjVal(funcobj,new double[] {200,600}));


        tab1 = new double[]{3.0,9.0};
        tab2 = new double[]{8.0,4.0};
        tab3 = new double[]{12.0,3.0};
        tab4 = new double[]{6.0,9.0};

         func1 = new FuncBasic(tab1,">=",27);
         func2 = new FuncBasic(tab2,">=",32);
         func4 = new FuncBasic(tab3,">=",36);
         funcobj = new ObjFunc(tab4,"min");
         ArrayList<FuncBasic> allf1 = new ArrayList<>();
        allf1.add(func1);allf1.add(func2);allf1.add(func4);
        Points obj1 = new Points();

        ArrayList<Pair<double[],Double>> list1 = mainApp(allf1,funcobj);
        System.out.println(list1.get(0).getKey()[0] +" "+ list1.get(0).getKey()[1] + " " + list1.get(0).getValue());



    }


}


