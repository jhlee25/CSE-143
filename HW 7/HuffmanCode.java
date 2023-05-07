// Junhyoung Lee
// 8/21/20
// CSE 143
// TA: Sophia Schmid
// Assignment #7
//
// This program compress and decompress given data using Huffman Code

import java.util.*;
import java.io.*;

public class HuffmanCode {
   private Queue<HuffmanNode> list;
   private HuffmanNode decompTree;
   private HuffmanNode current;
   
   // Constructs initialization of a new Huffman Code using the algorithm 
   // described for making a code from the given frequencies
   public HuffmanCode(int[] frequencies) {
      list = new PriorityQueue<>();
      for (int i = 0; i < frequencies.length; i++) {
         if (frequencies[i] != 0) {
            HuffmanNode value = new HuffmanNode(i, frequencies[i]);
            list.add(value);
         }
      }
      while (list.size() > 1) {
         HuffmanNode first = list.remove();
         HuffmanNode second = list.remove();
         HuffmanNode sum = new HuffmanNode(0, first.freq + second.freq, first, second);
         list.add(sum);
      }
   }
   
   // Constructs initialization of a new Huffman Code by reading 
   // in a previously constructed code from a .code file
   // You may assume the given Scanner is not null and is always contains 
   // data encoded in legal, valid standard format
   public HuffmanCode(Scanner input) {
      decompTree = new HuffmanNode(0, 0);
      while (input.hasNext()) {
         int ascii = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         current = decompTree;
         if (code.length() > 1) {
            for (int i = 0; i < code.length() - 1; i++) {
               String digit = String.valueOf(code.charAt(i));   
               current = huffmanCodeHelper(digit, current);
            }
         }
         code = String.valueOf(code.charAt(code.length() - 1));
         if (code.equals("0")) {
            current.left = new HuffmanNode(ascii, 0);
         } else {
            current.right = new HuffmanNode(ascii, 0);
         }
      }
   }
   
   // Constructs initialization of a new Huffman Code by reading 
   // in a previously constructed code from a .code file using given 
   // each digit of the Huffman Code
   // Returns the HuffmanNode that represents the current node
   // You may assume the given Scanner is not null and is always contains 
   // data encoded in legal, valid standard format
   private HuffmanNode huffmanCodeHelper(String digit, HuffmanNode root) {
      if (digit.equals("0")) {
         if (root.left == null) {
            root.left = new HuffmanNode(0, 0);
            return root.left;
         } else {
            return root.left;
         }
      } else {
         if (root.right == null) {
            root.right = new HuffmanNode(0, 0);
            return root.right;
         } else {
            return root.right;
         }
      }
   }
   
   // Stores the current huffman codes to the given output stream 
   // in the standard format
   public void save(PrintStream output) {
      HuffmanNode tree = list.peek();
      save(output, tree, "");
   }
   
   // Stores the current huffman codes to the given output stream 
   // in the standard format using given tree which is created from constructor
   // and the given type of Huffman Code which is binary number
   private void save(PrintStream output, HuffmanNode tree, String type) {
      if (tree != null) {
         if (tree.left != null || tree.right != null) {
            save(output, tree.left, type + "0"); 
            save(output, tree.right, type + "1"); 
         }
         if (tree.asciiValue != 0) {
            output.println(tree.asciiValue);
            output.println(type);
         }
      }
   }
   
   // Reads individual bits from the given input stream and write 
   // the corresponding characters to the given output
   // It should stop reading when the BitInputStream is empty
   // You may assume that the input stream contains a legal encoding
   // of characters for this huffman code of tree
   public void translate(BitInputStream input, PrintStream output) {
      while (input.hasNextBit()) {
         translate(input, output, decompTree);
      }
   }
   
   // Reads individual bits from the given input stream and write 
   // the corresponding characters to the given output 
   // using given tree which is created from constructor
   // It should stop reading when the BitInputStream is empty
   // You may assume that the input stream contains a legal encoding
   // of characters for this huffman code of tree
   private void translate(BitInputStream input, PrintStream output, HuffmanNode root) {
      if (root.asciiValue == 0) {
         int each = input.nextBit();
         if (each == 0) {
            translate(input, output, root.left);
         } else {
            translate(input, output, root.right);
         }
      } else {
         output.print(Character.toString((char)root.asciiValue));
      }
   }
   
   // Class for storing a single node of HuffmanCode
   // It implements Comparable<E>
   private static class HuffmanNode implements Comparable<HuffmanNode> {
      public int asciiValue;
      public int freq;
      public HuffmanNode left;
      public HuffmanNode right;
      
      // Constructs a leaf node with given Ascii value and frequency
      public HuffmanNode(int asciiValue, int freq) {
         this(asciiValue, freq, null, null);
      }
      
      // Constructs a branch node with given Ascii value, frequency,
      // left subtree and right subtree
      public HuffmanNode(int asciiValue, int freq, HuffmanNode left, HuffmanNode right) {
         this.asciiValue = asciiValue;
         this.freq = freq;
         this.left = left;
         this.right = right;
      }
      
      // Returns the integer value that is result of comparing frequencies of two nodes
      public int compareTo(HuffmanNode other) {
         return Integer.compare(this.freq, other.freq);
      }
   }
}