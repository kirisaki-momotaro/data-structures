package mypackage;

class BinarySearchTreeDynamic { 
	  int numberOfCompares;
    /* Class containing left and right child of current node and key value*/
    
  
    // Root of BST 
    Node root; 
  
    // Constructor 
    BinarySearchTreeDynamic() {  
        root = null;  
        numberOfCompares=0;
    } 
    void range(Node current,int k1,int k2) {
		numberOfCompares=0;
			if (increaseComparesByOne()&&current==null) {
				return;
			}
			if(increaseComparesByOne()&&current.key>k1) {
				numberOfCompares++;
				range(current.left,k1,k2);
			}
			if(increaseComparesByOne()&&current.key>k1 && current.key<k2) {
				numberOfCompares++;
				//System.out.println(current.key);
			}
			if(increaseComparesByOne()&&current.key<k2) {
				numberOfCompares++;
				range(current.right,k1,k2);
			}
		
	}
    
    
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
    void inorder()  { 
       inorderRec(root); 
    } 
    Boolean increaseComparesByOne() {
		++numberOfCompares;
		return true;
	}
  
    // A utility function to do inorder traversal of BST 
    void inorderRec(Node root) { 
        if (root != null) { 
            inorderRec(root.left); 
            System.out.println(root.key); 
            inorderRec(root.right); 
        } 
    } }