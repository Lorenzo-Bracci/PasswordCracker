//author: Lorenzo Bracci

import java.io.File;
import java.util.*;
import java.io.FileWriter;
import java.lang.*;

public class PasswordCrackTest{
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
  if(mangles[j].length() > 1){//make sure that length is  greater than 1, no point in mangling strings with 1 or less characters
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

//mangles = removeDuplicates(mangledWords.toArray(new String[0]));//create array of new strings to mangles and remove duplicates
mangles = mangledWords.toArray(new String[0]);
}
    //optimization would be to eliminate all duplicate entries in the array
    return mangles;
  }

  private static String[] createDictArray(String fileName) throws Exception {//create an array with all the dictionary entries
    File passwordFile = null;
    Scanner scan = null;
    try{
    passwordFile = new File(fileName);
    scan = new Scanner(passwordFile);
  }catch(Exception ex){
    System.out.println("The input password file is incorret");
    System.exit(1);
  }
    Stack<String> dict = new Stack<String>();//create data sctructure of variable length

    for(int i = 0; i < mostCommonPasswords.length; i++)//add most common passwords to the dictionary
    dict.push(mostCommonPasswords[i]);

    while (scan.hasNextLine()) {
  				String dictEntry = scan.nextLine();
        dict.push(dictEntry);
        }
  			scan.close();
        return dict.toArray(new String[0]);//return array with all passwords hash
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

/*public static void main(String[] args) throws Exception {// this program should take 2 arguments<dictionary>(dictionary file dict.txt) <passwd>(the UNIX file containing the passwords to crack)
String[] dict = createDictArray(args[0]);//store all dictionary entries in an array
String[] hashes = createHashArray(args[1]);//store all hashes and salts in an array
String[] salt = new String[hashes.length];//here we store every salt
boolean[] done = new boolean[hashes.length];//this array is used to check if a certain hash has been found
for(int i = 0; i < hashes.length; i++){
salt[i] = hashes[i].substring(0, 2);//extract the salts out of the strings
  done[i] = false;
}
long start = System.currentTimeMillis();
LinearProbingHashST<String> computedHashes = new LinearProbingHashST<String>(100000);
boolean notUsed = false;
for(int i = 0; i < dict.length; i++){//in this for loop I simply test if I can crack any password withoout mangling
  if(dict[i].length() > 8){//code used to make sure that we don t try the same password twice
    if(!(computedHashes.contains(dict[i].substring(0, 8)) ) ) {
      computedHashes.put(dict[i].substring(0, 8));
  notUsed = true;
    }
  }else{
    if(!(computedHashes.contains(dict[i]))) {
      computedHashes.put(dict[i]);
    notUsed = true;
    }
  }
  for(int j = 0; j < hashes.length; j++){
if(notUsed){
    if(jcrypt.crypt(salt[j], dict[i]).equals(hashes[j])){//compute hash given dictionary entry and salt and check if it matches the hash in the password file
      done[j] = true;
      System.out.println(dict[i]);
      break;
    }
}
  }
  notUsed = false;
}
int manglingRound = 1;
while(true){
int check = 1000;//only used for testing purposes to check where we are at
  for(int i = 0; i < dict.length; i++){//in this for loop I simply test if I can crack any password withoout mangling
if(i == check){
  System.out.println(i + " words have been mangled and the current word is: " + dict[i]);
check = check + 1000;
}
 //System.out.println("The mangling for word" + dict[i]);
  String[] mangles = mangle(dict[i], manglingRound);//creates all the mangles for one word
  //for(String str: mangles)
  //System.out.println(str);
    for(int j = 0; j < mangles.length; j++){//for every user try all different mangles
      if(mangles[j].length() > 8){
        if(!(computedHashes.contains(mangles[j].substring(0, 8)))) {
          computedHashes.put(mangles[j].substring(0, 8));
        notUsed = true;
        }
      }else{
        if(!(computedHashes.contains(mangles[j]))) {
          computedHashes.put(mangles[j]);
          notUsed = true;
        }
      }
        for(int k = 0; k < hashes.length; k++){//trying the different mangles
          if((!done[k]) && notUsed){//check that the user is not cracked and that the password hasn t already been tried
          if(jcrypt.crypt(salt[k], mangles[j]).equals(hashes[k])){//compute hash given dictionary entry and salt and check if it matches the hash in the password file
            done[k] = true;
            System.out.println(mangles[j]);
            System.out.println(dict[i]);
            break;
          }
        }
    }
  }
  }
  manglingRound++;
  int passwordCracked = 0;
  for(int i = 0; i < done.length; i++){//count how many passwords have been creacked so far
  if(done[i])
  passwordCracked++;
  }
System.out.println("You successfully cracked " + passwordCracked + " passwords");
long end = System.currentTimeMillis();
long totTime = end - start;
System.out.println("time elapsed: " + totTime);
  if(passwordCracked == done.length)//if we cracked all passwords we break out of the larger while loop
break;
if(manglingRound > 3)//only used for testing purpose, to make the program stop to run after one execution
break;

}
long end = System.currentTimeMillis();
long totTime = end - start;
System.out.println("time elapsed: " + totTime);



}

public static void main(String[] args) throws Exception {
  String[] hashes = createHashArray(args[1]);//store all hashes and salts in an array
  String[] salt = new String[hashes.length];//here we store every salt
  boolean[] done = new boolean[hashes.length];//this array is used to check if a certain hash has been found
  for(int i = 0; i < hashes.length; i++){
  salt[i] = hashes[i].substring(0, 2);//extract the salts out of the strings
    done[i] = true;
  }
  done[6] = false;
  done[8] = false;
int check = 1000000;
  for(int i = 8; i < hashes.length; i++){//in this for loop I simply test if I can crack any password withoout mangling
    for(int j = 0; j < 100000000; j++){
      if(j == check){
      System.out.println("the current number is: " + j);
check = check + 1000000;
}
     if(jcrypt.crypt(salt[i], Integer.toString(j)).equals(hashes[i])){//compute hash given dictionary entry and salt and check if it matches the hash in the password file
       done[i] = true;
       System.out.println("Cracked password: " + j);
       break;
     }
    }
  }
}

*/public static void main(String[] args) throws Exception {
  String[] cracked = {"froes",
"abc123",
"apostolic",
"bouncy",
"12345",
"bethor",
"reipoccopier",
"lisaed",
"fOrWaRdInG",
"mAnYfOlD",
"mimesisy",
"8nattier",
"silaroow",
"eolatac",
"OOLEY",
"rosa",
"YARPS",
"Emily",
"hAUNTINh",
"ymmaSSam"
};
  String[] hashes = createHashArray(args[1]);//store all hashes and salts in an array
  String[] salt = new String[hashes.length];//here we store every salt
  boolean[] done = new boolean[hashes.length];//this array is used to check if a certain hash has been found
  for(int i = 0; i < hashes.length; i++){
  salt[i] = hashes[i].substring(0, 2);//extract the salts out of the strings
    done[i] = false;
  }
int check = 1000000;
  for(int i = 0; i < hashes.length; i++){//in this for loop I simply test if I can crack any password withoout mangling
    for(int j = 0; j < cracked.length; j++){
      if(j == check){
      System.out.println("the current number is: " + j);
check = check + 1000000;
}
     if(jcrypt.crypt(salt[i], cracked[j]).equals(hashes[i])){//compute hash given dictionary entry and salt and check if it matches the hash in the password file
       done[i] = true;
       System.out.println("Cracked password: " + cracked[j]);
       System.out.println("Hash of cracked password: " + hashes[i]);
       System.out.println("Position of cracked password: " + i);
       break;
     }
    }
  }
}

}
