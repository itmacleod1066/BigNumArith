/**
 * Node Class taken from DSA
 * @author Ian MacLeod
 * @Date 9/26/21
 * @param <E> Generic type definition
 */
public class Node<E> {
	
	//private instance variables
	private E data;
	private Node<E> next;
	
	/**
	 * Non-Default Constructor
	 * @param data the data held by the node
	 * @param next pointer to the next node
	 */
	public Node(E data, Node<E> next) {
		this.data = data;
		this.next = next;
	}
	
	/**
	 * Non-Default Construcotr that initializes the data field to null
	 * @param next pointer to the next node
	 */
	public Node(Node<E> next) {
		data = null;
		this.next = next;
	}
	
	/**
	 * Setter method that sets an element to a node
	 * @param element element to be set
	 * @return the element that was set
	 */
	public E setElement(E element) {
		return this.data = element;
	}
	
	/**
	 * Setter method that sets a nodes next value
	 * @param next The next node to be pointed to
	 * @return the next node
	 */
	public Node<E> setNext(Node<E> next) {
		return this.next = next;
	}
	
	/**
	 * Getter method that returns the node's data
	 * @return The node's data
	 */
	public E getElement() {
		return this.data;
	}
	
	/**
	 * Getter method that returns the node's next
	 * @return the node's next
	 */
	public Node<E> next(){
		return this.next;
	}

}
