package mypackage;
/**
 * 
 * @author ioannidis christos 2018030006
 *
 * @param <T>
 */
public class ListNode<T> {
    // The actual data
    T data;
    // Reference to the next node
    ListNode<T> next;
    // Reference to the prev node
    ListNode<T> prev;
    /**
     * Constructor.
     * Note that the next and prev variables are set to null, thus this is the "root-node"
     *
     * @param data node data
     */
    ListNode(T data) {
        this(null, data, null);
    }
    /**
     * Constructor.
     *
     * @param data node data
     * @param next reference to next node
     * @param prev reference to the previous node
     */
    ListNode(ListNode<T> prev, T data, ListNode<T> next) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}