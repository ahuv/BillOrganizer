package BillOrganizer;

import java.util.Comparator;

public class BillTypeComparator implements Comparator<Bill> {
	
	
	public int compare(Bill bill, Bill other) 
	{
		return bill.getBillType().compareTo(other.getBillType());
	}
	
}


