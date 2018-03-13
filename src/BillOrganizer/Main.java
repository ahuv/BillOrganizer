package BillOrganizer;

import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
	
	public static Scanner scan = new Scanner(System.in);
	
	public static void main (String []args)
	{
		BillOrganizer pile = new BillOrganizer();
		
		int choice;
		
		while(true)
		{
			choice = mainMenu();
			
			switch(choice)
			{
			case 0: //close application
				
				System.out.println("Ending application...");
				System.out.println("Billing application is closed.");
				System.exit(0);
				break;
				
			case 1: //restore from serialized file
				
				pile = readSerFile(pile);
				if(pile == null)
				{
					break;
				}
				doBillingAction(pile);
				break;
				
			case 2: //read from a text file
				
				pile = readFile(pile);
				if(pile == null)
				{
					break;
				}
				doBillingAction(pile);
				break;
				
			case 3: //start new billing organizer
				
				doBillingAction(pile);
				break;
				
			default:
				System.out.println("Invalid option. Please reenter your menu choice: ");
				break;
			}
		}
	}
	
	/**
	 * The readSerFile method reads a serialized file and 
	 * stores the information in the BillOrganizer.
	 * @param pile A BillOrganizer object containing billing information
	 * @return pile A BillOrganizer object containing billing information
	 */
	public static BillOrganizer readSerFile(BillOrganizer pile)
	{
		String name = "";
		String path = ".txt";
		
		System.out.println("Enter the name of the file you would like to restore: ");
		name = scan.next();
		
		String filename = name.trim();
		
		try 
		{
			pile = new BillOrganizer(filename, path);
		} 
		catch (ClassNotFoundException | IOException e) 
		{
			System.out.println("Error: could not find or read the specified file.");
			return null;
		}
		
		System.out.println("The following bill(s) have been retrieved: \n" + pile.toString());
		
		return pile;
		
	}
	
	/**
	 * The readFile method reads a  file and 
	 * stores the information in the BillOrganizer.
	 * @param pile A BillOrganizer object containing billing information
	 * @return pile A BillOrganizer object containing billing information
	 */
	public static BillOrganizer readFile(BillOrganizer pile)
	{
		
		String file;
		String path = ".txt";
		
		System.out.println("Enter the name of the file currently storing billing information: ");
		file = scan.next();
		file.trim();
		
		String filename = file.concat(path);
		
		try 
		{
			pile = new BillOrganizer(filename);
		} 
		catch (ClassNotFoundException | DuplicateDataException | IOException e) 
		{
			System.out.println("Error: could not find the specified file.");
			return null;
		}
		catch(InputMismatchException e)
		{
			System.out.println("Error: could not read the file. Please check the file contents.");
			return null;
		}
		
		System.out.println("The following bill(s) have been retrieved: \n" + pile.toString()); 
		
		return pile;
	}
	
	/**
	 * The doBillingAction method allows the user to perform an action on his bills,
	 * such as get total sum, pay bill, add bill, organize bills.
	 * @param pile A BillOrganizer object containing billing information
	 */
	public static void doBillingAction(BillOrganizer pile)
	{
		int choice;
		int saveChoice;
		int viewChoice;
		int addChoice;
		int payChoice;
		
		while(true)
		{
			choice = billingMenu();
			
			switch(choice)
			{
			case -1: //return to main menu

				return;
				
			case 0: //close organizer - creates serialized file
				
				saveChoice = saveBillsMenu();
				saveBills(saveChoice, pile);
				
				System.out.println("Ending application...");
				System.out.println("Billing application is closed.");
				System.exit(0);
				break;
				
			case 1:	//view all bills
				viewChoice = viewBillsMenu();
				viewBills(viewChoice, pile);
				break;
				
			case 2:	//view total bill charges
				double total = pile.totalBills();
				System.out.println("Current total charges of all bills is $" + total);
				break;
				
			case 3:	//add new bill
				pile = addBill(pile);
				break;
				
			case 4:	//pay bill
				payChoice = payNextMenu();
				payBill(payChoice, pile);
				break;
				
			default: //if user choice is not an option
				System.out.println("Invalid option. Please reenter your menu choice: ");
				break;
			}//end switch
		}//end while
	
	}//end method
	
	/**
	 * The mainMenu method provides the user with options to initiate the program
	 * and set up a BillOrganizer object.
	 * @return int choice The number referencing the user's choice.
	 */
	public static int mainMenu()
	{
		int choice;
		
		System.out.println("\nHow would you like to start organizing your bills?" +
							"\n1. Open a serialized file" +
							"\n2. Open a text file " + 
							"\n3. Start a new pile of bills" +
							"\n0. Exit application");
		
		choice = scan.nextInt();
		return choice;	
	}
	
	/**
	 * The billingMenu method provides the user with options of actions he
	 * may perform on bills.
	 * @return int choice The number referencing the user's choice.
	 */
	public static int billingMenu()
	{
		int choice;
		
		System.out.println("\nWhat would you like to do with your bills?" +
							"\n 1. Organize (sort) bills" +
							"\n 2. View total bill charges" +
							"\n 3. Add new bill" +
							"\n 4. Pay bill" +
							"\n 0. Close Bill Organizer (will save current bills to a file)" +
							"\n-1. Return to main menu");
		
		choice = scan.nextInt();
		return choice;
	}
	
	/**
	 * The saveBillsMenu method provides the user with several options
	 * for ways he may save his current bills.
	 * @return int choice The number referencing the user's choice.
	 */
	public static int saveBillsMenu()
	{
		int choice; 
		
		System.out.println("Where would you like to save your bills?" + 
							"\n1. Text file" +
							"\n2. Serialized file ");
		
		choice = scan.nextInt();
		return choice;
	}
	
	/**
	 * The viewBillsMenu method provides the user with several options
	 * for ways he may organize  and view his current bills.
	 * @return int choice The number referencing the user's choice.
	 */
	public static int viewBillsMenu()
	{
		int choice; 
		
		System.out.println("How would you like to sort your bills?" + 
							"\n1. By bill ID" +
							"\n2. By date " +
							"\n3. By amount" +
							"\n4. By bill type");
		
		choice = scan.nextInt();
		return choice;
	}
	
	/**
	 * The payNextMenu method provides the user with several options
	 * for choosing which bill he wants to pay off next.
	 * @return int choice The number referencing the user's choice.
	 */
	public static int payNextMenu()
	{
		int choice; 
		
		System.out.println("How would you pay your next bill?" +
							"\n1. Specify bill ID" +
							"\n2. By soonest due" +
							"\n3. By largest amount" +
							"\n4. Select bill type");
		
		choice = scan.nextInt();
		return choice;
	}

	
	/**
	 * The selectBillType method provides the user with a list 
	 * of all possible billTypes that may be associated with a bill.
	 * @return int choice The number referencing the user's choice.
	 */
	public static int selectBillType()
	{
		int choice; 

		do{
		System.out.println("Select the bill type: ");
		System.out.println(  "1. CLOTHING" + 
						   "\n2. EDUCATION" +
						   "\n3. FOOD" + 
						   "\n4. GROCERIES" +
						   "\n5. PHONE" + 
						   "\n6. TRAVEL" +
						   "\n7. UTILITES");
		
		choice = scan.nextInt();
		}while(choice < 1 || choice > 7);
			
		return choice;
	}
	
	/**
	 * The getBillType method retrieves the billType that the 
	 * user has chosen to associate with the current bill.
	 * @param int choice The number referencing the user's choice.
	 * @return BillType type The BillType of the current bill
	 */
	public static BillType getBillType(int choice)
	{
		BillType type = null;
		
		switch(choice)
		{
		case 1:
			type = BillType.CLOTHING;
			break;
		case 2:
			type = BillType.EDUCATION;
			break;
		case 3:
			type = BillType.FOOD;
			break;
		case 4:
			type = BillType.GROCERIES;
			break;
		case 5:
			type = BillType.PHONE;
			break;
		case 6:
			type = BillType.TRAVEL;
			break;
		case 7:
			type = BillType.UTILITIES;
			break;
		default:
			System.out.println("Invalid option. Please reenter your menu choice: ");
			break;
		}//end switch
		
		return type;
	}
	
	/**
	 * The addBill method adds a bill to the current list of bills.
	 * @param pile A BillOrganizer object containing billing information
	 * @return pile A BillOrganizer object containing billing information
	 */
	public static BillOrganizer addBill(BillOrganizer pile)
	{
		
		String name;
		double amount;
		String date;
		BillType type;
		
		int chooseType;
		
		Bill bill = null;
		
		scan.nextLine();
		System.out.println("Enter the name of bill vendor: ");
		name = scan.nextLine();
		System.out.println("Enter the amount due: ");
		amount = scan.nextDouble();
		System.out.println("Enter the date due (use format MM/dd/yyyy): ");
		date = scan.next();
		
		chooseType = selectBillType();
		type = getBillType(chooseType);
			
		try 
		{
			bill = new Bill(name, amount, date, type);
		} 
		catch (ParseException | DateTimeParseException e) 
		{
			System.out.println("Error: Date must be entered int the following format: MM/dd/yyyy");
		}
			
		try
		{
			pile.insert(bill);	
		}
		catch(DuplicateDataException e)
		{
			System.out.println("Error: Cannot insert bill because of duplicate bill ID.");
		}
		
		
		return pile;
			
	}
	
	/**
	 * The payBill method pays the next bill; "next" is relative - it depends
	 * on how the user has chosen to define next (soonest due, largest amount, etc).
	 * @param int choice The number referencing the user's choice.
	 * @param pile A BillOrganizer object containing billing information
	 */
	public static void payBill(int choice, BillOrganizer pile)

	{
		int id;
		boolean paid;
		
		switch(choice)
		{
		case 1: //pay by bill ID
			
			System.out.println("Please enter the bill ID of the bill you would like to pay: ");
			try{
				id = scan.nextInt();
				pile.payNextBill(id);
			}
			catch(NoSuchElementException e)
			{
				System.out.println("Error: there is no bill currently associated with the specified id number.");
			}
			break;
			
		case 2: //pay by date due
			
			try
			{
				pile.payNextBill(BillCriteria.BILLDUEDATE);
			}
			catch(EmptyListException e)
			{
				System.out.println("Error: cannot pay bill from an empty pile.");
			}
			break;
			
		case 3: //pay by amount
			
			try
			{
				pile.payNextBill(BillCriteria.BILLAMOUNT);
			}
			catch(EmptyListException e)
			{
				System.out.println("Error: cannot pay bill from an empty pile.");
			}
			break;
			
		case 4: //pay by bill type
			
			try
			{
				pile.payNextBill(BillCriteria.BILLTYPE);
			}
			catch(EmptyListException e)
			{
				System.out.println("Error: cannot pay bill from an empty pile.");
			}
			break;
			
		default:
			System.out.println("Invalid option. Please reenter your menu choice: ");
			break;
		}//end switch
	}
	
	/**
	 * The viewBills method iterates through a queue to display all current bills.
	 * The queue to be iterated through is dependent on how the user has chosen
	 * to organize the bills.
	 * @param int choice The number referencing the user's choice.
	 * @param pile A BillOrganizer object containing billing information
	 */
	public static void viewBills(int choice, BillOrganizer pile)
	{
		Iterator<Bill> iter;
		
		switch(choice)
		{
		case 1: //view by id
			
			System.out.println(pile.toString());
			break;
			
		case 2: //view by date
			
			iter = pile.IterateByDate();
			
			while(iter.hasNext())
			{
				System.out.println(iter.next());
			}
			break;
			
		case 3: //view by amount
			
			iter = pile.IterateByAmount();
			
			while(iter.hasNext())
			{
				System.out.println(iter.next());
			}
			break;
			
		case 4: //view by type
			
			iter = pile.IterateByType();
			
			while(iter.hasNext())
			{
				System.out.println(iter.next());
			}
			break;
			
		default:
			System.out.println("Invalid option. Please reenter your menu choice: ");
			break;
		}//end switch
	}
	
	/**
	 * The saveBills method saves the information contained in the BillOrganizer object,
	 * either to a text file or serialized file, depending on the user's choice.
	 * @param pile A BillOrganizer object containing billing information
	 */
	public static void saveBills(int choice, BillOrganizer pile)
	{
		String file;
		String path = ".txt";
		
		boolean saved = true;
		
		switch(choice)
		{
		case 1: //text file
			
			do
			{
				System.out.println("\nEnter the name of the file to save it to: ");
				file = scan.next();
				String filename = file.trim();
				
				try 
				{
					saved = pile.closeOrganizer(filename, path);
				}
				catch (IOException e) 
				{
					System.out.println("Error: could not create file.");
				}
				
				if(saved)
				{
					System.out.println("File saved successfully.");
					break;
				}
			
				System.out.println("Error: duplicate filename.");
		
			}while(saved == false);
			
			break;
			
		case 2: //ser file
			
			do
			{
				System.out.println("\nEnter the name of the file to save it to: ");
				file = scan.next();
				String filename = file.trim();
				filename = file.concat(path);
				
				try 
				{
					saved = pile.closeOrganizer(filename);
				} 
				catch (IOException e) 
				{
					System.out.println("Error: could not create a serialized file.");
				}
				
				if(saved)
				{
					System.out.println("File saved successfully.");
					break;
				}
				
				System.out.println("Error: duplicate filename.");
		
			}while(saved == false);
			
			break;
			
		default:
			
			System.out.println("Invalid option. Please reenter your menu choice: ");
			break;
		}
		
		
		
	}
}



