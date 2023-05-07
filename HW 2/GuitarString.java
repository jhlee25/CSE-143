// Junhyoung Lee
// 7/11/20
// CSE 143
// TA: Sophia Schmid
// Assignment #2
//
// This program creates a ring buffer that models a vibrating
// guitar string of a given frequency.
// Using this ring buffer, this objects do the pluck, tic, and sample method

import java.util.*;

public class GuitarString {
   private int capacityN; // ring buffer's maximum capacity
   private Queue<Double> ring; // ring buffer created by LinkedList
   
   public static final double ENERGY_DECAY = 0.996;
   
   // Constructs a guitar string with given frequency
   // In the ring buffer, it initializes each storage as zeros
   // @throw - IllegalArgumentException if the frequency is less than
   // or equal to 0 or if the capacity of the ring buffer would be less than 2
   public GuitarString(double frequency) {
      capacityN = (int) Math.round(StdAudio.SAMPLE_RATE / frequency);
      if (frequency <= 0 || capacityN < 2) {
         throw new IllegalArgumentException();
      }
      ring = new LinkedList<>();
      for (int i = 0; i < capacityN; i++) {
         fillingRing(0.0);
      }
   }
   
   // Constructs a guitar string and initializes the contents
   // of the ring buffer to the values in the given array
   // @throw - IllegalArgumentException if the array has fewer than two elements
   public GuitarString(double[] init) {
      capacityN = init.length;
      if (init.length < 2) {
         throw new IllegalArgumentException();
      }
      ring = new LinkedList<>();
      for (int i = 0; i < capacityN; i++) {
         fillingRing(init[i]);
      }
   }
   
   // Fills the contents of ring buffer with given value
   private void fillingRing(double value) {
      ring.add(value);
   }
   
   // Replaces the all contents in the ring buffer with random values
   // between -0.5 inclusive and +0.5 exclusive
   public void pluck() {
      Random r = new Random();
      for (int i = 0; i < capacityN; i++) {
         double rValue = r.nextDouble() - 0.5;
         ring.remove();
         ring.add(rValue);
      }
   }
   
   // Applys the Karplus-Strong update once
   // It removes the first value and adds modified value of
   // first and second to the back of the ring buffer
   public void tic() {
      double first = ring.remove();
      double second = ring.peek();
      ring.add((first + second) / 2 * ENERGY_DECAY);
   }
   
   // Returns the current sample which is the value
   // at the front of the ring buffer
   public double sample() {
      return ring.peek();
   }
}