package mypackage;
/**
 * @author ioannidis christos 2018030006
 */
import java.util.NoSuchElementException;
public class DoublyLinkedList<T> {
    // Front / head node
	
	/**
	 * front:pointer to the first node
	 * current:pointer the the current node
	 * size:list size
	 * currentLine:it stores a value 1-size which indicates the position of the current node
	 */
    private ListNode<T> front;
    private ListNode<T> current;
    private int size;
    private int currentLine;
    
    
    
    public int getCurrentLine() {
		return currentLine;
	}
	public void setCurrentLine(int currentLine) {
		this.currentLine = currentLine;
		
	}
	/**
     * Constructing empty list.
     */
    public DoublyLinkedList() {
        front = null;
        current = front;
        currentLine=1;
    }
    
    
    public String getCurrent(){
    	return current.toString();
    }
    /**
     * returns a node in a certain position
     * @param x
     * @return
     */
    public ListNode<T> get(int x){
    	current=front;
    	for(int i=0;i<x;i++) {
    		current=current.next;
    	}
    	return current;
    }
    /**
     * goes up one line
     */
    public void readUp() {
    	if(current.prev!=null) {
    	current=current.prev;
    	currentLine--;
    	}
    	System.out.println("you are on first line");
    }
    /**
     * goes down one line
     */
    public void readDown() {
    	if(current.next!=null) {
    	current=current.next;
    	currentLine++;
    	}else {
    	System.out.println("you are on last line");
    	}
    }
    /**
     * goes to first line
     */
    public void goToFirstLine() {
    	current=front;
    	currentLine=1;
    }
    /**
     * goes to last line
     */
    public void goToLastLine() {
    	while(current.next!=null) {
    		current=current.next;
    		currentLine++;
    	}
    }
    /**
     * prints the String stored in the node pointed by current
     */
    public void printCurrent() {
    	//current=front;
    	System.out.println(current.data);
    }
    public String getCurrentString() {
    	return current.data.toString();
    }
    /**
     * prints the content of all nodes
     * @param toggle:if true the line number is also displayed
     */
    public void printAll(Boolean toggle) {
    	current=front;  
    	int lineNumber=1;
    	while(current!=null) {
    		if(toggle==true) {
    			System.out.print("("+lineNumber+")");
    		}
    		System.out.println(current.data);
    		current=current.next;
    		lineNumber++;
    	}
    	current=front;
    }
    /**
     * deletes the node current is pointing to 
     */
    public void deleteLine() {
    	remove(current.data);
    }
    
    /**
     * if no node exists it creates a new node and front points to it.
     * if nodes already exist it creates a new node and adds it at the beginning of the list
     * @param x:the data to store in the node
     */
    public void addFront(T x) {
        if (isEmpty())
            front = new ListNode<T>(x);
        else {
            ListNode<T> temp = front;
            front = new ListNode<T>(null, x, temp);
            front.next.prev = front;
        }
        size++;
    }
    /**
     * if no node exists it creates a new node and front points to it.
     * if nodes already exist it creates a new node and adds it at the end of the list
     * @param x:the data to store in the node
     */
    public void addEnd(T x) {
        if (isEmpty())
            front = new ListNode<T>(x);
        else {
            ListNode<T> temp = front;
            // Traverse till end of list
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = new ListNode<T>(temp, x, null);
        }
        current=front;
        size++;
    }
    /**
     * Adding node before another node
     *
     * @param x Value to look for, adding before x if found
     * @param y Value to add.
     */
    public void addBefore(T y) {
    	T x=current.data;
        // List is empty, can't add
        if (isEmpty())
            throw new NoSuchElementException("Element " + x.toString() + " not found");
        ListNode<T> current = front;
        // Looping through until found
        while (current != null && !current.data.equals(x))
            current = current.next;
        // If null, not found
        if (current == null)
            throw new NoSuchElementException("Element " + x.toString() + " not found");
        ListNode<T> newNode = new ListNode<T>(current.prev, y, current);
        if (current.prev != null)
            current.prev.next = newNode;
        else
            front = newNode;
        current.prev = newNode;
        size++;
    }
    /**
     * Adding node after another node
     *
     * @param x Value to look for, adding after x if found
     * @param y Value to add.
     */
    public void addAfter(T y) {
    	
    	T x=current.data;
        if (isEmpty())
            throw new NoSuchElementException("Element " + x.toString() + " not found");
        ListNode<T> current = front;
        // Looping through until found
        while (current != null && !current.data.equals(x))
            current = current.next;
        // If null, not found
        if (current == null)
            throw new NoSuchElementException("Element " + x.toString() + " not found");
        // Not null, value found
        ListNode<T> newNode = new ListNode<T>(current, y, current.next);
        if (current.next != null)
            current.next.prev = newNode;
        current.next = newNode;
        size++;
    }
    /**
     * Removing a Node from the list.
     *
     * @param x Value to remove
     */
    public void remove(T x) {
        if (isEmpty())
            throw new NoSuchElementException("Element " + x.toString() + " not found");
        // Removing front element
        if (front.data.equals(x)) {
            front = front.next;
            return;
        }
        ListNode<T> current = front;
        // Looping through until found
        while (current != null && !current.data.equals(x))
            current = current.next;
        // If null, not found
        if (current == null)
            throw new NoSuchElementException("Element " + x.toString() + " not found");
        // It has a next pointer, so not the last node.
        if (current.next != null)
            current.next.prev = current.prev;
        current.prev.next = current.next;
        size--;
    }
    /**
     * @return true if list is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }
    /**
     * @return size of list
     */
    public int size() {
        return size;
    }
    /**
     * returns string stored in a node
     */
    public String toString() {
    	current=front;
    	ListNode<T> temp = current;
    	StringBuilder builder = new StringBuilder();
    	builder.append(temp.data);
    	return builder.toString();
    }
   
}