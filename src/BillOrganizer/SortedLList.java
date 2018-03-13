package BillOrganizer;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.NoSuchElementException;


public class SortedLList<T extends Comparable<T>> extends LList<T>{

	
	/**
	 * The insert method inserts a new bill into a list of bills
	 * in order, by comparing the billID of each bill.
	 * @param data The bill to insert into the list
	 * @throws DuplicateDataException
	 */
	@Override
	public void insert(T data) throws DuplicateDataException
	{
		LLNode<T> newNode = new LLNode<T> (data);
		  
		  //if list is empty
		  if (head == null)
		  {
			  head = newNode;
		  }
		  
		  else
		  {
			  LLNode<T> prev = head;
			  LLNode<T> curr = head;
			  
			  while(curr != null)
			  {
				  //if data is the same as the current node, throw exception
				  if (data.compareTo(curr.getData()) == 0)
				  {
					  throw new DuplicateDataException("Duplicate data!");
				  }
				  //if data is less than the current node, insert before current node
				  else if (data.compareTo(curr.getData())< 0)
				  {
					  //insert new node here
					  newNode.setNext(curr);
					  
					  //if current node is the head
					  if(head == curr)
					  {
						  head = newNode;
						  return;
					  }
					  
					  //if current node is not head
					  else
					  {
						  prev.setNext(newNode);
						  return;
					  }
				  }
				  //if data is greater than the current node, continue iterating
				  else
				  {
					  prev = curr;
					  curr = prev.getNext();
					  
					  //if current node is the last
					  if(curr == null)
					  {
						  prev.setNext(newNode);
					  }
				  }
			  }
		 }
	}	
	
	
	/**
	 * The overloaded insert method inserts a bill into a linked list.
	 * The link list is sorted according the the comparator that is passed to the method.
	 * @param data The bill to insert into the list
	 * @param comparator The comparator used to sort the list
	 * @throws DuplicateDataException
	 */
	public void insert(T data, Comparator<T> comparator)
	{

		  LLNode<T> newNode = new LLNode<T> (data);
		  
		  //if list is empty
		  if (head == null)
		  {
			  head = newNode;
		  }
		  
		  else
		  {
			  LLNode<T> prev = head;
			  LLNode<T> curr = head;
			  
			  while(curr != null)
			  {
				  //if data is less than or equal to the current node, insert before current node
				  if ((comparator.compare( data, curr.getData() )) <= 0)
				  {
					  //insert new node 
					  newNode.setNext(curr);
					  
					  //if current node is the head
					  if(head == curr)
					  {
						  head = newNode;
						  return;
					  }
					  
					  //if current node is not head
					  else
					  {
						  prev.setNext(newNode);
						  return;
					  }
				  }
				  //if data is greater than the current node, continue iterating
				  else if((comparator.compare( data, curr.getData() )) > 0)
				  {
					  prev = curr;
					  curr = prev.getNext();
					  
					  //if current node is the last
					  if(curr == null)
					  {
						  prev.setNext(newNode);
					  }
				  }
				  
			  }//end while
		 }//end else
	}	
	
	/**
	 * The find method checks if there is a node in the list referencing information matching to 
	 * information specified by the user. 
	 * @param data Information specified by the user.
	 * @return data referenced by the node if found matching data, else throw exception.
	 * @throws NoSuchElementException
	 */
	public T find(T data) throws NoSuchElementException
	{
		LLNode<T> newNode = new LLNode<T>(data);
		
		LLNode<T> curr = head;
		LLNode<T> prev = head;
		  
		while (curr !=null)
		{
			//if found data
			if(newNode.compareTo(curr) == 0)
			{
				break;
			}
			//if not found, continue checking list
			else
			{
				prev = curr;
				curr = curr.getNext();
				
				//if reach last node and data has not been found
				//throw exception
				if(curr == null & !(newNode.compareTo(curr) == 0))
				{
					throw new NoSuchElementException("Data not present in list");
				}
			}
		}
		return newNode.getData();
	}
	
	/**
	 * The toString method prints a String for each node in the current list.
	 */
	public String toString()
	{
		StringBuffer info = new StringBuffer("[ ");
		LLNode<T> curr = head;
		while (curr != null)
		{
			info.append("\n" + curr.getData().toString());
		    curr = curr.getNext();
		}
		info.append(" ]");
		return info.toString();
	}
}
