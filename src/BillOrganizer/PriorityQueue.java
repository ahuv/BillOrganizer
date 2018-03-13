package BillOrganizer;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class PriorityQueue<T extends Comparable<T>> {
	
	
	private SortedLList<T> bills;
	private Comparator<T> comparator;
	
	/**
	 * The PriorityQueue constructor receives a reference to a 
	 * comparator that is used to correctly insert the data into the list.
	 * @param comp
	 */
	public PriorityQueue(Comparator<T> comp)
	{
		bills = new SortedLList<T>();
		comparator = comp;
	}
	
	/**
	 * The enqueue method delegates the job to the SortedLList
	 * and passes a reference to the comparator associated with the queue.
	 * @param data The bill to add
	 */
	public void enqueue(T data)
	{
		bills.insert(data, comparator);
	}
	
	/**
	 * The dequeue method returns the data in the first node and 
	 * removes that node from the SortedLList.
	 * @return bill The data in the first node
	 * @throws EmptyListException 
	 */
	public T dequeue() throws EmptyListException
	{
		if(bills.isEmpty())
		{
			throw new EmptyListException("Empty List");
		}
		
		T bill = bills.getFirst();
		
		bills.removeFirst();
		
		return bill;
		
	}
	
	/**
	 * The peek method returns the data in the first node.
	 * @return The data in the first node
	 * @throws EmptyListException 
	 */
	public T peek() throws EmptyListException
	{
		if(bills.isEmpty())
		{
			throw new EmptyListException("Empty List");
		}

		return bills.getFirst();
	}
	
	/**
	 * The remove method removed the node containing a reference to 
	 * the specified data.
	 * @param data The data contained in the node that will be removed
	 */
	public void remove(T data)
	{
		boolean remove = bills.remove(data);
		
		if(remove == false)
		{
			throw new NoSuchElementException("Could not remove");
		}
	}
	
	public Iterator<T> iterator()
	{
		return bills.iterator();
	}
	
	//check if SortedLList is empty
	public boolean isEmpty()
	{
		return bills.isEmpty();
	}

	
}


