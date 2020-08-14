//author: Lorenzo Bracci

import java.io.File;
import java.util.*;
import java.io.FileWriter;

public class test{
/*
delete the first character from the string, e.g., tring;
delete the last character from the string, e.g., strin;
reverse the string, e.g., gnirts;
duplicate the string, e.g., stringstring;
reflect the string, e.g., stringgnirts or gnirtsstring;
uppercase the string, e.g., STRING;
lowercase the string, e.g., string;
capitalize the string, e.g., String;
ncapitalize the string, e.g., sTRING;
toggle case of the string, e.g., StRiNg or sTrInG
*/
static String[] mostCommonPasswords = { "123456",  "qwerty", "letmein", "football", "iloveyou", "admin", "abc123", "login", "passw0rd"};

static char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
 public static String[] removeDuplicates(String arr[]){
   String[] temp = new String[arr.length];
   for(int i = 0; i < arr.length; i++){
     if(arr[i].length() > 8){
       temp[i] = arr[i].substring(0,8);
     }else{
        temp[i] = arr[i];
     }

   }
Set<String> set = new HashSet<String>();
for(int i = 0; i < arr.length; i++){
set.add(temp[i]);
}
return set.toArray(new String[0]);
     }
private static String toggle1(String word){
String result = "";
int size = word.length();
String[] initialResult = new String[size];
  for(int i = 0; i < size; i+=2){
  initialResult[i] = word.substring(i, i + 1).toUpperCase();
  }
  for(int j = 1; j < size; j+=2){
  initialResult[j] = word.substring(j, j + 1).toLowerCase();
}
for(int i = 0; i <  size; i++)
result = result + initialResult[i];
return result;
}

private static String toggle2(String word){
String result = "";
int size = word.length();
String[] initialResult = new String[size];
  for(int i = 0; i < size; i+=2){
  initialResult[i] = word.substring(i, i + 1).toLowerCase();
  }
  for(int j = 1; j < size; j+=2){
  initialResult[j] = word.substring(j, j + 1).toUpperCase();
}
for(int i = 0; i <  size; i++)
result = result + initialResult[i];
return result;
}

 private static String reflect1(String word){
   return word + reverse(word);
 }
 private static String reflect2(String word){
   return  reverse(word) + word;
 }
 private static String duplicate(String word){
   return word + word;
 }
 private static String capitalize(String word){
   String first = word.substring(0,1);
       String rest = word.substring(1);
       return first.toUpperCase() + rest;
 }
 private static String nCapitalize(String word){
   String first = word.substring(0,1);
       String rest = word.substring(1);
       return first + rest.toUpperCase();
 }
private static String reverse(String word){
  byte[] byteArray = word.getBytes();
 byte[] result = new byte[byteArray.length];
                   for (int i = 0; i<byteArray.length; i++)
           result[i] = byteArray[byteArray.length-i-1];
             return new String(result);
}

  private static String[]  mangle(String dictEntry, int manglingRounds){//this is where the mangling code should be inserted
String[] mangles = {dictEntry};
    for(int i = 0;i < manglingRounds; i++){
    Stack<String> mangledWords = new Stack<String>();//create data sctructure of variable length//set it back to zero so that we do not return the manglings of the previous rounds
for(int j = 0; j < mangles.length; j++){
  if((mangles[j].length() > 1) && (mangles[j].length() < 9)){//make sure that length is  greater than 1, no point in mangling strings with 1 or less characters and smaller than 9 bceuase only 8 chars count
    mangledWords.push(mangles[j].substring(1));//remove first char
    mangledWords.push(mangles[j].substring(0, (mangles[j].length() - 1)));//remove second char
    mangledWords.push(reverse(mangles[j]));//revserse string
    mangledWords.push(duplicate(mangles[j]));//duplicate string
mangledWords.push(reflect1(mangles[j]));//reflect string
mangledWords.push(reflect2(mangles[j]));//reflect string
mangledWords.push(mangles[j].toUpperCase());//uppercase string
mangledWords.push(mangles[j].toLowerCase());//lowercase string
mangledWords.push(capitalize(mangles[j]));//capitalize string
mangledWords.push(nCapitalize(mangles[j]));//ncapitalize string
mangledWords.push(toggle1(mangles[j]));//toggle string case
mangledWords.push(toggle2(mangles[j]));//toggle string case
}
}
if(i == 0){//if we are in the first mangling round we add all the chars at beginning and end
  for(int j = 0; j < chars.length; j++){
      mangledWords.push((chars[j] + dictEntry));
      mangledWords.push((dictEntry + chars[j]));
    }
}

mangles = removeDuplicates(mangledWords.toArray(new String[0]));//create array of new strings to mangles and remove duplicates
}
    //optimization would be to eliminate all duplicate entries in the array
    return mangles;
  }

  private static String[] createDictArray(String fileName, String[] users) throws Exception {//create an array with all the dictionary entries
    File passwordFile = null;
    Scanner scan = null;
    try{
    passwordFile = new File(fileName);
    scan = new Scanner(passwordFile);
  }catch(Exception ex){
    System.out.println("The input dictionary file is incorret");
    System.exit(1);
  }
    Stack<String> dict = new Stack<String>();//create data sctructure of variable length

for(int i = 0; i < users.length; i ++)//adding usernames to the dictionary
dict.push(users[i]);

    for(int i = 0; i < mostCommonPasswords.length; i++)//add most common passwords to the dictionary
    dict.push(mostCommonPasswords[i]);

    while (scan.hasNextLine()) {
  				String dictEntry = scan.nextLine();
        dict.push(dictEntry);
        }
  			scan.close();
        return dict.toArray(new String[0]);//return array with all passwords hash
  }

  private static String[] getUserNames(String fileName) throws Exception {//create an array with all the userNames
    File passwordFile = null;
    Scanner scan = null;
    try{
    passwordFile = new File(fileName);
    scan = new Scanner(passwordFile);
  }catch(Exception ex){
    System.out.println("The input password file is incorret");
    System.exit(1);
  }
    Stack<String> users = new Stack<String>();//create data sctructure of variable length
    while (scan.hasNextLine()) {
  				String userLine = scan.nextLine();
          String[] userInformation = userLine.split(":");//parse the line with information about the user to retrive the password s hash
          users.push(userInformation[0]);//it s important to notice that the hash is going to be in position 1 in the array
  			}
  			scan.close();
        return users.toArray(new String[0]);//return array with all passwords hash
  }

private static String[] createHashArray(String fileName) throws Exception {//create an array with all the hashes
  File passwordFile = null;
  Scanner scan = null;
  try{
  passwordFile = new File(fileName);
  scan = new Scanner(passwordFile);
}catch(Exception ex){
  System.out.println("The input password file is incorret");
  System.exit(1);
}
  Stack<String> hashes = new Stack<String>();//create data sctructure of variable length
  while (scan.hasNextLine()) {
				String userLine = scan.nextLine();
        String[] userInformation = userLine.split(":");//parse the line with information about the user to retrive the password s hash
        hashes.push(userInformation[1]);//it s important to notice that the hash is going to be in position 1 in the array
			}
			scan.close();
      return hashes.toArray(new String[0]);//return array with all passwords hash
}

static void printAllKLength(char[] set, int k)
{
    int n = set.length;
    printAllKLengthRec(set, "", n, k);
}

// The main recursive method
// to print all possible
// strings of length k
static void printAllKLengthRec(char[] set, String prefix, int n, int k)
{

    if (k == 0)
    {
      if(jcrypt.crypt("/G", prefix).equals("/GAlrd0iClYvY")){//compute hash given dictionary entry and salt and check if it matches the hash in the password file
        System.out.println("Cracked password: " + prefix);
      }
        return;
    }
    for (int i = 0; i < n; ++i)
    {

        String newPrefix = prefix + set[i];

        printAllKLengthRec(set, newPrefix,
                                n, k - 1);
    }
}

public static void main(String[] args) throws Exception {// this program should take 2 arguments<dictionary>(dictionary file dict.txt) <passwd>(the UNIX file containing the passwords to crack)

String toCrack = "/GAlrd0iClYvY";
for(int i = 1; i < 9; i++){
printAllKLength(chars, i);
System.out.println("All passwords of length " + i + " have been tried");
}


}






}
