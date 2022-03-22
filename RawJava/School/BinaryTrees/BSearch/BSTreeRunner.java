//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

import static java.lang.System.*;

public class BSTreeRunner {
   public static void main(String args[]) {
      // add test cases here
      // int[] in = new int[] { 90, 80, 100, 70, 85, 98, 120 };
      // int[] in = new int[] {48, 23, 90, 11, 28, 77, 150, 55, 62};
      BinarySearchTree bst = new BinarySearchTree();
      // for (int i : in) {
      //    bst.add(i);
      // }
      for(int i = 0; i < 8000; i++){
         bst.add((Math.random() * 1000));
      }
      // bst.preOrder();
      // bst.postOrder();
      // bst.revOrder();
      System.out.println("Tree height is " + bst.getHeight());
      System.out.println("Number of leaves is " + bst.getNumLeaves());
      System.out.println("Number of levels is " + bst.getNumLevels());
      System.out.println("Number of nodes is " + bst.getNumNodes());
      System.out.println("Tree width is " + bst.getWidth());
      // System.out.println("Tree as string " + bst);
      if(bst.isFull()){
         System.out.println("The tree is full");
      } else {
         System.out.println("The tree is not full");
      }

   }
}