package mypackage;
/**
 * stores word string and line found and implements comparable for sorting
 * @author ioannidis christos 2018030006
 *
 */
public class Word implements Comparable<Word>{
	private String word;
	private int line;
	public Word(String word,int line) {
		this.word=word;
		this.line=line;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	
	
	public int compareTo(Word o) {
		return word.compareTo(o.word);
		// TODO Auto-generated method stub
		
	}
	
}
