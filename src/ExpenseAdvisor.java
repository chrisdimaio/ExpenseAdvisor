import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;
import java.sql.Statement;
import java.util.Map;
import java.util.HashMap;

public class ExpenseAdvisor
{
	private static final int CALCULATE = 1;
	private static final int ENTER_EXPENSE = 2;
	private static final int ENTER_INCOME = 3;
	private static final int VIEW_DATA = 4;
	private static final int RESET_DATABASE = 5;
	private static final int EXIT = 0;

	Map<String, String> debits = new HashMap<String, String>();

	public static void main(String args[])
	{
		System.out.println("This is Expense Advisor.");
		
		double pastExpenses 	= 0;
		double futureExpenses 	= 0;
		
		DBManager dbManager = new DBManager("db.db");
		// dbManager
		Scanner inputScanner = new Scanner(System.in);
		
		ResultSet test = dbManager.getIncomes();
		
		boolean loop = true;
		while (loop)
		{
			System.out.println(CALCULATE + ": To calculated needed balance");
			System.out.println(ENTER_EXPENSE + ": To enter expense");
			System.out.println(ENTER_INCOME+ ": To enter income");
			System.out.println(VIEW_DATA + ": To view data");
			System.out.println(RESET_DATABASE + ": Reset database");
			System.out.println(EXIT + ": To exit");
		
			int option = inputScanner.nextInt();
			switch(option)
			{
				case EXIT:
					System.out.print("Exiting...");
					loop = false;
					break;
				case CALCULATE:
					System.out.println("Needed balance today is " + ExpenseCalculator.calcNeededBalance(dbManager));
					break;
				case ENTER_EXPENSE:
					System.out.print("Enter expense name: ");
					String eName = inputScanner.next();
					
					System.out.print("Enter amount: ");
					double eAmount = inputScanner.nextDouble();
					
					System.out.print("Enter schedule: ");
					int eSchedule = inputScanner.nextInt();
					
					System.out.print("Enter date: ");
					int eDom = inputScanner.nextInt();
					
					dbManager.insertExpense(eName, eAmount, eSchedule, eDom);
					break;
				case ENTER_INCOME:
					System.out.print("Enter income name: ");
					String iName = inputScanner.next();
					
					System.out.print("Enter amount: ");
					double iAmount = inputScanner.nextDouble();
					
					System.out.print("Enter schedule: ");
					int iSchedule = inputScanner.nextInt();
					
					System.out.print("Enter date: ");
					int iDom = inputScanner.nextInt();
					
					dbManager.insertIncome(iName, iAmount, iSchedule, iDom);
					break;
				case VIEW_DATA:
					dbManager.viewData();
					break;
				case RESET_DATABASE:
					dbManager.setupTables();
					break;
			}
		}
    }
}