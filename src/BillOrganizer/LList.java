package BillOrganizer;

import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

//this is an unsorted linked list, doesn't check for duplicates
public class LList<T extends Comparable<T> > implements Iterable<T>, Serializable {

	protected LLNode<T> head;
	protected int modCount = 0;

	public LList()
	{
	  head = null;
	}

	public boolean isEmpty()
	{
	  return (head==null)? true : false;
	}

	public void addFirst(T data)
	{
	  //place reference to data into a new Node
	  LLNode<T> newNode = new LLNode<T>(data);
	  modCount++;
	  
	  if (head == null)
	  {
		  head = newNode;
		  return;
	  }
	  else
	  {
		  //newNode will placed before the current head
		  //so it will be linked to the current head
		  newNode.setNext(head);
		  //reset head to reference the newNode
		  head = newNode;
		  return;
	  }

	}

	public void insert(T data) throws DuplicateDataException
	{
	  //set up a new Node that references this data
	  LLNode<T> newNode = new LLNode<T>(data);
	  modCount++;
	  //if the linked list is empty , place this node at the head
	  if (head == null)
	  {
		  head = newNode;
	  }
	  else
	  {
		  //must iterate through the list to find the last node
		  LLNode<T> currentNode = head;
		  LLNode<T> previousNode = head;
		  while (currentNode != null){
			  previousNode = currentNode;
			  currentNode = currentNode.getNext();
		  }
		  //found the end of the list
		  previousNode.setNext(newNode);
	  }

	}


	public void add(int index, T data)throws IndexOutOfBoundsException
	{
	  //set up new node that references the data being inserted
	  LLNode<T> newNode = new LLNode<T>(data);
	  
	  int count=0;
	  LLNode<T> current = head;
	  LLNode<T> previous = head;
	  
	  while (current !=null && count <= index )
	  {
		  if (count == index)
		  {
			  //data must be inserted here
			  newNode.setNext(current);  
			  if (current == head)
			  {
				  head = newNode;  //reset the head
				  modCount++;
				  return;  //job done
			  }
			  else
			  {
				  //place new node in between two existing nodes
			      previous.setNext(newNode);
			      modCount++;
			      return;
			  }
		  }
		  else
		  {
			  //must move further down the list
			  count++;
			  previous = current;
			  current = current.getNext();
		  }
	  }
		 throw new IndexOutOfBoundsException();

	}





	public T getFirst ()throws NoSuchElementException
	{
	  if (head == null)
	  {
		  throw new NoSuchElementException();
	  }
	  else
	  {
		  return head.getData();
	  }

	}


	public T getLast()throws NoSuchElementException
	{
	  //must first find the last Node in the list
	  LLNode<T> current = head;
	  LLNode<T> previous = head;
	  
	  if (head == null)
	  {
		  //list is empty
		   throw new NoSuchElementException();
	  }
	  while(current.getNext() != null)
	  {
		  //this Node is not the last one, move along in the list
		  previous = current;
		  current = current.getNext();
	  }
	  return current.getData();

	}


	public T get(int index)throws NoSuchElementException
	{
	  LLNode<T> current = head;
	  LLNode<T> previous = head;
	  int count =0;
	  while (current != null && count <= index)
	  {
		  if (count == index)
		  {
			  return current.getData();
		  }
		  else
		  {
			  //must move along
			  previous = current;
			  current = current.getNext();
			  count++;
		  }
	  }
	  throw new NoSuchElementException();

	}


	public void removeFirst()throws NoSuchElementException
	{
	  if (head != null)
	  {
		  modCount++;
		  head = head.getNext();  //remove the reference to the first Node
	  }
	  else
	  {
		  throw new NoSuchElementException();
	  }

	}


	public void removeLast()throws NoSuchElementException
	{
	  if (head == null)
	  {
		  throw new NoSuchElementException();
	  }
	  else
	  {
		  LLNode<T> current = head;
		  LLNode<T> previous = head;
		  while (current.getNext() != null)
		  {
			  //not the last node yet, move along
			  previous = current;
			  current = current.getNext();
			  
		  }
		  //now reached the last
		  //check if the last node is the head of the list
		  if (current == head)
		  {
			  head = null;  //the list is now empty
		  }
		  else
		  {
			  //remove the link to the last node.
			  previous.setNext(current.getNext()); //remove the last node
		  }
		  modCount++;
		  return;
	  }

	}


	public boolean remove(T data)
	{
	  //find the Node that contains the data and remove it from the list
	  if (head == null)
	  {
		 return false;  //there was no data to remove
	  }
	  LLNode<T> currentNode = head;
	  LLNode<T> previousNode = head;
	  while (currentNode != null)
	  {
		  if (currentNode.getData().compareTo(data)==0)
		  {
			   //found the data, reset links
            if (currentNode == head)
            {
				  //have to reset head
				  head = currentNode.getNext();
			}
            else
            {    //somewhere in the middle
			     previousNode.setNext(currentNode.getNext());
            }
			  modCount++;
			  return true;   //job well done!
		  }
		  previousNode = currentNode;
		  currentNode = currentNode.getNext(); //proceed to next Node in the list
	  }
	  //didn't find the data
	  return false;

	} 


   public int size()
   {
  	 int count =0;
  	 LLNode<T> current = head;
  	 LLNode<T> previous = head;
  	 
  	 if (head == null)
  	 {
  		 return 0;
  	 }
  	 else
  	 {
  		while (current != null)
  		{
  			count++;
  			previous = current;
  			current = current.getNext();
  		}
  		return count;
  	 }
   }
  	
  	public String toString()
  	{
  		StringBuffer info = new StringBuffer("[ ");
  		LLNode<T>curr = head;
  		while (curr != null)
  		{
  			//curr is referencing a Node
  			info.append(curr.getData().toString() + " ");
  		    curr = curr.getNext();
  		}
  		info.append(" ]");
  		return info.toString();
  	}
  	
    
    
	  
    public Iterator<T> iterator()
    {
   	 	return new LLIterator();
    }
    
 
     class LLIterator implements Iterator<T>
     {
   		
   		protected LLNode<T> current;
   		protected boolean removeCalled;
   		
   		protected LLNode<T> previous;
   		protected LLNode<T> beforeprevious;
   		
   		protected int expectedModCount;
   		
   		public LLIterator()
   		{
   			//initialize variables
   			this.removeCalled = false;
   		    this.current =  head;
   			this.previous = null;   //doesn't point anywhere yet
   			this.beforeprevious = null; //doesn't point anywhere yet
   		    this.expectedModCount = modCount;  //to ensure the list stays the same 
   		                                       //during the iteration process
   		}
   		
   		public boolean hasNext()
   		{
   			if (expectedModCount != modCount)
   			{
   				throw new ConcurrentModificationException();
   			}
   			if (current !=null)
   			{
   				return true;
   			}
   			else
   			{
   				return false;
   			}
   		}
   		
   		public T next()
   		{
   			if (expectedModCount != modCount)
   			{
   				throw new ConcurrentModificationException();
   			}
   			
   			if (hasNext())
   			{
   			 
   			  T currentData = current.getData(); //data that will be returned
   			  
   			  beforeprevious = previous;
   			  previous = current;
   			  current = current.getNext();  //move to next Node in the chain, get ready to point to the 
   			                                //next data item in the list
   			  
   			  this.removeCalled = false;	//now pointing to next value in the list
   			                                //which is not the one that may have been removed
   			  return currentData; 
   			}
   			else
   			{
   				throw new NoSuchElementException();
   			}
   		}
   		
   		public void remove()
   		{
   			if (expectedModCount != modCount)
   			{
   				throw new ConcurrentModificationException();
   			}

   			if (this.removeCalled || previous == null)
   			{
   				throw new IllegalStateException();
   			}
   		    
   		    else
   		    {
   				
   				//remove the data that is referenced by the first node, special case
   				if (previous == head)
   				{
   					//must reset head 
   					head = previous.getNext();
   					previous = null;//there is no Node in front of the new head of the list
   					
   				}
   				else
   				{
   					//remove somewhere in the middle of the list
   					//have to set the previous node 
   					previous = beforeprevious;
   					beforeprevious.setNext(current);
   				}
   			 
   				this.removeCalled = true;  //we are in the process of removing an element
   				
   			}
   			
   		}

   	}
}
