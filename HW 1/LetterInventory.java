// Junhyoung Lee
// 7/2/20
// CSE 143
// TA: Sophia Schmid
// Assignment #1
//
// This program creates an array that can store the counts of each alphabet letter.
// The counts represent how many times each letter appeared in the given data
// but not including non alphabetic character (how many a's, how many b's, etc).

public class LetterInventory {
   // store count of how many times each letter used
   private int[] inventory;
   
   // length of given string data not including non alphabetic character
   private int size;
   
   public static final int COUNTERS = 26;
   
   // Constructs an array called inventory with size of class constant 
   // Using the given string data, value in the each index will be changed 
   // by count as well as size
   public LetterInventory(String data) {
      inventory = new int[COUNTERS];
      data = data.toLowerCase();
      for (int i = 0; i < data.length(); i++) {
         char ch = data.charAt(i);
         for (int j = 0; j < COUNTERS; j++) {
            if (ch == (char) ('a' + j)) {
               inventory[j]++;
               size++;
            }
         }
      }
   }
   
   // Returns number of all the distinct letters in the data
   // (sum of all the counts in the inventory array)
   public int size() {
      return size;
   }
   
   // Returns true if the string data is empty and false otherwise
   // (true if all counts in the inventory are zero)
   public boolean isEmpty() {
      return (size == 0);
   }
   
   // Returns the count of how many times passed letter appeared in the data
   // (how many of this letter are in the inventory)
   // @throws - IllegalArgumentException if non alphabetic character is passed
   public int get(char letter) {
      letter = Character.toLowerCase(letter);
      return checkLetter(letter);
   }
   
   // Returns string representation of the inventory with the letters
   // all in lowercase and in sorted order and surrounded by square brackets
   // eg. "[aaaablm]"
   public String toString() {
      if (size == 0) {
         return "[]";
      } else {
         String result = "[";
         for (int i = 0; i < COUNTERS; i++) {
            for (int j = 0; j < inventory[i]; j++) {
               result += (char) ('a' + i);
            }
         }
         result += "]";
         return result;
      }
   }
   
   // Sets the count of passed letter to the passed value
   // @throws - IllegalArgumentException if non alphabetic character is passed
   // @throws - IllegalArgumentException if passed value is negative
   public void set(char letter, int value) {
      letter = Character.toLowerCase(letter);
      checkLetter(letter);
      if (value < 0) {
         throw new IllegalArgumentException();
      }
      size += value;
      inventory[(int) (letter - 97)] = value;
      size = toString().length() - 2;
   }
   
   // Returns count of passed letter in the inventory if it is alphabetic character
   // @throws - IllegalArgumentException if non alphabetic character is passed
   public int checkLetter(char letter) {
      for (int i = 0; i < COUNTERS; i++) {
         if (letter == (char) ('a' + i)) {
            return inventory[i];
         }
      }
      throw new IllegalArgumentException();
   }
   
   // Constructs and returns a new LetterInventory object that represents sum of
   // this object's letter count and other object's letter count which is passed
   // The two LetterInventory objects should not be changed by this method
   public LetterInventory add(LetterInventory other) {
      LetterInventory sum = new LetterInventory("");
      for (int i = 0; i < COUNTERS; i++) {
         char ch = (char) ('a' + i);
         sum.set(ch, (this.get(ch) + other.get(ch)));
      }
      return sum;
   }
   
   // Constructs and returns a new LetterInventory object that represents result of
   // subtracting the passed other object from this object (i.e., subtracting the count
   // of each letter from this object's count to other object's count)
   // If any resulting count would be negative, it returns null
   // The two LetterInventory objects should not be changed by this method
   public LetterInventory subtract(LetterInventory other) {
      LetterInventory result = new LetterInventory("");
      for (int i = 0; i < COUNTERS; i++) {
         char ch = (char) ('a' + i);
         if ((this.get(ch) - other.get(ch)) < 0) {
            return null;
         } else {
            result.set(ch, (this.get(ch) - other.get(ch)));
         }
      }
      return result;
   }
}