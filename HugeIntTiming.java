/*******************************************************************************
* Hamdan Basharat / basham1 / 400124515
* HugeIntTiming Class 
*******************************************************************************/
package hugeinteger;
import java.util.*; //import Java API methods

public class HugeIntTiming {  
    public static void main(String[] args) {
        String num1 = "123";
        String num2 = "126";
        System.out.println("Integer 1: "+num1);
        System.out.println("Integer 2: "+num2);
        //int num1 = 10;
        //int num2 = 10;
        
        HugeInteger test1 = new HugeInteger(num1);
        HugeInteger test2 = new HugeInteger(num2);

        System.out.println("Add: "+test1.add(test2).toString());
        System.out.println("Subtract: "+test1.subtract(test2).toString());
        System.out.println("Multiply: "+test1.multiply(test2).toString());
        System.out.println("Divide: "+test1.divide(test2).toString());
        System.out.println("Compare: "+test1.compareTo(test2));
        
        /*
        int MAXRUN = 500, MAXNUMINTS = 100, n=10;
        HugeInteger huge1, huge2, huge3;
        long startTime, endTime;
        double runTime=0.0;
        for(int numInts=0;numInts<MAXNUMINTS;numInts++){
            huge1 = new HugeInteger(1000);    
            huge2 = new HugeInteger(1000);   
            startTime = System.currentTimeMillis();
            for(int numRun=0;numRun<MAXRUN;numRun++){huge1.add(huge2);}
            endTime = System.currentTimeMillis();
            runTime += (double) (endTime-startTime)/((double) MAXRUN);
        }
        runTime = runTime/((double)MAXNUMINTS);
        System.out.println(runTime);
        */
    }
}
