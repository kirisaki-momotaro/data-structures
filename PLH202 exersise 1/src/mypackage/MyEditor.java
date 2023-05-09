package mypackage;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import jdk.jfr.Experimental;
/**
 * 
 * @author ioannidis christos 2018030006
 *
 */
public class MyEditor {
	TextManager textManager;
	public static void main(String[] args) {
		/**
		 * initiallises textManager
		 */
		//String fileDirectory="C:\\Users\\chris\\Desktop\\testfile_x2.txt";
		String fileDirectory=args[0];
		String ndxFileDirectory=fileDirectory+".ndx";
		int minWordSize=5;
		int maxWordSize=20;
		int bufferSize=128;
		int lineBufferSize=80;
		
		TextManager textManager=TextManager.getInstance(minWordSize,maxWordSize,bufferSize,fileDirectory,ndxFileDirectory);
		
		FileInput fin=new FileInput(lineBufferSize,minWordSize,maxWordSize,bufferSize,fileDirectory,ndxFileDirectory);
		
		
		
		
		
		
		
		
		
		
		textManager.options();
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
