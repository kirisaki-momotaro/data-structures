package mypackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.sun.jdi.connect.Connector.BooleanArgument;
//import com.sun.tools.jdi.BooleanValueImpl;
/**
 * This is the main class which interacts with the user and includes all the main 
 * functions needed for the programs functionality
 * @author ioannidis christos 2018030006
 *
 */
public class TextManager {
	/**
	 * words: is the array list that stores values of type Word which include a String and a line number
	 * toggle: used to toggle the display of line number
	 * list: the list used to load the txt file and each node includes a line from it
	 * textInstance: used to implement singleton design pattern for this class
	 * numberOfPages: stores the number of buffered pages of the file opened
	 * fileName: stores the directory of the file to open
	 * 
	 */
	ArrayList<Word> words;
	Boolean toggle;
	public static DoublyLinkedList<String> list;
	public static TextManager textInstance;
	int numberOfPages;
	String fileName=null;
	String ndxFileName=null;
	int minSize=0;
	int maxSize=0;
	int bufferSize=0;
	
	
	/**
	 * Textmanager constructor and getInstance function used to implement singleton design pattern
	 * @param fname
	 */
	private TextManager(int minSize,int maxSize,int bufferSize,String fname,String ndxFileName) {
		list = new DoublyLinkedList<String>();
		words= new ArrayList<Word>();
		toggle=false;
		this.fileName=fname;
		this.ndxFileName=ndxFileName;
		this.minSize=minSize;
		this.maxSize=maxSize;
		this.bufferSize=bufferSize;
				
	}
	 public static TextManager getInstance(int minSize,int maxSize,int bufferSize,String fname,String ndxFileName) 
	    { 
	        if (textInstance == null) 
	            textInstance = new TextManager(minSize,maxSize,bufferSize,fname,ndxFileName); 
	  
	        return textInstance; 
	    } 
	 
	 
	 
	
	public static DoublyLinkedList<String> getList() {
		return list;
	}

	public static void setList(DoublyLinkedList<String> list) {
		TextManager.list = list;
	}

	
	
	
	/**
	 * readChar and readString used to get user input
	 * @return
	 */
	public char readChar() {
		System.out.println("please enter command");
		char c = 0;
		Scanner s= new Scanner(System.in);
		c = s.next().charAt(0);
		
		return c;
	}
	
	public String readString() {
		System.out.println("please enter string");
		String c = null;
		Scanner s= new Scanner(System.in);
		c = s.next();
		
		return c;
	}
	
	
	/**
	 * options: used to establish communication between the user and the program
	 */
	public void options() {
		/**
		 * quit:when true the program quits
		 * input:used to store command inputed by user
		 * str:used to store string inputed by user
		 */
		
			Boolean quit=false;
			
			while(quit==false) {
			char input =readChar();
	        
	        String str=null;
			switch (input) {
	            case '^':  System.out.println("go to first line");
	            			list.goToFirstLine();
	                     break;
	            case '$':  System.out.println("go to last line");
	            			list.goToLastLine();
	                     break;
	            case '-':  System.out.println("go up one line");
	            			list.readUp();
	                     break;
	            case '+':  System.out.println("Go down one line");
	            			list.readDown();
	                     break;
	            case 'a':  System.out.println("add new line after current");
	            			str=readString();
	            			list.addAfter(str.toString());
	            
	                     break;
	            case 't':  System.out.println("add new line before current");
	            			str=readString();
	            			list.addBefore(str);
	                     break;
	            case 'd':  System.out.println("delete current line");
	            			list.deleteLine();
	                     break;
	            case 'l': System.out.println("print all lines");
	            			list.printAll(toggle);
	                     break;
	            case 'n':  System.out.println("toggle whether line numbers are displayed when printing all lines");
	            			toggle=!toggle;
	                     break;
	            case 'p': System.out.println("print current line");
	            			list.printCurrent();
	                     break;
	            case 'q':System.out.println("quit without save");
	            			quit=true;
	                     break;
	            case 'w':System.out.println("write file to disk");
	            			fileOutput(fileName);
	            		
	                     break;
	            case 'x': System.out.println("exit with save");
	            			fileOutput(fileName);
	            			quit=true;
                		 break;
	            case '=': System.out.println("print current line number");
	            			System.out.println(list.getCurrentLine());
                		 break;
	            case '#': System.out.println("print number of lines and characters");
	            			System.out.println(list.size()+" lines");
	            			int chars=0;
	            			for(int i=0;i<list.size();i++) {
	            				chars+=list.get(i).data.length();
	            			}
	            			System.out.println(chars+" characters");
                		 break; 
	            case 'c': System.out.println("create index file");	
	            		  this.createIndexFile(minSize,maxSize,bufferSize,ndxFileName);
	            		  break;
	            case 'v': System.out.println("print index file");	
      		  			  this.printIndexFile(minSize,maxSize,bufferSize,ndxFileName);
      		  			  break;
	            case 's': System.out.println("serial word search");	
	                      String inWord=readString();	
	  			  		  this.serialSearch(inWord,maxSize,bufferSize,ndxFileName);
	  			  		  break;
	            case 'b': System.out.println("binary word search");	
                		  String inWord1=readString();	
                		  this.binarySearch(inWord1,maxSize,bufferSize,ndxFileName);
                		  break;  
	            /*case '.':
	            		  System.out.println("test results");	
	            		  this.test();
	            		  break;*/
	            default: System.out.println("Invalid Input");
	                     break;
	        }
			}
		
	}
	
	
	
	
	
	
	
	
	/**
	 * used to store prossesed file
	 * @param fname
	 */
	public void fileOutput(String fname) {
		
		/**
		 * fos:file pointer 
		 */
		RandomAccessFile fos = null;
		try {
			fos = new RandomAccessFile(fname, "rw");
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
			
			
			try {
				for(int i=0;i<list.size();i++) {
					fos.writeBytes(list.get(i).data);
					fos.writeBytes("\n");
					
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		
		
	}
	
	
	
	/**
	 * function for binary search
	 * returns number of disc accesses
	 * @param inWord
	 * @param maxSize
	 * @param bufferSize
	 * @param file:directory of index file
	 */
	public int binarySearch(String inWord,int maxSize,int bufferSize,String file) {
		/**
		 * fos: file pointer 
		 * wordSize:size of the words used in bytes
		 * wordsPerPage:number of words per page
		 * currentPage:used as a pointer to the currently loaded page
		 * discAccesses: number of disc accesses until a word is found
		 * left,currentPage,mid:used to implement binary search algorithm
		 * wordFound:turns true when the word is found for the first time
		 * nextPage:turns true when a word is found in the boundaries of a page
		 */
		File existingFile=new File(file);
		numberOfPages=(int) (existingFile.length()/bufferSize);
		RandomAccessFile fos=null;
		int wordSize=maxSize+4;
		int wordsPerPage=bufferSize/wordSize;
		int currentPage=numberOfPages/2;
		int discAccesses=0;
		int j=0;		
		currentPage=numberOfPages;
		int left=0;
		Boolean out=false;		
		Boolean wordFound=false;		
		Boolean nextPage=false;		
		int mid=0;
		
		
		try {
			fos = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}  
		
		System.out.print(inWord+" found in lines:");
		
		
		
		
		while(left<currentPage) {
			
			
		
			byte[] buffer=new byte[bufferSize];
			try {
				
				if(nextPage==false) {
					///////////////////////////////////////////////////////
					int lastMid=mid;
					mid=left+((currentPage -left)/2);
					if(lastMid==mid) {
						out=true;
					}
					/////////////////////////////////////////////////////
					//System.out.println(mid);
					fos.seek(bufferSize*mid);
				}else {
					fos.seek(bufferSize*mid);
				}
				
				
				fos.read(buffer);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ByteBuffer bb = ByteBuffer.wrap(buffer);
			
			for(int i=0;i<wordsPerPage;i++) {
				
			byte byteArray[] = new byte[maxSize];
			bb.get(byteArray, 0, maxSize);
			int someInt = bb.getInt();  
			String someString = new String(byteArray, java.nio.charset.StandardCharsets.US_ASCII); 
			
			if(inWord.length()>maxSize) {
			inWord=inWord.substring(0,maxSize);
			}
			if(inWord.length()<maxSize) {
				someString=someString.substring(0,inWord.length());
			}
			if(someString.equals(inWord)) {
				System.out.print(" "+someInt);
				wordFound=true;
				//discAccesses=j;				
				if(i==0) {
					mid=mid-1;
					nextPage=true;
				}
				if(i==wordsPerPage) {
					mid=mid+1;
					nextPage=true;
				}
				
				
				
			}else if(wordFound==true){
				if(someString.equals(inWord)==false) {
					out=true;
				}
			}
			if(i==0) {
				//System.out.println("first "+someString);
				if(inWord.compareTo(someString)<0) {
				currentPage=mid-1;				
				//System.out.println("its less");
				}
			}
			if(i==wordsPerPage-1) {
				//System.out.println("last "+someString);
				if(inWord.compareTo(someString)>0) {
					left=mid+1;				
				//System.out.println("its more");
				}
			}
			
			
			//j++;
			
			
			}
			
		if(out==true) {
			
			System.out.println("\n number of disc accesses:"+discAccesses);
			break;
		}
		discAccesses++;
		
		
	}
		if(wordFound==false) {
		System.out.println("\n word not found! number of disc accesses:"+discAccesses);}
		
		
		return discAccesses;
		}
	/**
	 * function for serial search
	 * returns number of disc accesses
	 * @param inWord
	 * @param maxSize
	 * @param bufferSize
	 * @param file:directory of index file
	 */
	public int serialSearch(String inWord,int maxSize,int bufferSize,String file) {
		
		
		/**
		 * fos: file pointer 
		 * wordSize:size of the words used in bytes
		 * wordsPerPage:number of words per page
		 * discAccesses:number of disc accesses until the word is found
		 */
		
		System.out.print(inWord+" found in lines:");
		
		FileInputStream fos = null;
		int wordSize=maxSize+4;
		int wordsPerPage=bufferSize/wordSize;	
		int discAccesses=0;
		boolean found=false;
		
		
		try {
			fos = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File existingFile=new File(file);
		numberOfPages=(int) (existingFile.length()/bufferSize);
		/**
		 * for loop that loads the file page by page and searches every pages words until the word is found
		 * buffer:an array of bytes inside which the page is loaded
		 */
		for(int j=0;j<numberOfPages;j++) {
			byte[] buffer=new byte[bufferSize];
			try {
				fos.read(buffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ByteBuffer bb = ByteBuffer.wrap(buffer);
			
			
			/**
			 * compares all words of the loaded page to the one inputed by the user
			 */
			for(int i=0;i<wordsPerPage;i++) {
				
			byte byteArray[] = new byte[maxSize];
			bb.get(byteArray, 0, maxSize);
			int someInt = bb.getInt();  
			String someString = new String(byteArray, java.nio.charset.StandardCharsets.US_ASCII); 
			
			/**
			 * if the inputed word size is bigger than the specified max size, cuts the word so that it can be compared
			 * if the word is found it prints it and and stores the value of disc accesses
			 */
			if(inWord.length()>maxSize) {
			inWord=inWord.substring(0,maxSize);
			}
			if(inWord.length()<maxSize) {
				someString=someString.substring(0,inWord.length());
			}
			if(someString.equals(inWord)) {
				System.out.print(" "+someInt);
				discAccesses=j;
				found=true;
				
			}
			if(inWord.compareTo(someString)<0) {
				if(found==false) {
				System.out.println("\n word not found");
				discAccesses=j;
				System.out.println("\n"+discAccesses+" disc accesses");
				return discAccesses;
				}
			}
			
			
			}
			
			
			}
		/**
		 * prints "word not found" message if word is not found 
		 * prints number of disc accesses if the word is found
		 */
		
		if(discAccesses==0) {
			System.out.println("\n word not found");
		}
		else {
		System.out.println("\n"+discAccesses+" disc accesses");
		}
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return discAccesses;
	}
	/**
	 * function for reading and printing index file
	 * @param minSize
	 * @param maxSize
	 * @param bufferSize
	 * @param file:directory of index file
	 */
	public void printIndexFile(int minSize,int maxSize,int bufferSize,String file) {
		

		/**
		 * fos: file pointer 
		 * wordSize:size of the words used in bytes
		 * wordsPerPage:number of words per page
		 */
		
		
		FileInputStream fos = null;
		int wordSize=maxSize+4;
		int wordsPerPage=bufferSize/wordSize;
		
		
		try {
			fos = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File existingFile=new File(file);
		numberOfPages=(int) (existingFile.length()/bufferSize);
		
		
		/**
		 * reads index file page by page and prints all words while also adding them to the words list
		 */
		for(int j=0;j<numberOfPages;j++) {
		byte[] buffer=new byte[bufferSize];
		try {
			
			fos.read(buffer);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		ByteBuffer bb = ByteBuffer.wrap(buffer);
		
		for(int i=0;i<wordsPerPage;i++) {
			
		byte byteArray[] = new byte[maxSize];
		bb.get(byteArray, 0, maxSize);
		
		
		int someInt = bb.getInt(); 
		String someString = new String(byteArray, java.nio.charset.StandardCharsets.US_ASCII);
		 
		System.out.println(someString+" "+someInt);
		words.clear();
		words.add(new Word(someString,someInt));
		}
		
		}
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * function for serial search
	 * @param minSize
	 * @param maxSize
	 * @param bufferSize
	 * @param file:directory to create index file
	 */
	public void createIndexFile(int minSize,int maxSize,int bufferSize,String file) {
		/**
		 * fos:file pointer
		 */
		FileOutputStream fos = null;
		words.clear();
		
		/**
		 * splits all list's stings into words and stores them into the words array list along with their line number
		 * if the word's size does not comply with the min-max size specified above the it proccesses the word accordingly and then stores it 
		 */
		for(int i=0;list.size()>i;i++) {
		String str=list.get(i).data;
		String delims = "[ .,?!]+";
		String[] result = str.split(delims);
		for (int x=0; x<result.length; x++) {
			if(result[x].length()>minSize ) {
				if(result[x].length()<maxSize) {
					while(result[x].length()<maxSize) {
						result[x]=result[x]+" ";
					}
					words.add(new Word(result[x],i+1));
				}
				else {
					String string=result[x].substring(0,maxSize);
					words.add(new Word(string,i+1));
				}
			}
		
		
		}
		
		
		}
		/**
		 * sorts words alphabetically and allocates space for byte array
		 */
		Collections.sort(words);
		int wordSize = maxSize+4;
		java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(wordSize*words.size());
		
		/**
		 * stores word string and line number into the byte array in byte form
		 */
		for(int j = 0;j<words.size();j++) {
			String someString =words.get(j).getWord();
			
			
			bb.put(someString.getBytes(java.nio.charset.StandardCharsets.US_ASCII));
			bb.putInt(words.get(j).getLine());
			
			
			System.out.println(words.get(j).getWord()+","+words.get(j).getLine());
		}
		int wordsPerPage=bufferSize/wordSize;
		
		byte byteArray[] = bb.array();
		byte toFile[]=new byte[bufferSize];
		
		
		int currentPage=0;
		numberOfPages=byteArray.length/bufferSize;
		System.out.println("number of pages "+numberOfPages);
		try {
			File oldFile=new File(file);
			oldFile.delete();
			
			fos = new FileOutputStream(file,true);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		String spaces="";
		for(int i=0;i<bufferSize-(wordSize*wordsPerPage);i++) {
			
			spaces=spaces+" ";
			
			
		}
		
		/**
		 * stores all pages of the array to a .ndx file 
		 */
		for(int o=0;o<numberOfPages;o++) {
			int k=0;
			
		/**
		 * takes a specified number of bytes from the byte array and stores them to the file as a page	
		 */
		for(k=0;k<wordsPerPage*wordSize;k++) {
		toFile[k]=byteArray[k+wordsPerPage*wordSize*currentPage];
		}
		
		java.nio.ByteBuffer aa = java.nio.ByteBuffer.allocate(wordSize*words.size());
		aa.put(spaces.getBytes(java.nio.charset.StandardCharsets.US_ASCII));
		byte spaceArray[] = aa.array();
		for(int t=0;t<spaceArray.length;t++) {
			toFile[k]=spaceArray[t];			
		}	
		try {
			
			
			fos.write(toFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentPage++;
		}
		
		
		
		
		System.out.println("created byte array "+wordsPerPage+" words per page, "+ numberOfPages+" number of pages, "+"words list size:"+words.size()+",byte array length:"+byteArray.length);
		
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
