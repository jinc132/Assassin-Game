/**
* Jin Chang
* July 12, 2017
* TA: Miri Hyman
* Assignment 3: AssassinManager.java
*
* An AssassinManager manages the game of Assassins. It maintains
* a history of who is currently alive and whom they are stalking.
* As well as manages who was assassinated and who killed them.
*/
import java.util.*;

public class AssassinManager {
   // Private fields.
   private AssassinNode frtKill;  // Reference to the front of the kill ring.
   private AssassinNode frtGrave; // Reference to the front of the graveyard.
   
   /**
   * pre: The given list of names cannot be null or empty. Otherwise, 
   *      an IllegalArgumentException will be thrown.
   * post: Constructs a kill ring of Assassin nodes that contains all 
   *       names from the given list in the same order.      
   */
   public AssassinManager(List<String> names) {
      if(names == null || names.isEmpty()) {
         throw new IllegalArgumentException();
      }
      frtKill = null; 
      frtGrave = null;
      for(int i = names.size() - 1; i >= 0; i--) {
         frtKill = new AssassinNode(names.get(i), frtKill);
      }
   }
   
   /**
   * post: If the game is not over then, it will print the names of the
   *       people in the kill ring and who they are stalking.
   * Example: "X is stalking Y"
   *       If the game is over and the remaining person is the winner
   *       of the game then, the winner will be printed as stalking
   *       themself.
   * Example: "X is stalking X"
   */
   public void printKillRing() {
      AssassinNode curr = frtKill;
      while(curr != null) {
         System.out.print("    " + curr.name + " is stalking ");
         if(curr.next != null) {
            System.out.println(curr.next.name);
         } else {
            System.out.println(frtKill.name);
         }
         curr = curr.next; 
      }
   }
   
   /**
   * post: If the graveyard is empty then, it will print nothing.
   *       If the graveyard is not empty then, it will print the names
   *       in the graveyard as well as who was their killer in the opposite
   *       order of which they were assassinated.
   * Example: "X was killed by Y" 
   */
   public void printGraveyard() {
      AssassinNode curr = frtGrave;
      while(curr != null) {
          System.out.println("    " + curr.name + " was killed by " + curr.killer);
          curr = curr.next;
      }
   }
   
   /**
   * post: Regardless of the given name's case, it should return true if  
   *       the given name is in the kill ring.
   *       However, if the given name is not found in the kill ring then,
   *       it will return false .
   */
   public boolean killRingContains(String name) {
      return containsName(frtKill, name);
   }
   
   /**
   * post: Regardless of the given name's case, it should return true if
   *       the given name is found in the graveyard.
   *       However, if the given name is not found in the graveyard then,
   *       it will return false.
   */
   public boolean graveyardContains(String name) {
      return containsName(frtGrave, name);
   }
   
   /**
   * post: If the game is over which means that there is only one person
   *       in the kill ring then, it will return true.
   *       Otherwise, if there is still people in the kill ring, it will 
   *       return false. 
   */
   public boolean isGameOver() {
      return frtKill.next == null;
   }
   
   /**
   * post: If the game is over is not over then, it will return null.
   *       If the game is over is over then, it will return the name
   *       of the winner from the kill ring.
   */
   public String winner() {
      if(!isGameOver()) {
         return null;
      } else {
         return frtKill.name;
      }
   }
   
   /**
   * pre: If the game is over then, an IllegalStateException will be 
   *      thrown.
   *      If the game is not over but, the the kill ring does not 
   *      contain the given name. The IllegalArgumentException will be 
   *      thrown.
   * post: Without relatively changing the order of the kill ring and 
   *       the given name's case it will record the assassinated person
   *       with the given name as well as their killer. It will transfer 
   *       the assassinated person with the given name to the front of
   *       the graveyard as well as record their killer.
   *       If there is only one name in the kill ring then, the given name
   *       will be transferred to the front of the graveyard and reposition
   *       the order of the kill ring.
   */
   public void kill(String name) {
      if(isGameOver()) {
         throw new IllegalStateException();
      }
      if(!killRingContains(name)) {
         throw new IllegalArgumentException();
      }
      AssassinNode curr = frtKill;
      AssassinNode tempGrave = frtGrave;
      // Searches through the kill ring until it reaches null or unless if
      // finds the killer.
      while(curr.next != null && !curr.next.name.equalsIgnoreCase(name)) {
         curr = curr.next;
      }
      // If the name is at the very beginning of the kill ring.
      if(frtKill.name.equalsIgnoreCase(name)) {
         frtGrave = frtKill;
         frtKill = frtKill.next;
      } else {
         frtGrave = curr.next;
         curr.next = curr.next.next;
      }
      frtGrave.next = tempGrave;
      frtGrave.killer = curr.name;
   }
   
   /**
   * post: Searches and checks if the given name is located in the given list
   *       of players. Regardless of the given name's case, if it is found 
   *       within the given list it will return true. Otherwise, if it is not 
   *       found it will return false.
   */
   private boolean containsName(AssassinNode nodeList, String name) {
      AssassinNode curr = nodeList;
      while(curr != null) {
         if(curr.name.equalsIgnoreCase(name)) {
            return true;
         }
         curr = curr.next;
      }
      return false;
   }
}