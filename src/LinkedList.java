import java.util.NoSuchElementException;

/**
 * LinkedList Implementation Taken From DSA
 * @author Ian MacLeod
 * @Date 9/26/21
 * @param <E> Generic Type Definition
 */
public class LinkedList<E> implements List<E>{
	
	//instance variables
	private Node<E> head;
	private Node<E> tail;
	private Node<E> curr;
	private int listSize; 
	
	/**
	 * Constructor
	 * @param size size of linkedlist
	 */
	public LinkedList(int size) {
		this();
	}
	
	/**
	 * Constructor that calls the clear method upon instantiation
	 */
	public LinkedList() {
		clear();
	}

	@Override
	/**
	 * Method that clears the linkedlist
	 */
	public void clear() {
		curr = tail = new Node<E>(null);
		head = new Node<E>(tail);
		listSize = 0;
	}

	@Override
	/**
	 * Method that inserts a new node at the current location
	 * @param it element to set
	 * @return boolean true 
	 */
	public boolean insert(E it) {
		curr.setNext(new Node<E>(curr.getElement(), curr.next()));
		curr.setElement(it);
		if(tail == curr) {
			tail = curr.next();
		}
		listSize++;
		return true;
	}

	@Override
	/**
	 * Method that appends a new node to the end
	 * @param it element to append
	 * @return true
	 */
	public boolean append(E it) {
		tail.setNext(new Node<E>(null));
		tail.setElement(it);
		tail = tail.next();
		listSize++;
		return true;
	}

	@Override
	/**
	 * Method that removes an element from the linkedlist
	 * @throws NoSuchElementException for when there is no element to return
	 * @returns E the element removed
	 */
	public E remove() throws NoSuchElementException {
		if(curr == tail) throw new NoSuchElementException();
		E element = curr.getElement();
		curr.setElement(curr.next().getElement());
		if(curr.next() == tail) tail = curr;
		curr.setNext(curr.next().next());
		listSize--;
		return element;
	}

	@Override
	/**
	 * Moves the current point to head
	 */
	public void moveToStart() {
		curr = head.next();
		
	}

	@Override
	/**
	 * Moves the current pointer to tail
	 */
	public void moveToEnd() {
		curr = tail;
		
	}

	@Override
	/**
	 * Sets the current pointer to one node before the old current
	 */
	public void prev() {
		if(curr == head.next()) return;
		Node<E> prev = head;
		while(prev.next() != curr) {
			prev = prev.next();
		}
		
		curr = prev;
		
	}

	@Override
	/**
	 * Sets the current pointer to the current pointers next node
	 */
	public void next() {
		curr = curr.next();
		
	}

	@Override
	/**
	 * Method that returns the listsize of the linkedlist
	 * @return int size of the linkedlist
	 */
	public int length() {
		return listSize;
	}

	@Override
	/**
	 * Method that returns the numerical position of curr
	 * @return the numerical positon of curr
	 */
	public int currPos() {
		Node<E> temp = head.next();
		int i;
		for(i = 0; curr != temp; i++) {
			temp = temp.next();
		}
		return i;
	}

	@Override
	/**
	 * Method that moves the current pointer to the specified location
	 * @param pos the position to move to
	 * @boolean returns true
	 */
	public boolean moveToPos(int pos) {
		if(pos < 0 || pos > listSize) {
			return false;
		}
		curr = head.next();
		for(int i = 0; i < pos; i++) {
			curr = curr.next();
		}
		return true;
	}

	@Override
	/**
	 * Method that says whether or not the current position is at the tail
	 * @return boolean true or false
	 */
	public boolean isAtEnd() {
		return curr == tail;
	}

	@Override
	/**
	 * Method that gets the current pointers value
	 * @throws NoSuchElementException if there is no element to return
	 * @return E the current node's element
	 */
	public E getValue() throws NoSuchElementException {
		if(curr == tail) {
			throw new NoSuchElementException();
		}
		
		return curr.getElement();
	}

	@Override
	/**
	 * Returns true or false based on whether or not the linkedlist is empty
	 * @return boolean true or false
	 */
	public boolean isEmpty() {
		return listSize == 0;
	}
	
	/**
	 * Method that prints the content of a linkedlist
	 */
	public void print() {
		Node<E> temp = head.next();
		while(temp.next() != null) {
			System.out.print(temp.getElement());
			temp = temp.next();
		}
	}
	
	/**
	 * Method that returns the current node
	 * @return the current node
	 */
	public Node<E> getCurr(){
		return curr;
	}

}
