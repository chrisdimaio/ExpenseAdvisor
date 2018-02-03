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
import java.io.File;

public class DBManager
{
	Connection connection = null;
	
	DBManager(String dbFile)
	{
		try
		{
			// If db file doesn't exist we create a new one.
			if(!new File(dbFile).exists())
			{
				System.out.println("db.db doesn't exists");
				System.out.println("creating new database");
				
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
				
				setupTables();
			}
			else
			{
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void closeConnection()
	{
		try
		{
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setupTables()
	{
		Statement stmt = null;
		try
		{
			stmt = connection.createStatement();
			stmt.executeUpdate("drop table if exists expenses;");
			stmt.executeUpdate("drop table if exists income;");
			stmt.executeUpdate("create table expenses (name TEXT, amount REAL, schedule INTEGER, day INTEGER);");
			stmt.executeUpdate("create table income (name TEXT, amount REAL, schedule INTEGER, day INTEGER);");
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void insertExpense(String name, double amount, int schedule, int day)
	{
		PreparedStatement pstmt = null;
		try
		{
			pstmt = connection.prepareStatement("insert into expenses values (?, ?, ?, ?)");
			
			pstmt.setString(1, name);
			pstmt.setDouble(2, amount);
			pstmt.setInt(3, schedule);
			pstmt.setInt(4, day);
			
			pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ResultSet getIncomes()
	{
		ResultSet results = null;
		Statement stmt = null;
		try
		{
			stmt = connection.createStatement();
			results = stmt.executeQuery("select * from income;");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}
	
	public ResultSet getExpenses()
	{
		ResultSet results = null;
		Statement stmt = null;
		try
		{
			stmt = connection.createStatement();
			results = stmt.executeQuery("select * from expenses;");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return results;
	}
	
	public void insertIncome(String name, double amount, int schedule, int day)
	{
		PreparedStatement pstmt = null;
		try
		{
			pstmt = connection.prepareStatement("insert into income values (?, ?, ?, ?)");
			
			pstmt.setString(1, name);
			pstmt.setDouble(2, amount);
			pstmt.setInt(3, schedule);
			pstmt.setInt(4, day);
			
			pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void viewData()
	{
		Statement stmt = null;
		try
		{
			DatabaseMetaData dbMetadata = connection.getMetaData();
			ResultSet tableResults = dbMetadata.getTables(null, null, "%", null);
			while (tableResults.next())
			{
				System.out.println("-" + tableResults.getString(3) + "-");
				
				stmt = connection.createStatement();
				ResultSet results = stmt.executeQuery("select * from " + tableResults.getString(3));
				ResultSetMetaData metadata = results.getMetaData();

				// Print the column headers.
				int columnCount = metadata.getColumnCount();
				for(int i = 0; i < columnCount; i++)
				{
					System.out.print(metadata.getColumnName(i+1)+"\t\t");
				}

				System.out.print("\n");

				while (results.next())
				{
					for(int i = 0; i < columnCount; i++)
					{
						printAnyType(results,  metadata.getColumnType(i+1), i+1);
						if(i != columnCount-1){System.out.print(",\t\t");}
					}
					System.out.print("\n");
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void printAnyType(ResultSet rs, int SQLType, int index) throws SQLException
	{
		switch(SQLType)
		{
			case 4: //int
				System.out.print(rs.getInt(index));
				break;
			
			case 6: //float
				System.out.print(rs.getFloat(index));
				break;
				
			case 12: //varchar
				System.out.print(rs.getString(index));
				break;
		}
		// System.out.println(SQLType);
	}
}