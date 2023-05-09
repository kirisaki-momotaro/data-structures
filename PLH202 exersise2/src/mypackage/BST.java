package mypackage;
/**
 * 
 * @author christos ioannidis 2018030006
 *	int root:holds the position of the root inside the bst
 *  int avail:the next available position of the list 
 *  int[][] bst:the list in which the bst is implemented
 *  int[] inOrderInt:the list returned by the in order function 
 *  int inOrderCounter:used to indicate the position inside inOrderInt that the next int will be saved
 *  int numberOfCompares:number of compares of a function
 */
public class BST {
	int root;
	int avail;
	int n;
	int[][] bst;
	int[] inOrderInt;
	int inOrderCounter;
	
	int numberOfCompares;
	
	/**
	 * constructor
	 * @param n
	 */
	public BST(int n) {
		
		
		
		inOrderInt=new int[n];
		inOrderCounter=0;
		
		
		
		this.root=-1;
		this.avail=0;
		this.n=n;
		this.bst=new int[3][n];
		for(int i=0;i<n;i++) {
			setRight(i, i+1);
			setValue(i, -1);
			setLeft(i, -1);
		}
		
		
	}
	/**
	 * finds numbers in a certain range
	 * @param current:pointer
	 * @param k1:min value
	 * @param k2:max value
	 */
	void range(int current,int k1,int k2) {
		numberOfCompares=0;
			if (increaseComparesByOne()&&current==-1) {
				return;
			}
			if(increaseComparesByOne()&&getValue(current)>k1) {
				numberOfCompares++;
				range(getLeft(current),k1,k2);
			}
			if(increaseComparesByOne()&&getValue(current)>k1 && getValue(current)<k2) {
				numberOfCompares++;
				//System.out.println(getValue(current));
			}
			if(increaseComparesByOne()&&getValue(current)<k2) {
				numberOfCompares++;
				range(getRight(current),k1,k2);
			}
		
	}
	/**
	 * creates an ordered list of the values stored in the bst
	 * @param current
	 * @return
	 */
	int[] inOrder(int current) {
		
		if(current!=-1) {
			inOrder(getLeft(current));
			//System.out.println(getValue(current));
			inOrderInt[inOrderCounter]=getValue(current);
			inOrderCounter++;
			inOrder(getRight(current));
		}
		return inOrderInt;
	}
	
	
	public int getRoot() {
		return root;
	}

	/**
	 * adds an int to the bst
	 * @param value
	 */
	void add(int value) {
		
		numberOfCompares=0;
		
		if(increaseComparesByOne()&&root==-1) {
			root=avail;
			avail=getRight(avail);
			setValue(root,value);
			setRight(root, -1);
			
			numberOfCompares+=4;
			
			
		}
		else {
			int current=root;
			boolean inPlace=false;
			while(inPlace==false) {
				
				numberOfCompares++;
				
				if(value>getValue(current)) {
					
					
					
					if(increaseComparesByOne()&&getRight(current)==-1) {
						setRight(current, avail);
						current=avail;
						avail=getRight(avail);
						setValue(current, value);						
						setRight(current, -1);
						inPlace=true;
						
						numberOfCompares+=6;
					}else {
					current=getRight(current);
					}
				}else {
					
					if(increaseComparesByOne()&&getLeft(current)==-1) {
						setLeft(current, avail);
						current=avail;
						avail=getRight(avail);
						setValue(current, value);						
						setRight(current, -1);
						inPlace=true;
						numberOfCompares+=6;
					}else {
					current=getLeft(current);
					}
				}
			}
			
		}
		
	}
	
	public int getNumberOfCompares() {
		return numberOfCompares;
	}

	public void setNumberOfCompares(int numberOfCompares) {
		this.numberOfCompares = numberOfCompares;
	}

	int find(int value) {
		numberOfCompares=0;
		
		int current=root;
		boolean found=false;
		
		while(found==false) {
			
			if(increaseComparesByOne()&&getValue(current)==value) {
				found=true;
				numberOfCompares++;
			}			
			else if(increaseComparesByOne()&&value>getValue(current)) {
				if(increaseComparesByOne()&&getRight(current)==-1) {
					current=-1;
					found=true;
					numberOfCompares+=2;
				}
				else {
					current=getRight(current);
				}
				
			}
			else if(increaseComparesByOne()&&value<getValue(current)) {
				if(increaseComparesByOne()&&getLeft(current)==-1) {
					current=-1;
					found=true;
					numberOfCompares+=2;
				}
				else {
					current=getLeft(current);
				}
				
			}
		}
		return current;
	}
	
	Boolean increaseComparesByOne() {
		++numberOfCompares;
		return true;
	}
	
	/**
	 * the functions bellow are used to simulate the use of nodes in a 3xn list 	
	 */
	int getValue(int current) {
		return bst[0][current];
	}
	void setValue(int root,int value) {
		bst[0][root]=value;
	}
	int getRight(int root){
		return bst[2][root];
		
	}
	void setRight(int root,int right) {
		bst[2][root]=right;
	}
	int getLeft(int root){
		return bst[1][root];
		
	}
	void setLeft(int root,int left) {
		bst[1][root]=left;
	}
}
