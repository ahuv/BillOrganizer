package BillOrganizer;

import java.util.Comparator;

public class BillDateComparator implements Comparator<Bill> {
	
	public int compare(Bill bill, Bill other) 
	{
		return bill.getDateDue().compareTo(other.getDateDue());
	}

	
}