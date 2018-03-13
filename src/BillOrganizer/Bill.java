package BillOrganizer;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class Bill implements Comparable<Bill>, Serializable{
	
	private String vendName;
	private Double amountDue;
	private LocalDate dateDue;
	private BillType billType;
	private Integer billID;
	
	protected static int count = 0;
	
	/**
	 * The overloaded Bill constructor creates a new bill
	 * by reading in billing information from a file.
	 *  @param scan The file scanner that is reading the file containing the bills
	 * @throws ParseException 
	 */
	public Bill(Scanner scan) throws ClassNotFoundException, IOException 
	{
		
		this.vendName = scan.next();
		this.amountDue = scan.nextDouble();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		this.dateDue = LocalDate.parse(scan.next(),formatter);
		this.billType = BillType.valueOf(scan.next());
		billID = ++count;
	}

	/**
	 * The overloaded Bill constructor creates a new bill
	 * by reading in billing information provided by the user.
	 * @param name The name of the bill
	 * @param amount The amount due for the bill
	 * @param date The date that the bill is due to be paid by
	 * @param type The BillType (enum) that the bill is classified as
	 * @throws ParseException 
	 */
	public Bill(String name, double amount, String billDate, BillType type) throws ParseException
	{
		
		this.vendName = name;
		this.amountDue = amount;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		this.dateDue = LocalDate.parse(billDate,formatter);
		this.billType = type;
		billID = ++count;
	}

	/**
	 * The compareTo method compared two bills based on the billIDs.
	 * @param other The bill that is being compared
	 * @return an int, depending on whether the bill is greater than, equal to, or less than
	 * the other bill
	 */
	public int compareTo(Bill other) 
	{
		return this.billID.compareTo(other.billID);
	} 
	
	public String getVendName()
	{
		return vendName;
	}
	
	public Integer getBillID()
	{
		return billID;
	}

	public Double getBillAmount() 
	{
		return amountDue;
	}

	public LocalDate getDateDue() 
	{
		return dateDue;
	}

	public BillType getBillType() 
	{
		return billType;
	}

	/**
	 * The String method returns a formatted string containing
	 * all of the information pertaining to the bill.
	 */
	public String toString()
	{
		String info = String.format("Bill  %-6d %-16s %8.2f %14s %12s", billID, vendName, amountDue, dateDue, billType);
		
		return info;
	}

}
