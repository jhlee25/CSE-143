// Junhyoung Lee
// 7/11/20
// CSE 143
// TA: Sophia Schmid
// Assignment #2
//
// This program implements the interface called Guitar. It implements playNote,
// hasString, pluck, sample, tic, and time method used in the interface.

public class Guitar37 implements Guitar {
   // stores the GuitarString objects in the array
   private GuitarString[] stringArray;
   
   // represents the index of stringArray
   private int indexOfArray;
   
   // represents the time at current position
   private int currentTime;
   
   public static final String KEYBOARD =
   "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";  // keyboard layout
   
   // Constructs a new array of guitar strings with distinct 37 strings
   // Each guitar string in the array has their own frequency
   public Guitar37() {
      stringArray = new GuitarString[KEYBOARD.length()];
      double[] freq = new double[KEYBOARD.length()];
      for (int i = 0; i < KEYBOARD.length(); i++) {
         freq[i] = (int) (440 * Math.pow(2, ((i - 24) / 12.0)));
         stringArray[i] = new GuitarString(freq[i]);
      }
   }
   
   // Plays the note with passed exact pitch as an interger value
   // The passed pitch will be converted to corresponding key character
   // Not every value of pitch can be played and if it can't play, it is ignored
   public void playNote(int pitch) {
      if (pitch > -25 && pitch < 13) {
         stringArray[(pitch + 24)].pluck();
      }
   }
   
   // Returns true or false based on whether the particular passed key
   // has a corresponding string for this guitar
   public boolean hasString(char key) {
      for (int i = 0; i < KEYBOARD.length(); i++) {
         if (key == KEYBOARD.charAt(i)) {
            return true;
         }
      }
      return false;
   }
   
   // Plays the note with passed key character
   // It has a precondition that the key is legal for this guitar by hasString method
   // @throws - IllegalArgumentException if the key is not one of the 37 keys
   // it is designed to play
   public void pluck(char key) {
      if (hasString(key) == false) {
         throw new IllegalArgumentException();
      }
      for (int i = 0; i < KEYBOARD.length(); i++) {
         if (key == KEYBOARD.charAt(i)) {
            indexOfArray = i;
         }
      }
      stringArray[indexOfArray].pluck();
   }
   
   // Returns the current sample, the sum of all samples from
   // the strings of this guitar
   public double sample() {
      double sum = 0.0;
      for (int i = 0; i < KEYBOARD.length(); i++) {
         sum += stringArray[i].sample();
      }
      return sum;
   }
   
   // Advances the time forward one by calling tic method
   // Current time increments as this method called
   public void tic() {
      for (int i = 0; i < KEYBOARD.length(); i++) {
         stringArray[i].tic();
      }
      currentTime++;
   }
   
   // Returns what time is at current position
   public int time() {
      return currentTime;
   }
}
