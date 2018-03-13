package BillOrganizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class BillOrganizer implements Serializable
{
	private SortedLList<Bill> bills = new SortedLList<Bill>();
	
	private PriorityQueue<Bill> billByDate;
	private PriorityQueue<Bill> billByCharge;
	private PriorityQueue<Bill> billByType;

	
	/**
	 * The constructor sets up a set of empty PriorityQueues
	 * organized by bill due date, bill amount, and bill type respectively.
	 */
	public BillOrganizer()
	{
		billByDate = new PriorityQueue<Bill>(new BillDateComparator());
		billByCharge = new PriorityQueue<Bill>(new BillAmountComparator());
		billByType = new PriorityQueue<Bill>(new BillTypeComparator());
	}
	
	/**
	 * The constructor reads in data from file and stores data in several instances of PriorityQueue.
	 * @throws IOException 
	 * @throws DuplicateDataException 
	 * @throws ClassNotFoundException 
	 */
	public BillOrganizer(String file) throws ClassNotFoundException, DuplicateDataException, IOException
	{
		
		billByDate = new PriorityQueue<Bill>(new BillDateComparator());
		billByCharge = new PriorityQueue<Bill>(new BillAmountComparator());
		billByType = new PriorityQueue<Bill>(new BillTypeComparator());
		
		Scanner scan = new Scanner(new FileReader(file));
		
		while (scan.hasNext())
		{
			Bill bill = new Bill(scan);
			
			bills.insert(bill);
			
			billByDate.enqueue(bill);
			billByCharge.enqueue(bill);
			billByType.enqueue(bill);
		}
		
		scan.close();
		
		Bill counter = bills.getLast();
		counter.count = counter.getBillID();
		
	}
	
	
	/**
	 * The constructor restores the SortedLList from a serialized file.
	 * Iterates through the SortedLList using an iterator and
	 * inserts each bill into each PriorityQueue using the correct Comparator.
	 * @throws IOException 
	 * @throws DuplicateDataException 
	 * @throws ClassNotFoundException 
	 */
	public BillOrganizer(String file, String path) throws IOException, ClassNotFoundException
	{
		
		billByDate = new PriorityQueue<Bill>(new BillDateComparator());
		billByCharge = new PriorityQueue<Bill>(new BillAmountComparator());
		billByType = new PriorityQueue<Bill>(new BillTypeComparator());
		
		String filename = file.concat(path);
		
		FileInputStream fileIn = new FileInputStream(filename);
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		bills = (SortedLList<Bill>) objIn.readObject();
		objIn.close();
		fileIn.close();
		
		for(Bill b : bills)
		{
			billByDate.enqueue(b);
			billByCharge.enqueue(b);
			billByType.enqueue(b);
		}
		
		Bill counter = bills.getLast();
		counter.count = counter.getBillID();
	}
	
	/**
	 * The insert method inserts a reference to the bill in each PriorityQueue and in the SortedLList.
	 * The bill may have a different position in each queue, depending on the comparator.
	 * @param bill The bill to add
	 * @throws DuplicateDataException 
	 */
	public void insert(Bill bill) throws DuplicateDataException
	{
		
		billByDate.enqueue(bill);
		billByCharge.enqueue(bill);
		billByType.enqueue(bill);
		bills.insert(bill);
		
	}
	
	/**
	 * The payNextBill method allows the user to pay his next bill, where "next"
	 * is dependent on the BillCriteria.
	 * The paid bill is removed from all queues as well as the SortedLList.
	 * @param criteria The BillCriteria that identifies how the user would like 
	 * 		  to pay bills - by date, charge, or bill type.
	 * @throws EmptyListException 
	 */
	public void payNextBill(BillCriteria criteria) throws EmptyListException
	{ 
		Bill nextBill;
		
		switch(criteria)
		{
		case BILLDUEDATE:
			
			nextBill = billByDate.dequeue();
			
			billByCharge.remove(nextBill);
			billByType.remove(nextBill);
			
			bills.remove(nextBill);
			
			break;
			
		case BILLAMOUNT:
			
			nextBill = billByCharge.dequeue();
			
			billByDate.remove(nextBill);
			billByType.remove(nextBill);
			
			bills.remove(nextBill);
			
			break;
			
		case BILLTYPE:
			
			nextBill = billByType.dequeue();
			
			billByDate.remove(nextBill);
			billByCharge.remove(nextBill);
			
			bills.remove(nextBill);
			
			break;
		}
		
	}
	
	/**
	 * The payNextBill method allows the user to pay a bill by specifying the bill ID.
	 * @param billID The bill ID specified by the user.
	 * @throws NoSuchElementException if the SortedLList does not contain a bill with the specified id number.
	 */
	public void payNextBill(int billID) throws NoSuchElementException
	{
		
		Iterator<Bill> iter = bills.iterator();
			
		while(iter.hasNext())
		{
			Bill bill = iter.next();
			
			if(iter.next().getBillID().compareTo(billID) == 0)
			{
				
				billByDate.remove(bill);
				billByCharge.remove(bill);
				billByType.remove(bill);
				bills.remove(bill);
				
			}
		}
		
		throw new NoSuchElementException("Could not find bill with specified ID.");

	}
	
	/**
	 * The IterateByDate method returns a LLIterator initialized with the head of the 
	 * PriorityQueue organized by date.
	 * @return an iterator for the queue organized by date
	 */
	public Iterator<Bill> IterateByDate()
	{
		return billByDate.iterator();
		
	}
	
	/**
	 * The IteratebyAmount method returns a LLIterator initialized with the head of the 
	 * PriorityQueue organized by amount.
	 * @return an iterator for the queue organized by amount
	 */
	public Iterator<Bill> IterateByAmount()
	{
		return billByCharge.iterator();
		
	}
	
	/**
	 * The IterateByType method returns a LLIterator initialized with the head of the 
	 * PriorityQueue organized by type.
	 * @return an iterator for the queue organized by type
	 */
	public Iterator<Bill> IterateByType()
	{
		return billByType.iterator();
		
	}
	
	/**
	 * The totalBills method sums the charge for all bills.
	 * @return total The total amount due for all bills combined.
	 */
	public double totalBills()
	{
		double total = 0;
		
		for(Bill b : bills)
		{
			total += b.getBillAmount();
		}
		
		return total;
		
	}
	
	/**
	 * The toString method provides a string containing information 
	 * for all bills in the SortedLList.
	 */
	public String toString()
	{
		StringBuffer info = new StringBuffer();
		
		for(Bill b : bills)
		{
			info.append(b.toString() + "\n");
		}
		
		return info.toString();
	}
	
	/**
	 * The closeOrganizer method creates a serialized file
	 * containing the information stored in the sortedLList of the BillOrganizer object.
	 * Will not allow a user to create a file with a duplicate filename.
	 * @param serFile The filename specified for the serialized file
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws DuplicateDataException 
	 */
	public boolean closeOrganizer(String serFile) throws FileNotFoundException, IOException
	{
		boolean saved = true;
		
		File file= new File(serFile);
		
		if(file.exists())
		{
			saved = false;
		}
		else
		{
			FileOutputStream fileOut = new FileOutputStream(serFile);
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(bills);
			objOut.close();
			fileOut.close();
		
			saved = true;
		}
		
		return saved;	
	}
	
	/**
	 * The closeOrganizer method creates a file
	 * containing the information stored in the sortedLList of the BillOrganizer object.
	 * Will not allow a user to create a file with a duplicate filename.
	 * @param serFile The filename specified for the serialized file
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws DuplicateDataException 
	 */
	public boolean closeOrganizer(String file, String path) throws FileNotFoundException, IOException
	{
		boolean saved = true;
		
		String filename = file.concat(path);
		
		File currFile = new File(filename);
		
		if(currFile.exists())
		{
			saved = false;
		}
		else
		{
			Writer writer = new FileWriter(currFile, false);
 		
			for(Bill myBill : bills)
			{
				LocalDate billDate = myBill.getDateDue();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				String date = billDate.format(formatter);
			
				writer.append("\n" + myBill.getVendName() + "\t" + myBill.getBillAmount() + 
							  "\t"  + date + "\t" + myBill.getBillType());
			}
		
			writer.close();
			
			saved = true;
		}
		
		return saved;
	}
	
	
}
