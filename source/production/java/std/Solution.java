/*
Ion Flux Relabeling

Power of Two Nodes:

If a node's index (1-based) plus one is a power of two, it is guaranteed
to be the left child of its parent. This is because binary trees have the
property that each node with index 'i' has children at indices '2i' and '2i+1'.
When 'i+1' is a power of two, '2i+1' is the next power of two, which means 'i'
is the left child of its parent.

To illustrate, consider an example where 'i+1' is a power of two
(e.g., 1, 2, 4, 8, etc.). In these cases, the binary representation of 'i' has
only one '1' bit, and 'i+1' has one more '1' bit, which means 'i' is the left
child of its parent.

Non-Power of Two Nodes:

When a node's index plus one is not a power of two, it means that node is
somewhere on the right subtree of its parent. In this case, you need to
calculate the height of the node and adjust the index accordingly.

---------------------------------------------------------------------------------------------
The code calculates the height of such a node using the giveHeight function.
It iteratively subtracts values 'z' from the index until 'i+1' becomes a
power of two. The value 'z' is calculated as '(2^y) - 1', where 'y' is the
largest integer such that '2^y' is less than or equal to 'i+1'. This ensures
that 'i+1' transitions from a non-power of two to the next power of two.

Once you have found the height and adjusted the index accordingly, you can
calculate the parent node using the formula 'i + 2^height'.

Consider a binary tree with nodes indexed from 1 to N, where N is the
total number of nodes. The root node is at index 1, and each node has
two children at indices 2i and 2i+1, where 'i' is the index of the parent
node.

Now, let's say we have a node at index 'x' (1-based index) in the binary
tree, and 'x+1' is not a power of two. We want to find its parent node.

Determine the Height of the Node:

To find the parent node, we first need to determine the height of the
node. The height of a node is defined as the number of edges in the
longest path from that node to a leaf node. We can calculate the height
of the node using the giveHeight function in your code.

The giveHeight function works as follows:

If 'x+1' is a power of two, it directly calculates the height using
the formula: height = log2(x+1). This is because the binary tree
has a regular structure, and you can calculate the height directly
when the node's index plus one is a power of two.

If 'x+1' is not a power of two, it enters a loop and subtracts
values 'z' from 'x' until 'x+1' becomes a power of two. The value
'z' is calculated as '(2^y) - 1', where 'y' is the largest integer
such that '2^y' is less than or equal to 'x+1'. This process continues
until 'x+1' becomes a power of two. At this point, 'x' has reached
the level just above the leftmost leaf nodes, and the height is known.

Calculate the Parent Node:

Once we know the height of the node, we can calculate its parent node
using the formula: parent = x + 2^height.

Here, 'x' represents the current node's index.

'height' is the height of the node we calculated earlier using the
giveHeight function.

Adding '2^height' to 'x' effectively moves 'x' up the tree to its
parent node, which is 'height' levels higher.

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
     * y = integer whole of log2(node)
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
