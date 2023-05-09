package mypackage;


import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
/**
 * 
 * @author christos ioannidis 2018030006
 * class used for file input
 * String fileName:directory of the file
 * RandomAccessFile file:file opened
 * int[] fileInt:the contents of the file	
 * int numberOfInt:number of int contained in the file 
 */
public class FileInput {
	
	String fileName;
	RandomAccessFile file;
	int[] fileInt;
	int numberOfInt;
	/**
	 * reads a file in a given directory and saves the integers read in the list fileInt
	 */
	FileInput(String fileName){
		this.fileName=fileName;
		
		try {
			file=new RandomAccessFile(fileName,"r");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.numberOfInt=(int) (file.length()/4);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		fileInt=new int[numberOfInt];
		try {
			for(int i=0;i<numberOfInt;i++) {
				fileInt[i]=file.readInt();
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	int[] getFileInt() {
		return fileInt;
	}
	public int getNumberOfInt() {
		return numberOfInt;
	}
	public void setNumberOfInt(int numberOfInt) {
		this.numberOfInt = numberOfInt;
	}
	
}
