import java.util.*;

public class AssassinTest {
   public static void main(String[] args) {
      List<String> names = new ArrayList<>();
      names.add("Athos");
      names.add("Porthos");
      names.add("Aramis");
//       names.add("Carol");
//       names.add("Chris");
      
      AssassinManager test = new AssassinManager(names);
      test.printKillRing();
      test.printGraveyard();
      
      if (test.killRingContains("Aramis")) {
         System.out.println("yes");
      } else {
         System.out.println("yes");
      }
      test.kill("porthos");
      
      test.printKillRing();
      test.printGraveyard();
   }
}