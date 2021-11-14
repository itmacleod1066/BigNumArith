import java.util.NoSuchElementException;

/**
 * List Interface Definition taken from DSA
 * @author Ian MacLeod
 * @Date 9/26/21
 * @param <E> Generic Type
 */
public interface List<E> {
	
	public void clear();

	// Insert "it" at the current location
	// The client must ensure that the list's capacity is not exceeded
	public boolean insert(E it);

	// Append "it" at the end of the list
	// The client must ensure that the list's capacity is not exceeded
	public boolean append(E it);

	// Remove and return the current element
	public Object remove() throws NoSuchElementException;

	// Set the current position to the start of the list
	public void moveToStart();

	// Set the current position to the end of the list
	public void moveToEnd();

	// Move the current position one step left, no change if already at beginning
	public void prev();

	// Move the current position one step right, no change if already at end
	public void next();

	// Return the number of elements in the list
	public int length();

	// Return the position of the current element
	public int currPos();

	// Set the current position to "pos"
	public boolean moveToPos(int pos);

	// Return true if current position is at end of the list
	public boolean isAtEnd();

	// Return the current element
	public Object getValue() throws NoSuchElementException;
	
	// Return whether or not the LinkedList is empty
	public boolean isEmpty();

}
