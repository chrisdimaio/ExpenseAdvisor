import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ExpenseCalculator
{	
	static int calcNeededBalance(DBManager dbManager)
	{
		// Just using 30 for now this will need to be exact.
		int daysInMonth = 30;
		
		int[] expenseSchedule = new int[daysInMonth];
		
		// Add expenses
		expenseSchedule[1] += 10;
		expenseSchedule[12] += 15;
		expenseSchedule[23] += 1;
		expenseSchedule[24] += 3;
		expenseSchedule[27] += 75;
		
		return 0;
	}
}