package mypackage;
import java.io.*; 
import java.util.*;

/**
 * 
 * @author christos ioannidis 2018030006
 * 
 * private static int initPages: number of pages created initially by the linear hashing algorithm
 * private static int pageSize: key capacity of each page
 * private int keysNo:total number of keys to store
 * static float maxThresh:an indicator of when to split a bucket
 * static float maxThresh2:an indicator of when to split a bucket
 * static float minThresh:an indicator of when to merge buckets
 * LinearHashing Hash1:an instance of the LinearHashing class used to access linear hashing functions
 * static int []fileInts:all the keys read from the file
 *
 */

public class LinearMain {
	private static int initPages;
	private static int pageSize;
	
	private int keysNo;
	
	static float maxThresh;
	static float maxThresh2;
	static float minThresh;
	LinearHashing Hash1;
	static int []fileInts;
	static long programStartTime;
	static long programEndTime;
	
	
	public static void main(String args[]) throws IOException, ClassNotFoundException {
		initPages = 100; pageSize = 10;
		
		minThresh=(float) 0.5;
		maxThresh=(float)0.5;
		maxThresh2=(float)0.8;
		
		/**
		 * Used to count program total runtime
		 */
		programStartTime=0;
		programEndTime=0;
		programStartTime=System.nanoTime();
		
		
		
		String fileDirectory=args[0];
		//FileInput fin=new FileInput("C:\\Users\\chris\\Desktop\\testnumbers_10000_BE.bin");
		FileInput fin=new FileInput(fileDirectory);
		fileInts=fin.getFileInt();
		
		LinearMain lm=new LinearMain();
		lm.averageHash(maxThresh);
		lm.averageHash(maxThresh2);
		lm.averageBST();
		
		
		
		
		programEndTime=System.nanoTime();
		long programProccessTime = programEndTime-programStartTime;
		
		if(programProccessTime/1000000>=1) {
			programProccessTime/=1000000;
			System.out.print("\nTotal run time:"+programProccessTime+" ms");
		}
		else {
			System.out.print("\nTotal run time:"+programProccessTime+" ns");
		}
	
	
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
		
		
		
		
		
		
		
		
		
/**
 * prints the average number of compares by 100 keys for insertion,search,deletion of a BST
 */
void averageBST() {
	//System.out.println("Creating BST...."); 
	BinarySearchTreeDynamic bstd=new BinarySearchTreeDynamic();
	//System.out.println( "Adding " + keysNo + " keys...");
	keysNo = fileInts.length;
	
	/**
	 * number of compares counters
	 */
	int averageCompAdd=0;
	int averageCompSearch=0;
	int averageCompDelete=0;
	int [][]averageCompList= new int[3][keysNo/100];
	
	for(int o=0;o<keysNo;o+=100) {
		for(int j=0;j<100;j++) {			
			bstd.add(fileInts[o+j]);
			averageCompAdd+=bstd.getNumberOfCompares();							
		}
		averageCompAdd/=100;		
		//System.out.println("average comp for insert BST "+(o+100)+":"+averageCompAdd);
		averageCompList[0][o/100]= ((int) averageCompAdd);
		/**
		 * the 50 random numbers could as well be the first 50 ints from every 100
		 */
		for(int k=0;k<50;k++) {
			int search_key = (fileInts[o+k]);
			bstd.find(search_key);
			averageCompSearch+=bstd.getNumberOfCompares();					
		}
		averageCompSearch/=50;
		//System.out.println("average comp for search BST "+(o+100)+":"+averageCompSearch);
		averageCompList[1][o/100]= ((int) averageCompSearch);
		/**
		 * the 50 random numbers could as well be the first 50 ints from every 100
		 */
		for(int d=0;d<50;d++) {
			int delete_key = (fileInts[o+d]);		       	
			bstd.deleteKey(delete_key);
			averageCompDelete+=bstd.getNumberOfCompares();					
		}
		averageCompDelete/=50;
		//System.out.println("average comp for delete bst "+(o+100)+":"+averageCompDelete);
		averageCompList[2][o/100]= ((int) averageCompDelete);
	}
	for(int t=0;t<3;t++) {
		if(t==0) {
			
			System.out.print("\n\naverage compares for adding element BST: ");
		}
		if(t==1) {
			System.out.print("\naverage compares for searching 50 elements BST: ");
		}
		if(t==2) {
			System.out.print("\naverage compares for deleting 50 elements BST: ");
		}
		for(int p=0;p<keysNo;p+=100) {
			System.out.print(averageCompList[t][p/100]+",");
		}
	}
	}
	
	
		

/**
 * prints the average number of compares by 100 keys for insertion,search,deletion of a Hash Table
 * maxThresh: indicates the maximum threshhold which when exceeded a bucket split is triggered
 **/
void averageHash(float maxThresh) {
	
	//System.out.println("Creating Linear Hash Table...."); 
	LinearHashing Hash1 = new LinearHashing(pageSize, initPages,maxThresh,minThresh);

	//System.out.println("\nPlease enter the number of keys you wish to insert:");
	keysNo = fileInts.length;

	//System.out.println( "Adding " + keysNo + " keys...");
	/**
	 * number of compares counters
	 */
	double averageCompAdd=0;
	double averageCompSearch=0;
	double averageCompDelete=0;
	int [][]averageCompList= new int[3][keysNo/100];
	
	
	for(int o=0;o<keysNo;o+=100) {
		for(int j=0;j<100;j++) {
			Hash1.insertKey(fileInts[o+j]);
			averageCompAdd+=Hash1.getTotalCompares();			
		}
		averageCompAdd/=100;
		//System.out.println("average comp for insert key hash "+(o+100)+":"+(int)averageCompAdd);
		averageCompList[0][o/100]= ((int) averageCompAdd);
		/**
		 * the 50 random numbers could as well be the first 50 ints from every 100
		 */
		for(int k=0;k<50;k++) {
			int search_key = (fileInts[o+k]);
			boolean result = Hash1.searchKey(search_key);
			averageCompSearch+=Hash1.getTotalCompares();				
		}
		averageCompSearch/=50;
		//System.out.println("average comp for search hash "+(o+100)+":"+(int)averageCompSearch);
		averageCompList[1][o/100]= ((int) averageCompSearch);
		/**
		 * the 50 random numbers could as well be the first 50 ints from every 100
		 */
		for(int d=0;d<50;d++) {
			int delete_key = (fileInts[o+d]);		       	
		  Hash1.deleteKey(delete_key);
			averageCompDelete+=Hash1.getTotalCompares();				
		}
		averageCompDelete/=50;
		//System.out.println("average comp for delete hash "+(o+100)+":"+(int)averageCompDelete);
		averageCompList[2][o/100]= (int)averageCompDelete;
	}
	for(int t=0;t<3;t++) {
		if(t==0) {
			System.out.print("\n\naverage compares for adding element Hash Table u="+ maxThresh +": ");
		}
		if(t==1) {
			System.out.print("\naverage compares for searching 50 elements Hash Table u="+ maxThresh +": ");
		}
		if(t==2) {
			System.out.print("\naverage compares for deleting 50 elements Hash Table u="+ maxThresh +": ");
		}
		for(int p=0;p<keysNo;p+=100) {
			System.out.print(averageCompList[t][p/100]+",");
		}
	}
}
}

