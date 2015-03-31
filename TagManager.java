//Karsten Roberts
//Programming Assignment 12
//This class controls the game of tag
//TagManager
//24 March 2015

import java.util.*;

public class TagManager{
   /**
   *This is the first node of the list made up of people still playing the game
   */
   private TagNode headRemaining;
   /**
   *This is the first node of the list made up of people who are out
   */
   private TagNode headOut;
   /**
   *This is the constructor for TagManager objects
   *
   *@param names This is a list made up of the names of the orginal people playing tag
   */
   public TagManager(List<String> names){
      //System.out.println("Object Created");
      if(names == null || names.size() == 0) {//if there are no people in the list, the rest of the program will not work
         throw new IllegalArgumentException();
      }
      else{
         headRemaining = new TagNode(names.get(0));
         TagNode anchor = headRemaining;
         for(int nodeAssignment = 1; nodeAssignment < names.size(); nodeAssignment++){
            anchor.next = new TagNode(names.get(nodeAssignment));
            anchor = anchor.next;
         }
      }
   }
   /**
   *This method prints out the people who haven't been tagged yet.
   */
   public void printTagRing(){
      //System.out.println("Print tagRing Function");
      String toPrint = null;
      String toAdd = null;
      TagNode anchor = headRemaining;
      while(anchor != null){
         toPrint = "  " + anchor.name + " is stalking ";
         if(anchor.next == null){
            toAdd = headRemaining.name;
            //toPrint = toPrint + headRemaining.name;
         }
         else{
            toAdd = anchor.next.name;
            //toPrint = toPrint + anchor.next.name;
         }
         anchor = anchor.next;
         toPrint = toPrint + toAdd;
         System.out.println(toPrint);
      }
   }
   /**
   *This method calls the method loserPrint, which prints out the list of losers, from most recently tagged to the first person tagged
   */
   public void printLosers(){
      //System.out.println("Print losers");
      TagNode anchor = headOut;
      loserPrint(anchor);            
   }
   /**
   *This method prints out the list of people tagged in reverse order
   */
   public void loserPrint(TagNode toPrint){
      if(toPrint != null){
         loserPrint(toPrint.next);
         System.out.println("  " + toPrint.name + " was tagged by " + toPrint.tagger);
      }
      else{
         return;
      }
   }
   /**
   *This method calls the method namePresent, which checks to see if the person has not yet been tagged
   *
   *@param name This is the name of the person we are looking for. 
   *@return This method returns true if the person has not yet been tagged, and false if they have been
   */
   public boolean tagRingContains(String name){
      //System.out.println("tagRingContains");
      return namePresent(name, headRemaining);
   }
   /**
   *This method calls the method namePresent, which checks to see if the person has been tagged
   *
   *@param name This is the name of the person who we are looking for
   *@return This method returns true if the person has been tagged, and false if they have not been
   */
   public boolean losersContains(String name){
      //System.out.println("losersContains");
      return namePresent(name, headOut);
   }
   /**
   *This method checks to see if a name is present in the given list
   *
   *@param name This is the name being searched for.
   *@param head This is the head node of the list being searched through
   *@return This method returns true if the name is present in the given list, and false if it is not
   */
   public boolean namePresent(String name, TagNode head){
      //System.out.println("namePresent");
      name = name.toLowerCase();
      TagNode anchor = head;
      while(anchor!=null){
         if (anchor.name.toLowerCase().equals(name)){
            return true;
         }
         anchor = anchor.next;
      }
      return false;
   }
   /**
   *This method checks to see whether the game is over or not
   *
   *@return This method returns true if there is only one person left untagged, or false if there are more
   */
   public boolean isGameOver(){
      //System.out.println("isGameOver");
      if(headRemaining.next == null){
         return true;
      }
      return false;
   }
   /**
   *This method returns the name of the winner if the game is over
   *
   *@return This method returns the name of the winner if the game is over, or null if it is not
   */
   public String winner(){
      //System.out.println("winner");
      if(isGameOver()){
         return headRemaining.name;
      }
      return null;
   }
   /**
   *This method simulates the tagging of one person in the list by another
   *
   *@param name This is the name of the person to be tagged.
   */
   public void tag(String name){
      //System.out.println("tag");
      name = name.toLowerCase();
      if (isGameOver()){
         throw new IllegalStateException();//If the game is over, there is nobody to tag. This should not happen, as it is taken care of in TagMain
      }
      else if(!tagRingContains(name)){
         throw new IllegalArgumentException();//If the name does not exist in the list, you can not tag it. This should not not happen, as it is taken care of in TagMain
      }
      else{
         TagNode anchor = headRemaining; 
         TagNode anchorChain = null;
         TagNode endOfChain = headRemaining;
         while(endOfChain.next!=null){//finds the end of the list of the remaining people
            endOfChain = endOfChain.next;
         }
         while(anchor != null){
            if(anchor.name.toLowerCase().equals(name)){//steps through the whole list checking for the name
               if(anchorChain == null){//if the person is the first in the list, execute the following code
                  anchor.tagger = endOfChain.name;
                  headRemaining = anchor.next;
                  anchor.next = null;
                  taggedOut(anchor);
               }
               else{//otherwise, do this
                  anchor.tagger = anchorChain.name;
                  anchorChain.next = anchor.next;
                  anchor.next = null;
                  taggedOut(anchor);
               }
            }
            anchorChain = anchor;
            anchor = anchor.next;
         }
      }
   }
   /**
   *This method adds the tagged individual to the end of the tagged list
   *
   *@param tagged This is the TagNode object of the person who was tagged.
   */
   public void taggedOut(TagNode tagged){
      //System.out.println("taggedOut");
      TagNode anchor = headOut;
      if(headOut == null){//If this is the first person to be tagged, it sets the first node equal to them
         headOut = tagged;
      }
      else{
         while(anchor.next != null){//This sets anchor to the last node in the tagged out list
            anchor = anchor.next;
         }
         anchor.next = tagged;
      }
   }
}