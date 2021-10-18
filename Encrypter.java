import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Scanner;
/**
 * Write a description of class Writer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Encrypter
{  
   private String userName="";
   public Encrypter(String inputName){
       userName = inputName;
       if(userName.length()<7){
           int needToAdd = 7-userName.length();
           userName+=userName.substring(0,needToAdd);
       }
       else if(userName.length()>7){
           userName=userName.substring(0,7);
       }
       userName=userName.toLowerCase();
   }
   private Integer logBase2(int n){
       //logBase a of b = log b / log a, so logBase 2 of n = log n / log 2
       return (int)(Math.log(n)/Math.log(2));
   }
   private String change_letter(char c){
       int ascii = (int)c-5; //gets the numerical value of the char and subtracts 5 from it
       int converted_binary = 0;
       //saves each char of the userName in a char Array
       Character[] name = {userName.charAt(0),userName.charAt(1),userName.charAt(2),userName.charAt(3),userName.charAt(4),userName.charAt(5),userName.charAt(6)};
       //converts the int variable ascii to binary
       converted_binary=Integer.parseInt(Integer.toBinaryString(ascii));
       //changes the binary to a string
       String convertedBinaryString = String.valueOf(converted_binary);
       //save the length of the binary string in an int variable digits
       int digits = convertedBinaryString.length();
       //changes the binary to the user's name and returns it
       //every 1 = uppercase;every 0 = lowercase
       int startingNameIndex = 7-digits;
       for (int newIndex=startingNameIndex;newIndex<7;newIndex++){
           if(Character.valueOf(convertedBinaryString.charAt(newIndex-startingNameIndex)).equals('1')){
                name[newIndex]=Character.toUpperCase(name[newIndex]);
           }
       }
       String newName="";
       for (Character charName: name){
           newName+=charName;
       }
       //System.out.println(newName);
       return newName;
       //return (char) (ascii+5);
   }
   protected String switch_message(String msg){
       //save each character of the parameter in a char arrayList
       ArrayList<Character> charList = new ArrayList<Character>();
       for (int i=0; i<msg.length();i++){
           charList.add(msg.charAt(i));
       }
       //change each letter using the change_letter function, adds them back together,
       //and returns a string with the new msg
       String new_msg = "";
       for (char c : charList){
           new_msg+=change_letter(c);
       }
       return new_msg;
   }
}
/*
 * vandelL=1
 * vandeLl=2
 * vandeLL=3
 * vandEll=4
 * vandElL=5
 * vandELl=6
 * vandELL=7
 * vanDell=8
 * vanDelL=9
 * vanDeLl=10
 * vanDeLL=11
 * vanDEll=12
 * vanDElL=13
 * vanDELl=14
 * vanDELL=15
 * vaNdell=16
 */
