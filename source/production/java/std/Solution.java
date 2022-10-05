/*
Ion Flux Relabeling
*/

package std;

import java.util.Arrays;

public class Solution {
  public static int[] solution(int h, int[] q) {

    int[] nodeParent = new int[q.length];

    for (int i = 0; i < q.length; i++) {
      nodeParent[i] = giveParent(h, q[i]);
    }

    return nodeParent;
  }

  public static boolean isPowerOfTwo(int x) {
    return x > 0 && (x & x - 1) == 0;
  }

  //this the floor() of log2
  public static int log2(int x) {
    return (int) (Math.log(x) / Math.log(2));
  }

  public static int giveHeight(int x) {
    int nodeHeight;

    // if (x + 1 = power of 2) then the formula to find the height is log2(x + 1)
    if (isPowerOfTwo(x + 1)) {
      nodeHeight = log2(x + 1);

    /* else:
     * v = x
     * y = log2(v)
     * z = (2^y) - 1
     * v = v - z until (v + 1) is pow2
     * then nodeHeight = log2(v + 1);
     */
    } else {
        while (!isPowerOfTwo(v + 1)) {
          int v = x;
          int y = log2(v);
          int z = (int) (Math.pow(2, y) - 1);
          v = v - z;
        }
        nodeHeight = log2(v + 1);
    }

    return nodeHeight;
  }

  public static int giveParent(int h, int x) {
    int nodeParent = 0;

    //root is ((2^h) - 1) where h is tree height
    int howManyNodes = (int) Math.pow(2, h) - 1;
    int v = x;


    // if (x + 1 = power of 2) then the formula to find the parent is (node + 2^nodeheight)
    if (isPowerOfTwo(x + 1)) {
      nodeParent = x + (int) (Math.pow(2, giveHeight(x)));

    /* if not:
     * y = integer whole of log2(node) floor(log2(node))
     * z = (2^y) - 1
     * then check if (node - z) + 1 is power of 2
     */
    } else {
      while (!isPowerOfTwo(v + 1)) {
        int y = log2(v);
        int z = (int) (Math.pow(2, y) - 1);

        //if z * 2 == v during this process, we know it's a right node. one less than parent
        if (z * 2 == v) {
          nodeParent = x + 1;
        }
        v = v - z;
      }
    }

    //this picks up the remaining left nodes that were not initially (x + 1 = power of 2)
    if (nodeParent == 0) {
      nodeParent = x + (int) (Math.pow(2, giveHeight(x)));
    }

    //if node height + 1 = tree height; parent will be root
    if (giveHeight(x) + 1 == h) {
      nodeParent = howManyNodes;
    }

    //recognize specified tree height and don't return higher than root
    if (nodeParent > howManyNodes) {
      nodeParent = -1;
    }

    return nodeParent;
  }

  public static void main(String []args) {
    int[] tryThis = solution(30, new int[]{79, 33, 52, 15});
    System.out.println(Arrays.toString(tryThis));
  }
}
