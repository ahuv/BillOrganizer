package BillOrganizer;

import java.util.Comparator;

public class BillAmountComparator implements Comparator<Bill> {
	
	
	public int compare(Bill bill, Bill other) 
	{
		return other.getBillAmount().compareTo(bill.getBillAmount());
	}
	
}
