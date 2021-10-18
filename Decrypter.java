import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
/**
 * Write a description of class Writer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Decrypter 
{
    private Character change_name(String name){
        //take the parameter name and saves each of it's characters in a char arrayList
       ArrayList<Character> charName = new ArrayList<Character>();
       for (int index=0;index<7;index++){
           charName.add(name.charAt(index));
       }
       //continually removes the first character from the list until it reaches an uppercase character
       while(charName.size()>0&&!Character.isUpperCase(charName.get(0))){
          charName.remove(0);
       }
       int Binary[] = new int[charName.size()];
       //converts each character in the array to binary
       //uppercase = 1
       //lowercase = 0
       for (int charIndex=0;charIndex<charName.size();charIndex++){
           if(Character.isUpperCase(charName.get(charIndex))){
               Binary[charIndex]=1;
           }
           else{
               Binary[charIndex]=0;
           }
       }
       //adds each number in the binary array to a string
       String binary = "";
       //System.out.println(Arrays.toString(Binary));
       for(int n=0;n<charName.size();n++){
           //System.out.println(Binary[n]);
           binary+=Binary[n];
       }
       //converts the binary to a decimal value, adds 5, converts it to character, and returns it
       int decimalValue=0;
       if(binary!=""){
           decimalValue=Integer.parseInt(String.valueOf(binary),2)+5;
       }
       else{
           decimalValue=0;
       }
       if(decimalValue<127){
           return (char) decimalValue;
       }
       else{
           return ' ';
       }
    }
    protected String switch_message(String msg){
       //splits the message into substrings with a length of 7
       ArrayList<String> nameList = new ArrayList<String>();
       for (int i=0; i<(int)(msg.length()/7);i++){
           nameList.add(msg.substring(0+7*i,(1+i)*7));
        }
       //change each substring using the change_name function, adds the returned characters togther,
       //and returns a string of the new msg
       String new_msg = "";
       for (String name : nameList){
           new_msg+=change_name(name);
       }
       return new_msg;
   }
}
