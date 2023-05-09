package mypackage;
/**
 * 
 * @author christos ioannidis 2018030006
 *
 */
class BinarySearchAlgorithm { 
    // Returns index of x if it is present in arr[l.. 
    // r], else return -1 
	int numberOfCompares;
	int[] arr;
	/**
	 * reads an inputed int array 1xn
	 * @param arr
	 */
	public BinarySearchAlgorithm(int arr[]) {
		this.arr=arr;
		numberOfCompares=0;
		
	}
		
	
	
	
	Boolean increaseComparesByOne() {
		++numberOfCompares;
		return true;
	}
	void range(int min,int max) {
		numberOfCompares=0;
		int totalCompares=0;
		int minPos=find(min);
		totalCompares+=numberOfCompares;
		int maxPos=find(max);
		totalCompares+=numberOfCompares;
		for(int i=minPos;i<maxPos;i++) {
			//System.out.println(arr[i]);
			totalCompares++;
		}
		numberOfCompares=totalCompares;
	}
	/**
	 * finds the position of a value x
	 * @param x
	 * @return
	 */
	int find( int x) 
    { 
		numberOfCompares=0;
        int l = 0, r = arr.length - 1; 
        while (l <= r) { 
            int m = l + (r - l) / 2; 
  
            // Check if x is present at mid 
            if (increaseComparesByOne()&&arr[m] == x) 
                return m; 
  
            // If x greater, ignore left half 
            if (increaseComparesByOne()&&arr[m] < x) {
                l = m + 1; 
                numberOfCompares++;
            }
            
  
            // If x is smaller, ignore right half 
            else
                r = m - 1; 
        }// if we reach here, then element was 
        // not present 
        return -1;  }
	public int getNumberOfCompares() {
		return numberOfCompares;
	}
	public void setNumberOfCompares(int numberOfCompares) {
		this.numberOfCompares = numberOfCompares;
	} }
