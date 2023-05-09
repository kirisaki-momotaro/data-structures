package mypackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * class used for input-output
 *@author ioannidis christos 2018030006
 *
 */
public class FileInput {
	private int buffer;
	private String line;
	
	///////////////////////////////////////////////////////////////////////
	/**
	 * stores the contents of a list to a file node by node
	 * @param fname:name of the file it writes to
	 */
	public void fileOutput(int minSize,int maxSize,int bufferSize,String fname,String ndxFileName) {
		BufferedWriter bw = null;
		
		
			try {
				bw = new BufferedWriter(new FileWriter(fname));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TextManager tm=TextManager.getInstance(minSize,maxSize,bufferSize,fname,ndxFileName);
			
			try {
				for(int i=0;i<tm.getList().size();i++) {
					bw.write(tm.getList().get(i).data);
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	/**
	 * it reads a text file and adds the text line by line to the linked list initialised in TextManager
	 * @param fname:directory of the file to load
	 */
	
	public FileInput(int lineBufferSize,int minSize,int maxSize,int bufferSize,String fname,String ndxFileName) {
		
		File in=new File(fname);
		if(in.canRead()==false) {
			try {
				in.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fname));
		} catch (FileNotFoundException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextManager tm=TextManager.getInstance(minSize,maxSize,bufferSize,fname,ndxFileName);
		
		
		try {
			if(br.readLine()!=null){
			while ((line = br.readLine()) != null) {
				if(line.length()>lineBufferSize) {
					line=line.substring(0,lineBufferSize);
				}
				
				tm.getList().addEnd(line);
				
			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


	public int getBuffer() {
		return buffer;
	}

	public void setBuffer(int buffer) {
		this.buffer = buffer;
	}
}
