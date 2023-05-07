// Junhyoung Lee
// 7/31/20
// CSE 143
// TA: Sophia Schmid
// Assignment #4 (Using 1 Late Day)
//
// This program reads an input file with a grammar in Backus-Naur Form (BNF)
// and will allow the user to randomly generate elements of the grammar.

import java.util.*;

public class GrammarSolver {
   private SortedMap<String, String[]> allGrammars;
   
   // Constucts a storage of passed grammar separating them by "::=" sign
   // @throw - IllegalArgumentException if the grammar is empty or
   // if there are two or more entries in the grammar for the same nonterminal
   public GrammarSolver(List<String> grammar) {
      if (grammar.size() == 0) {
         throw new IllegalArgumentException();
      }
      allGrammars = new TreeMap<>();
      for (int i = 0; i < grammar.size(); i++) {
         String[] line = grammar.get(i).split("::=");
         String[] rules = line[1].split("[|]+");
         for (int j = 0; j < rules.length; j++) {
            rules[j] = rules[j].trim();
         }
         allGrammars.put(line[0], rules);
      }
      if (grammar.size() > allGrammars.keySet().size()) {
         throw new IllegalArgumentException();
      }
   }
   
   // Returns true if the given symbol is a nonterminal of the grammar;
   // returns false otherwise
   public boolean grammarContains(String symbol) {
      return allGrammars.containsKey(symbol);
   }
   
   // Returns result that is generated randomly with the given number
   // of occurrences of the given symbol using the grammar
   // For any given nonterminal symbol, each of its rules should be
   // applied with equal probability
   // @throw - IllegalArgumentException if the grammar does not contain
   // the given nonterminal symbol or if the number of times is less than 0
   public String[] generate(String symbol, int times) {
      if (!allGrammars.containsKey(symbol) || times < 0) {
         throw new IllegalArgumentException();
      }
      String[] result = new String[times];
      for (int i = 0; i < result.length; i++) {
         result[i] = generate(symbol);
      }
      return result;
   }
   
   // Returns a sentence that is generated randomly with the given symbol
   // For any given nonterminal symbol, each of its rules should be
   // applied with equal probability
   private String generate(String symbol) {
      Random r = new Random();
      int rIndex = r.nextInt(allGrammars.get(symbol).length);
      String[] rules = allGrammars.get(symbol);
      String randomeRule = rules[rIndex];
      String result = "";
      String[] rule = randomeRule.split("[ \t]+");
      for (String each : rule) {
         if (!allGrammars.containsKey(each)) {
            result = result + each + " ";
         } else {
            result = result + generate(each);
         }
      }
      return result;
   }
   
   // Returns a string representation of the various nonterminal symbols
   // from the grammar as a sorted, comma-separated list enclosed
   // in square brackets, as in ¡°[<np>, <s>, <vp>]¡±
   public String getSymbols() {
      return allGrammars.keySet().toString();
   }
}