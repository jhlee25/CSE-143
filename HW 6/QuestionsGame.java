// Junhyoung Lee
// 8/16/20
// CSE 143
// TA: Sophia Schmid
// Assignment #6 (Using 3 Late Day)
//
// This program creates a guessing game called 20 Questions to represent
// the computer's tree of yes/no questions and answers for playing games 
// of 20 Questions. In this program, the human begins a round by choosing
// some object, and the computer attempts to guess that object by asking 
// a series of yes/no questions until it thinks it knows the answer.

import java.util.*;
import java.io.*;

public class QuestionsGame {
   private QuestionNode overallRoot;
   private Scanner console;
   
   // Constructs a new QuestionsGame with a single leaf node representing
   // the object "computer"
   // Also constructs console to read user input
   public QuestionsGame() {
      overallRoot = new QuestionNode("computer");
      console = new Scanner(System.in);
   }
   
   // Reads passed input file and replace the current tree with
   // a new tree using the information in the file
   // Assume the file is legal and in standard format
   public void read(Scanner input) {
      overallRoot = readHelper(input);
   }
   
   // Returns a single node created by reading passed input file
   // and replace the current tree with a new tree using 
   // the information in the file
   // Assume the file is legal and in standard format
   private QuestionNode readHelper (Scanner input) {
      String type = input.nextLine();
      String data = input.nextLine();
      QuestionNode root = new QuestionNode(data);
      if (type.equals("Q:")) {
         root.yes = readHelper(input);
         root.no = readHelper(input);
      }
      return root;
   }
   
   // Stores the current questions tree to passed output file
   // This can be used to later play another game with the computer using
   // questions from this one
   public void write(PrintStream output) {
      write(output, overallRoot);
   }
   
   // Stores the current questions tree to passed output file
   // This can be used to later play another game with the computer using
   // questions from this one
   private void write(PrintStream output, QuestionNode root) {
      if (root != null) {
         String type = "";
         if (root.yes != null || root.no != null) {
            type = "Q:";
         } else {
            type = "A:";
         }
         output.println(type);
         output.println(root.data);
         write(output, root.yes);
         write(output, root.no);
      }
   }
   
   // Uses the current question tree to play one complete guessing game with
   // the user, asking yes/no questions until reaching an answer object to guess
   public void askQuestions() {
      askQuestions(overallRoot);
   }
   
   // Uses the current question tree to play one complete guessing game with
   // the user, asking yes/no questions until reaching an answer object to guess
   private void askQuestions(QuestionNode root) {
      if (root.yes == null || root.no == null) { // leaf node
         String promt = "Would your object happen to be " + root.data + "?";
         if (yesTo(promt)) {
            System.out.println("Great, I got it right!");
         } else {
            System.out.print("What is the name of your object? ");
            String object = console.nextLine().trim().toLowerCase();
            System.out.println("Please give me a yes/no question that");
            System.out.println("distinguishes between your object");
            System.out.print("and mine--> ");
            String question = console.nextLine().trim();
            String answer = "And what is the answer for your object?";
            if (yesTo(answer)) {
               root.no = new QuestionNode(root.data);
               root.data = question;
               root.yes = new QuestionNode(object);
            } else {
               root.yes = new QuestionNode(root.data);
               root.data = question;
               root.no = new QuestionNode(object);
            }
         }
      } else { // branch node
         String question = root.data;
         if (yesTo(question)) {
            askQuestions(root.yes);
         } else {
            askQuestions(root.no);
         }
      }
   }
   
   // post: asks the user a question, forcing an answer of "y" or "n";
   //       returns true if the answer was yes, returns false otherwise
   public boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }
   
   // Class for storing a single node of QuestionsGame
   private static class QuestionNode {
      public String data;
      public QuestionNode yes;
      public QuestionNode no;
      
      // Constructs a leaf node with given data
      public QuestionNode(String data) {
         this(data, null, null);
      }
      
      // Constructs a branch node with given data, yes subtree, no subtree
      public QuestionNode(String data, QuestionNode yes, QuestionNode no) {
         this.data = data;
         this.yes = yes;
         this.no = no;
      }
   }
}
