// Junhyoung Lee
// 8/9/20
// CSE 143
// TA: Sophia Schmid
// Assignment #5 (Using 3 Late Day)
//
// This program uses a dictionary to find all combinations of words 
// that have the same letters as a given phrase

import java.util.*;

public class AnagramSolver {
   private Map<String, LetterInventory> dict;
   
   // Constructs an anagram solver that will use the given list as its dictionary
   // List is not changed in any way
   // Dictionary is a nonempty collection of nonempty sequences of letters and 
   // that it contains no duplicates
   public AnagramSolver(List<String> list) {
      dict = new HashMap<>();
      for(String each : list) {
         LetterInventory word = new LetterInventory(each);
         dict.put(each, word);
      }
   }
   
   // Finds combinations of words that have the same letters as the given letter
   // Prints all combinations of words from the dictionary that are anagrams of 
   // given letter and that include at most given max words
   // (or unlimited number of words if given max is 0)
   // @throw - IllegalArgumentException if max is less than 0
   public void print(String s, int max) {
      LetterInventory given = new LetterInventory(s);
      Stack<String> result = new Stack<>();
      if (max < 0) {
         throw new IllegalArgumentException();
      } else if (max == 0){
         print(given, -1, result);
      } else {
         print(given, max, result);
      }
   }
   
   // Finds combinations of words that have the same letters as the given letter
   // Prints all combinations of words from the given stored structure that  
   // are anagrams of given letter and that include at most given max words
   // (or unlimited number of words if given max is 0)
   private void print(LetterInventory given, int max, 
                      Stack<String> result) {
      for (String word : dict.keySet()) {
         LetterInventory subtract = given.subtract(dict.get(word));
         if (subtract != null) {
            if (subtract.size() == 0) {  // Success
               result.push(word);
               System.out.println(result);
               result.pop();
            } else { // In-Bounds Case
               result.push(word);
               print(subtract, max, result);
               result.pop();
            }
         }
      }
   }
}