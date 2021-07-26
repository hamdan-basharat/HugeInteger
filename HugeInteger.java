/*******************************************************************************
* Hamdan Basharat / basham1 / 400124515
* HugeInteger Class 
*******************************************************************************/
package hugeinteger;
import java.util.*; //import Java API methods

public class HugeInteger {                 
    public String huge; //initializes the variable for the huge number
    private int neg; //checks if there is a minus sign
    private boolean larger; //variable showing if this integer is larger than the other
    private boolean check=false; //variable used to ensure the program wont go back to a function it was called from
    /***************************************************************************
    * Constructor: If the user inputs the integer
    * @param: type String
    ***************************************************************************/
    public HugeInteger(String val){
        int j=0;
        if(val.charAt(0)=='-'){ //checks if the input had a negative sign, if so starts for loop at second index
            j++;
            neg = 1;
        }
        for(int i=j;i<val.length();i++){ //checks if string only has characters representing digits  
            if(!Character.isDigit(val.charAt(i))){
                throw new IllegalArgumentException("Invalid string input."); //exception if the character isnt a floating point decimal
            }
        } 
        if(neg==1){val=val.replaceAll("-","");}
        huge = val; //assigns input string to variable for huge number
    }
    /***************************************************************************
    * Constructor: If the user inputs the number of digits 
    * @param: type integer
    ***************************************************************************/
    public HugeInteger(int n){
        if(n<1) //exception if the user inputed a value less than one
            throw new IllegalArgumentException("n must be larger than 1.");
        char[] temp = new char[n]; //creates a temporary char array to hold the random digits
        Random rand = new Random();
        int q = rand.nextInt(2)+1;
        if(q==1){this.neg=1;}//randomly generates chance that the number is negative
        for(int i=0;i<n;i++){ //scans through the char array and fills it with random digits from 48 to 57
            int a = rand.nextInt(10) + 48; 
            temp[i] = (char)a; //converts floating point decimals to characters
        }
        if(temp[0]=='0'){ //if the first random number was 0, replaces it with a random number from 1-9
            int b = rand.nextInt(10) + 49;
            temp[0] = (char)b;
        }
        huge = new String(temp); //assigns the char array to the number as a string 
    }
    /***************************************************************************
    * Method: Adds two huge integers together
    * @param: type HugeInteger @return: type HugeInteger
    ***************************************************************************/
    public HugeInteger add(HugeInteger h){
        HugeInteger sum; //initalizes the sum integer 
        
        if(this.huge.length()==h.huge.length() && this.neg==1 && h.neg==0 && this.check==true){ //scenario for when the integers have the same number of digits and one is negative
            this.check=false;
            sum=this.subtract(h);
        }
        if(this.neg!=h.neg && this.check==false){ //if the integers dont have the same signs  calls subtract 
            this.check=true; //makes sure subtract doesnt call back to add
            sum=this.subtract(h);
        }
        else{   
            String summed = new String(); //initlaizes a new string to store digits of the huge int
            int num, carry = 0;
            
            String big = (this.huge.length()>h.huge.length())? this.huge : h.huge; //checks which integer has more digits and assigns them as big and small respectively
            String small = (this.huge.length()<h.huge.length())? this.huge : h.huge;
            if(small.length()==big.length()){small=this.huge; big=h.huge;} //if they have the same number of digits assigns small and big accordingly
            while(big.length()>small.length()){small="0"+small;} //pads the smaller number with zeros so that they both have the same number of digits
            
            //algorithm for add
            for(int i=big.length()-1;i>=0;i--){//runs for the length of the big integer
                num=(small.charAt(i)+big.charAt(i)-96+carry); //adds the sum with the carry
                if(num>9){ //if the number is greater than a single digits, creates a carry over
                    num-=10;
                    carry=1;
                }
                else{carry=0;}
                summed=num+summed; //adds the sum to the stirng
            }
            if(carry==1){summed=carry+summed;}//if there is still a carry after the algorithm, adds it to the front of the string
            sum = new HugeInteger(summed);
            if(this.neg==1 && h.neg==1){sum.neg=1;} //if both numbers were negative, the sum is made negative 
        }
    this.check=false; 
    return sum; //returns the summed integer
    }
    /***************************************************************************
    * Method: Subtracts two huge integers from each other
    * @param: type HugeInteger @return: type HugeInteger
    ***************************************************************************/
    public HugeInteger subtract(HugeInteger h){
        HugeInteger hcopy = new HugeInteger(h.huge);//creates a copy of this and h HugeIntegers
        HugeInteger thiscopy = new HugeInteger(this.huge);
        hcopy.neg=h.neg; thiscopy.neg=this.neg;
        HugeInteger diff; //intializes the difference integer
        
        if(this.huge=="0"){h.neg=1; return h;} //if one of the numbers is zero, the sign is assigned accordingly
        if(h.huge=="0"){return this;}
        if(this.magnitude(h)==0){return new HugeInteger("0");} //if two of the same number are being subtracted, zero is returned
        if(this.huge.length()==h.huge.length() && this.neg==1 && h.neg==0 && this.check==false){//scenario for when the integers have the same number of digits and one is negative
            this.check=true;
            h.neg=1;
            return this.add(h);
        }
        if(thiscopy.huge.length()==hcopy.huge.length() && this.neg==1 && h.neg==0 && this.check==true){//scenario for when the integers have the same number of digits and one is negative if sent from add
            thiscopy = new HugeInteger(h.huge);
            hcopy = new HugeInteger(this.huge);
            this.neg=0;
        }
        if(this.neg!=h.neg && this.check==false){//if the integers dont have the same signs  calls add
            this.check=true; //makes sure if add doesnt call back subtract
            diff=this.add(h);
            diff.neg=this.neg;
            return diff; //returns the difference hugeinteger 
        }
        else{
            int num; int carry = 0; String difference = new String();
            HugeInteger big=(thiscopy.huge.length()>hcopy.huge.length()) ? new HugeInteger(this.huge) : new HugeInteger(h.huge); //checks which integer has more digits and assigns them as big and small respectively
            HugeInteger small=(thiscopy.huge.length()<hcopy.huge.length()) ? new HugeInteger(this.huge) : new HugeInteger(h.huge);
            if(thiscopy.huge.length()==big.huge.length()){//scenarios for if the numbers have the same number of digits
                if (thiscopy.magnitude(hcopy)==1){//for when this is larger than h
                    big=new HugeInteger(thiscopy.huge);
                    small=new HugeInteger(hcopy.huge);
                }
                else{//for when h is greater than this
                    big=new HugeInteger(hcopy.huge);
                    small=new HugeInteger(thiscopy.huge);
                }
            }
            while(big.huge.length()>small.huge.length()){small.huge="0"+small.huge;}//pads the smaller number with zeros so that they both have the same number of digits
            
            //algorithm for subtract
            for(int i=big.huge.length()-1;i>=0;i--){//runs for the length of the big integer
                if (big.huge.charAt(i)+carry<small.huge.charAt(i)){//if the number isnt large enough to subtract from
                    num=(big.huge.charAt(i)-small.huge.charAt(i)+10+carry);//subtracts the number 
                    carry=-1; //makes a negative carry 
                }
                else{
                    num=(big.huge.charAt(i)-small.huge.charAt(i)+carry);//subtracts the number
                    carry=0;
                }
                difference=num+difference;//adds the number to the difference string
            }
            diff=new HugeInteger(difference);
            diff.removeZeros(); //removes any leading zeros 
        
            if(thiscopy.neg==0 && hcopy.neg==1){diff.neg=thiscopy.magnitude(hcopy)==1 ? 0 : 1;} //scenarios for assigning the sign of the difference integer
            if(thiscopy.neg==1 && hcopy.neg==0){diff.neg=thiscopy.magnitude(hcopy)==1 ? 1 : 0;}
            if(thiscopy.neg==1 && hcopy.neg==1){diff.neg=thiscopy.magnitude(hcopy)==1 ? 1 : 0;}
            if(thiscopy.neg==0 && hcopy.neg==0){diff.neg=thiscopy.magnitude(hcopy)==1 ? 0 : 1;}
            if(thiscopy.huge.length()==hcopy.huge.length() && this.neg==1 && h.neg==1){
                diff.neg=this.magnitude(h)==1 ? 1 : 0;
            }
        return diff;//returns the difference of the two huge integers
        }
    }
    /***************************************************************************
    * Method: Multiplies two huge integers together
    * @param: type HugeInteger @return: type HugeInteger
    ***************************************************************************/
    public HugeInteger multiply(HugeInteger h){      
        String int1 = new StringBuilder(this.huge).reverse().toString(); //creates new strings that is the reverse of each of the huge integers
        String int2 = new StringBuilder(h.huge).reverse().toString();
        StringBuilder stringProd = new StringBuilder(); //creates a new string to store the product (uses StringBuilder in order to use StringBuilder methods)
        
        if(this.huge=="0" ||h.huge=="0"){return new HugeInteger("0");}
        int[] prod = new int[this.huge.length()+h.huge.length()]; //creates a int array to store multiplied values (will be at most the size of the digits of both integers added)
        for(int i=0;i<this.huge.length();i++){
            for(int j=0;j<h.huge.length();j++)
                prod[i+j]+=(int1.charAt(i)-48)*(int2.charAt(j)-48); //multiplies all the digits of the second number by the current digit of the first number
        }
        for(int k=0;k<prod.length;k++){
            int indexValue = prod[k]%10; //gets value in the 'ones' position
            int carry = prod[k]/10; //gets value in the 'tens' position
            if(k+1<prod.length){prod[k+1]+=carry;} //adds carry to the next index
            stringProd = stringProd.insert(0,indexValue); //adds the 'ones' value to the string representing product
        }

        String product = new String(stringProd);//creates a String using the character array
        product = product.trim();//gets rid of null spaces
        HugeInteger returnProd = new HugeInteger(product); 
        if((this.neg==1&&h.neg==0) || (this.neg==0&&h.neg==1))//sets neg to 1 if there should be a minus sign
            returnProd.neg=1;
        return returnProd;//returns the product of the huge integers
    }
    /***************************************************************************
    * Method: Divides a huge integer from another
    * @param: type HugeInteger @return: type HugeInteger
    ***************************************************************************/
    public HugeInteger divide(HugeInteger h){
        HugeInteger dividend = new HugeInteger(this.huge); //creates copies of this and h huge integers 
        HugeInteger divisor = new HugeInteger(h.huge);
        if(dividend.huge=="0"){return new HugeInteger("0");} //scenarios for if one of the numbers is zero
        if(divisor.huge=="0"){throw new IllegalArgumentException("Cant divide by zero.");}
        dividend.neg=0; divisor.neg=0;
        HugeInteger one = new HugeInteger("1"); //creates constant huge integers to do math with
        HugeInteger zero = new HugeInteger("0");
        HugeInteger quotient= new HugeInteger("0"); //creates a flag to return as the quotient
        if(dividend.magnitude(divisor)==0){ //if both numbers are the same 
            quotient.huge="1";
            if(this.neg!=h.neg){quotient.neg=1;}//assigns the sign of the quotient
            return quotient; 
        }
        while(dividend.compareTo(divisor)==1){//counts the number of times the divisor and can be subtracted from the dividend (quotient)
            dividend = dividend.subtract(divisor);
            quotient = quotient.add(one); //counter
        }
        if(dividend.compareTo(divisor)==0){//if the loop stopped when the dividend equals the divisor, adds one more to the counter
            quotient = quotient.add(one);
        }
        if(this.neg!=h.neg){quotient.neg=1;}//assigns the sign of the quotient
        return quotient; //returns the quotient of the huge integers
    }
    /***************************************************************************
    * Method: Compares the magnitude of two huge integers
    * @param: type HugeInteger @return: type integer
    ***************************************************************************/
    public int magnitude(HugeInteger h){
        int flag=0; //sets a flag that can be altered to 1, -1 or left as 0
        this.removeZeros(); //removes leading zeros
        h.removeZeros();
        if(this.huge.equals(h.huge)){flag=0;return flag;} //if the numbers are the same
        if(this.huge.length()!=h.huge.length()) //if the numbers dont have the same number of digits
            flag = this.huge.length()>h.huge.length()? 1 : -1;
        else{
            for(int i=0;i<this.huge.length();i++){ //loop that checks if the first element in one is larger than the other
                if(this.huge.charAt(i)==h.huge.charAt(i))//if the element is the same it moves to the next element
                     continue;
                if(this.huge.charAt(i)!=h.huge.charAt(i)){
                        flag = this.huge.charAt(i)>h.huge.charAt(i)? 1 : -1 ;
                        break;
                }
            }
        }
        return flag;//returns a value indicating if the HugeInt is larger, smaller, or equal in value
    }
    /***************************************************************************
    * Method: Compares the value of two huge integers
    * @param: type HugeInteger @return: type integer
    ***************************************************************************/
    public int compareTo(HugeInteger h){
        int flag=0; //sets a flag that can be altered to 1, -1 or left as 0
        this.removeZeros();//removes leading zeros
        h.removeZeros();
        if(this.neg==0 && h.neg==0){//if both numbers are positive
            if(this.huge.equals(h.huge)){flag=0;return flag;}
            if(this.huge.length()!=h.huge.length())
                flag = this.huge.length()>h.huge.length()? 1 : -1;
            else{
                for(int i=0;i<this.huge.length();i++){ //loop that checks if the first element in one is larger than the other
                    if(this.huge.charAt(i)==h.huge.charAt(i))//if the element is the same it moves to the next element
                        continue;
                    if(this.huge.charAt(i)!=h.huge.charAt(i)){
                        flag = this.huge.charAt(i)>h.huge.charAt(i)? 1 : -1 ;
                        break;
                    }
                }
            }
        }
        else if(this.neg==1 && h.neg==0){flag=-1;}//if one number is positive and the other is negative, the positive is larger
        else if(this.neg==0 && h.neg==1){flag=1;}
        else{//if both numbers are negative
            if(this.huge.equals(h.huge)){flag=0;return flag;}
            if(this.huge.length()!=h.huge.length())
                flag = this.huge.length()<h.huge.length()? 1 : -1;//since both numbers are negative, the smaller absolute value is greater one hte number line
            else{
                for(int i=0;i<this.huge.length();i++){//loop that checks if the first element in one is smaller than the other
                    if(this.huge.charAt(i)==h.huge.charAt(i))//if the element is the same it moves to the next element
                        continue;
                    if(this.huge.charAt(i)!=h.huge.charAt(i)){
                        flag = this.huge.charAt(i)<h.huge.charAt(i)? 1 : -1 ;
                        break;
                    }
                }
            }  
        }
        return flag;//returns a value indicating if the HugeInt is larger, smaller, or equal in value
    }
    /***************************************************************************
    * Method: Removes the leading zeros from the huge integer
    * @param: none @return: type HugeInteger
    ***************************************************************************/
    public HugeInteger removeZeros(){
        if(this.huge.length()>1){//only runs if the number is greater than one digit
            int i = 0; 
            while (this.huge.charAt(i)=='0') //checks how many leading zeros there are
                i++; 
            StringBuffer sb = new StringBuffer(this.huge);  
            sb.replace(0,i,""); //replaces leading zeros with blanks
            this.huge = sb.toString();
            this.huge = this.huge.trim();//removes blanks spaces
        }
        return this;
    }
    /***************************************************************************
    * Method: Returns a string representing the digits of the huge integer
    * @param: none @return: type String
    ***************************************************************************/
    public String toString(){
        if(this.huge.length()>1){this.removeZeros();}
        if(neg==1){ //if there is a negative, returns the string with a negative sign in front of it
            String sign = "-";
            return (sign+this.huge);
        }
        else{
            String sign = ""; //returns the string representation
            return (sign+this.huge);
        }          
    }
}