package mypackage;

/**
 * 
 * @author christos ioannidis 2018030006
 *
 */
class BinarySearchTreeDynamic { 
	  int numberOfCompares;
    /** Class containing left and right child of current node and key value*/
    
  
    /**
     *  Root of BST 
     */
    Node root; 
  
    /**
     * Constructor 
     */
    BinarySearchTreeDynamic() {  
        root = null;  
        numberOfCompares=0;
    } 
    
    
    /**
     * used to search for a value in the BST
     * @param value
     * @return
     */
    int find(int value) {
		numberOfCompares=0;
		
		Node current=root;
		boolean found=false;
		
		while(found==false) {
			
			if(increaseComparesByOne()&&current.key==value) {
				found=true;
				numberOfCompares++;
			}			
			else if(increaseComparesByOne()&&value>current.key) {
				if(increaseComparesByOne()&&current.right==null) {
					current=null;
					found=true;
					numberOfCompares+=2;
				}
				else {
					current=current.right;
				}
				
			}
			else if(increaseComparesByOne()&&value<current.key) {
				if(increaseComparesByOne()&&current.left==null) {
					current=null;
					found=true;
					numberOfCompares+=2;
				}
				else {
					current=current.left;
				}
				
			}
		}
		return 1;
	}
    public Node getRoot() {
		return root;
	}
	public void setRoot(Node root) {
		this.root = root;
	}
	// This method mainly calls insertRec() 
	/**
     * used to add a value in the BST
     *
     */
    void add(int key) { 
    	numberOfCompares=0;
       root = insertRec(root, key); 
    } 
      
    public int getNumberOfCompares() {
		return numberOfCompares;
	}
	public void setNumberOfCompares(int numberOfCompares) {
		this.numberOfCompares = numberOfCompares;
	}
	/* A recursive function to insert a new key in BST */
    Node insertRec(Node root, int key) { 
  
        /* If the tree is empty, return a new node */
        if (increaseComparesByOne()&&root == null) { 
            root = new Node(key); 
            numberOfCompares++;
            return root; 
        } 
  
        /* Otherwise, recur down the tree */
        if (increaseComparesByOne()&&key < root.key) {
            root.left = insertRec(root.left, key); 
            numberOfCompares++;
        }
        else if (increaseComparesByOne()&&key > root.key) 
            root.right = insertRec(root.right, key); 
  
        /* return the (unchanged) node pointer */
        return root; 
    } 
  
    // This method mainly calls InorderRec() 
    
    Boolean increaseComparesByOne() {
		++numberOfCompares;
		return true;
	}
  
  
    
 // This method mainly calls deleteRec() 
    /**
     * used to delete a value in the BST
     *
     */
    void deleteKey(int key) 
    { 
        root = deleteRec(root, key); 
    } 
  
    /* A recursive function to insert a new key in BST */
    Node deleteRec(Node root, int key) 
    { 
    	numberOfCompares=0;
        /* Base Case: If the tree is empty */
    	numberOfCompares++;
        if (root == null)  return root; 
  
        /* Otherwise, recur down the tree */
        numberOfCompares++;
        if (key < root.key) {
            root.left = deleteRec(root.left, key); 
        numberOfCompares++;}        
        else if (key > root.key&&increaseNumberOfCompares()) {
        	numberOfCompares++;
            root.right = deleteRec(root.right, key); }
  
        // if key is same as root's key, then This is the node 
        // to be deleted 
        else
        { 
            // node with only one child or no child 
        	numberOfCompares++;
            if (root.left == null) {
            	 numberOfCompares++;
                return root.right; 
           }            
            else if (root.right == null&&increaseNumberOfCompares()) { 
            	numberOfCompares++;
                return root.left; }
  
            // node with two children: Get the inorder successor (smallest 
            // in the right subtree) 
            root.key = minValue(root.right); 
  
            // Delete the inorder successor 
            root.right = deleteRec(root.right, root.key); 
        } 
  
        return root; 
    } 
    int minValue(Node root) 
    { 
    	numberOfCompares++;
        int minv = root.key; 
        while (root.left != null) 
        { 
        	numberOfCompares++;
        	numberOfCompares++;
            minv = root.left.key; 
            root = root.left; 
        } 
        return minv; 
    } 
    boolean increaseNumberOfCompares() {
    	numberOfCompares++;
    	return true;
    }

}