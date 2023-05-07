// Junhyoung Lee
// 7/20/20
// CSE 143
// TA: Sophia Schmid
// Assignment #3 (Using 1 Late Day)
//
// This program manages a game of assassin. In the game each person
// has a target to assassinate. Initially each person knows only who 
// they are assassinating. So this program will keep track of who is stalking whom 
// and the history of who killed whom.

import java.util.*;

public class AssassinManager {
   private AssassinNode frontKillRing;
   private AssassinNode frontGraveyard;
   
   // Constructs a kill ring with the passed list of names in same order
   // Also graveyard is created as null at the beginning
   // @throw - IllegalArgumentException if the passed list is empty
   public AssassinManager(List<String> names) {
      if (names.size() == 0) {
         throw new IllegalArgumentException();
      }
      frontKillRing = new AssassinNode(names.get(0));;
      frontGraveyard = new AssassinNode(null);
      for (int i = 1; i < names.size(); i++) {
         AssassinNode current = frontKillRing;
         while (current.next != null) {
            current = current.next;
         }
         current.next = new AssassinNode(names.get(i));
      }
   }
   
   // Prints the names of the people in the kill ring
   // If there is only one person in the ring, it should report 
   // that the person is stalking themselves
   public void printKillRing() {
      if (frontKillRing.next == null) {
         System.out.println("    " + frontKillRing.name +
         " is stalking " + frontKillRing.name);
      } else {
         AssassinNode current = frontKillRing;
         while (current.next != null) {
            System.out.println("    " + current.name +
            " is stalking " + current.next.name);
            current = current.next;
         }
         System.out.println("    " + current.name +
         " is stalking " + frontKillRing.name);
      }
   }
   
   // Prints the names of the people in the graveyard
   // It should print the names in reverse kill order
   // It should produce no output if the graveyard is empty
   public void printGraveyard() {
      if (frontGraveyard.name != null) {
         AssassinNode current = frontGraveyard;
         while (current.next != null) {
            System.out.println("    " + current.name +
            " was killed by " + current.killer);
            current = current.next;
         }
         if (current.name != null) {
            System.out.println("    " + current.name +
            " was killed by " + current.killer);
         }
      }
   }
   
   // Returns true if the given name is in the current kill ring 
   // and should return false otherwise
   // It should ignore case in comparing names
   public boolean killRingContains(String name) {
      AssassinNode current = frontKillRing;
      return check(name, current);
   }
   
   // Returns true if the given name is in the current graveyard 
   // and should return false otherwise
   // It should ignore case in comparing names
   public boolean graveyardContains(String name) {
      AssassinNode current = frontGraveyard;
      return check(name, current);
   }
   
   // Returns true if the given name is in the given nodes (current)
   // and should return false if given nodes is empty or no name is matched
   // It should ignore case in comparing names
   private boolean check(String name, AssassinNode current) {
      if (current.name == null) {
         return false;
      } else {
         while (current.next != null) {
            if (current.name.equalsIgnoreCase(name)) {
               return true;
            } else {
               current = current.next;
            }
         }
         if (current.name != null) {
            return (current.name.equalsIgnoreCase(name));
         } else {
            return false;
         }
      }
   }
   
   // Returns true if the game is over 
   // (i.e., if the kill ring has just one person in it) 
   // and should return false otherwise
   public boolean gameOver() {
      return (frontKillRing.next == null);
   }
   
   // Returns the name of the winner of the game
   // It should return null if the game is not over.
   public String winner() {
      if (gameOver()) {
         return frontKillRing.name;
      } else {
         return null;
      }
   }
   
   // Records the killing of the person with the given name, 
   // transferring the person from the kill ring to the graveyard
   // It should ignore case in comparing names
   // @throw - IllegalArgumentException if the given name is not part 
   // of the current kill ring
   // @throw - IllegalStateException if the game is over
   public void kill(String name) {
      if (!killRingContains(name)) {
         throw new IllegalArgumentException();
      }
      if (gameOver()) {
         throw new IllegalStateException();
      }
      AssassinNode current = frontKillRing;
      // If passed name is same as the first node of kill ring
      if (frontKillRing.name.equalsIgnoreCase(name)) {
         frontKillRing = frontKillRing.next;
         current.next = frontGraveyard;
         frontGraveyard = current;
         current = frontKillRing;
         // If kill ring has only two person initially
         // (one person left after the first kill)
         if (current.next == null) {
            frontGraveyard.killer = current.name;
         } else {
            while (current.next.next != null) {
               current = current.next;
            }
            frontGraveyard.killer = current.next.name;
         }
      }
      // If the passed name is same as any node in the middle 
      // (not first, not last) of the kill ring
      if (current.next != null) {
         while (current.next.next != null) {
            if (current.next.name.equalsIgnoreCase(name)) {
               // If graveyard is empty
               if (frontGraveyard.name == null) {
                  frontGraveyard.next = current.next;
                  current.next = current.next.next;
                  AssassinNode temp = frontGraveyard.next;
                  temp.next = frontGraveyard;
                  frontGraveyard = temp;
                  temp.next.next = null;
               } else { // If graveyard is not empty
                  AssassinNode temp = current.next;
                  current.next = current.next.next;
                  temp.next = frontGraveyard;
                  frontGraveyard = temp;
               }
               frontGraveyard.killer = current.name;
            } else {
               current = current.next;
            }
         }
      }
      // If the passed name is same as the last node of kill ring
      // only if kill ring has more than 2 person in it
      if (current.next != null && 
         current.next.name.equalsIgnoreCase(name)) {
         AssassinNode prev = frontKillRing;
         while (prev.next.next != null) {
            prev = prev.next;
         }
         current.next.next = frontGraveyard;
         frontGraveyard = current.next;
         current.next = null;
         frontGraveyard.killer = prev.name;
      }
   }
}
