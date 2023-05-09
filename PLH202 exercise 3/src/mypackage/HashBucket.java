package mypackage;
import java.io.*;
/**
 * 
 * @author christos ioannidis 2018030006
 * private int keysNumber:number of keys inside the bucket
	private int[] keys:a list of the keys inside the bucket
	private HashBucket overflowBucket:the overflow bucket
	private int overflowBucketSize: size of the overflow bucket
	private int numberOfCompares:number of compares (used to count the number of compares when adding deleting or searching for a key)
 *
 */
class HashBucket {

	private int keysNumber;
	private int[] keys;
	private HashBucket overflowBucket;
	private int overflowBucketSize;
	private int numberOfCompares;
	
	/**
	 * 
	 * @param bucketSize
	 * // Constructor: initialize variables
	 */
	public HashBucket(int bucketSize) {		

		keysNumber = 0;
		keys = new int[bucketSize];
		overflowBucket = null;
		overflowBucketSize=10;
		numberOfCompares=0;
	}

	public int numKeys(){return keysNumber;}
	
	
	
	
/**
 * // inserts a key to the node
 * @param key:key to insert 
 * @param lh: used to access data from the instance of the LinearHashing class which contains this bucket
 *  */
	public void insertKey(int key, LinearHashing lh) { // inserts a key to the node
		numberOfCompares=0;


		int i;
		int bucketSize = lh.getBucketSize();
		int keysNum = lh.getKeysNum();
		int keySpace = lh.getKeySpace();
		
		numberOfCompares+=3;

		//for (i = 0; (i < this.keysNumber) && (i < bucketSize); i++){
		for (i = 0; (i < this.keysNumber) && (i < bucketSize); i++){
			numberOfCompares++;
		   if (this.keys[i] == key){	//key already here. Ignore the new one
		     return;
		   }
		}
		numberOfCompares++;
		if (i < bucketSize){				// bucket not full write the new key
		  numberOfCompares+=4;	
		  keys[i] = key;
		  this.keysNumber++;
		  keysNum++;
		  lh.setKeysNum(keysNum); 			// update linear hashing class.
		  //System.out.println("HashBucket.insertKey: KeysNum = " + keysNum );
		}
		else {
		    //System.out.println("Overflow.............");
			numberOfCompares++;
		    if (this.overflowBucket != null){	// pass key to the overflow
		      this.overflowBucket.insertKey(key, lh);
		    }
		    else {						// create a new overflow and write the new key
			//this.overflowBucket = new HashBucket(bucketSize);
		    	numberOfCompares+=4;
		    	this.overflowBucket = new HashBucket(overflowBucketSize);
			keySpace += bucketSize;
		        lh.setKeySpace(keySpace);		// update linear hashing class.
			this.overflowBucket.insertKey(key, lh);
		    }
		}
	}
	/**
	 * // deletes a key 
	 * @param key:key to delete 
	 * @param lh: used to access data from the instance of the LinearHashing class which contains this bucket
	 *  */
	public void deleteKey(int key, LinearHashing lh) { // code not correct
		numberOfCompares=0;

		int i;
		int bucketSize = lh.getBucketSize();
		int keysNum = lh.getKeysNum();
		int keySpace = lh.getKeySpace();
		numberOfCompares+=3;
		for (i = 0; (i < this.keysNumber) && (i < bucketSize); i++) {
			numberOfCompares++;
		   if (this.keys[i] == key) {
			   numberOfCompares++;
		     if (this.overflowBucket == null) {		// no overflow
		    	 numberOfCompares+=4;
			 this.keys[i] = this.keys[this.keysNumber-1];
			 this.keysNumber--;
			 keysNum--;
			 lh.setKeysNum(keysNum);			// update linear hashing class.
		     }
		     else {	// bucket has an overflow so remove a key from there and bring it here
		    	 numberOfCompares+=4;
			 this.keys[i] = this.overflowBucket.removeLastKey(lh);
			 keysNum--;
			 lh.setKeysNum(keysNum);			// update linear hashing class.
			 if (this.overflowBucket.numKeys() == 0) { // overflow empty free it
				 numberOfCompares+=3;
			   this.overflowBucket = null;
			   keySpace -= bucketSize;
		         lh.setKeySpace(keySpace);			// update linear hashing class.
			 }
		     }
		     
		     return;
		   }
		}
		numberOfCompares++;
		if (this.overflowBucket != null) {			// look at the overflow for the key to be deleted if one exists
		  this.overflowBucket.deleteKey(key, lh);
		  numberOfCompares++;
		  if (this.overflowBucket.numKeys() == 0) {	// overflow empty free it
			  numberOfCompares+=3;
		    this.overflowBucket = null;
		    keySpace -= bucketSize;
		    lh.setKeySpace(keySpace);				// update linear hashing class.
		  }
	      }
		
	}

	int removeLastKey(LinearHashing lh) {	// remove bucket last key

		int retval;
		int bucketSize = lh.getBucketSize();
		int keySpace = lh.getKeySpace();
		numberOfCompares+=2;
		numberOfCompares++;

		if (this.overflowBucket == null) {
			numberOfCompares++;
		  if (this.keysNumber != 0){
			  numberOfCompares++;
		    this.keysNumber--;
		    return this.keys[this.keysNumber];
		  }
		  return 0;
		}
		else {
			numberOfCompares++;
		  retval = this.overflowBucket.removeLastKey(lh);
		  numberOfCompares++;
		  if (this.overflowBucket.numKeys() == 0) {	// overflow empty free it
			  numberOfCompares++;
			  numberOfCompares++;
			  numberOfCompares++;
		    this.overflowBucket = null;
		    keySpace -= bucketSize;
		    lh.setKeySpace(keySpace);			// update linear hashing class.
		  }
		  return retval;
		}
	}

	/**
	 * // searches for a key
	 * @param key:key to search for 
	 * @param lh: used to access data from the instance of the LinearHashing class which contains this bucket
	 *  */
	public boolean searchKey(int key, LinearHashing lh) {
		numberOfCompares=0;
		int i;
		int bucketSize = lh.getBucketSize();
		numberOfCompares++;
		numberOfCompares++;

		for (i = 0; (i < this.keysNumber) && (i < bucketSize); i++) {
			numberOfCompares++;
		   if (this.keys[i] == key) {	//key found
		     return true;
		   }
		}
		numberOfCompares++;
		if (this.overflowBucket != null) {				//look at the overflow for the key if one exists
		  return this.overflowBucket.searchKey(key,lh);
	      }
	      else {
		  return false;
	      }
	}

	public int getNumberOfCompares() {
		return numberOfCompares;
	}

	public void setNumberOfCompares(int numberOfCompares) {
		this.numberOfCompares = numberOfCompares;
	}
	/**
	 * splits the bucket when the maximum threshhold for bucket spliting is exceeded
	 * @param lh
	 * @param n
	 * @param bucketPos
	 * @param newBucket
	 */
	public void splitBucket(LinearHashing lh, int n, int bucketPos, HashBucket newBucket) {	//splits the current bucket

		int i;
		int bucketSize = lh.getBucketSize();
		int keySpace = lh.getKeySpace();
		int keysNum = lh.getKeysNum();
		numberOfCompares++;numberOfCompares++;numberOfCompares++;

		for (i = 0; (i < this.keysNumber) && (i < bucketSize);) {
			numberOfCompares++;
		   if ((this.keys[i]%n) != bucketPos){	//key goes to new bucket
			   numberOfCompares++;numberOfCompares++;numberOfCompares++;numberOfCompares++;numberOfCompares++;numberOfCompares++;
		     newBucket.insertKey(this.keys[i], lh);
		     this.keysNumber--;
		     keysNum = lh.getKeysNum();
		     keysNum--;
		     lh.setKeysNum(keysNum);		// update linear hashing class.
		     //System.out.println("HashBucket.splitBucket.insertKey: KeysNum = " + keysNum );
		     this.keys[i] = this.keys[this.keysNumber];
		   }
		   else {				// key stays here
		     i++;numberOfCompares++;
		   }
		}numberOfCompares++;

		if (this.overflowBucket != null) {	// split the overflow too if one exists
		  this.overflowBucket.splitBucket(lh, n, bucketPos, newBucket);
		}
		while (this.keysNumber != bucketSize) {
			numberOfCompares++;
		     if (this.overflowBucket == null) {
			 return;
		     }
		     numberOfCompares++;
		     if (this.overflowBucket.numKeys() != 0) {
		       this.keys[this.keysNumber] = this.overflowBucket.removeLastKey(lh);
		       numberOfCompares++;
		       if (this.overflowBucket.numKeys() == 0) {	// overflow empty free it
		    	   numberOfCompares++;numberOfCompares++;numberOfCompares++;
			 this.overflowBucket = null;
			 keySpace -= bucketSize;
			 lh.setKeySpace(keySpace);      // update linear hashing class.
		       }
		       this.keysNumber++;numberOfCompares++;
		     }
		     else {				// overflow empty free it
			 this.overflowBucket = null;numberOfCompares++;
			 keySpace -= bucketSize;numberOfCompares++;
		         lh.setKeySpace(keySpace);	numberOfCompares++;// update linear hashing class.
		     }
	 	}
	}
	/**
	 * 
	 * @param lh
	 * @param oldBucket
	 */
	public void mergeBucket(LinearHashing lh, HashBucket oldBucket) {	//merges the current bucket

		int keysNum = 0;numberOfCompares++;

		while (oldBucket.numKeys() != 0) {numberOfCompares++;
		     this.insertKey(oldBucket.removeLastKey(lh), lh);
		}
	}

      public void printBucket(int bucketSize) {

		int i;

		System.out.println("keysNum is: " + this.keysNumber);
		for (i = 0; (i < this.keysNumber) && (i < bucketSize); i++) {
		   System.out.println("key at: " + i + " is: " + this.keys[i]);
		}
		if (this.overflowBucket != null) {
		  System.out.println("printing overflow---");
		  this.overflowBucket.printBucket(bucketSize);
		}
	}

	
} // HaskBucket class
