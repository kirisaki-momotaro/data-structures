package mypackage;


/**
 * 
 * @author christos ioannidis 2018030006
 *int key: stores the value the node holds 
 *Node left, right:pointers to the next nodes
 */

class Node { 
    int key; 
    Node left, right; 

    public Node(int item) { 
        key = item; 
        left = right = null; 
    } 
} 
