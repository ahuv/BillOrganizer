package BillOrganizer;

import java.io.Serializable;

public class LLNode <T extends Comparable<T>> implements Serializable
{
	
	private T data;
	private LLNode<T> next;
	
	public LLNode(T data)
	{
		this.data = data;
		this.next = null;
	}
	
    public T getData()
    {
    	return data;
    }
    public LLNode<T> getNext()
    {
    	return next;
    }
    public void setNext(LLNode<T> nextNode)
    {
    	this.next = nextNode;
    }

	public int compareTo(LLNode<T> currNode) 
	{
		return this.data.compareTo(currNode.data);
	}
    
}

