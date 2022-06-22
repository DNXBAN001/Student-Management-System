package database;

import static utils.Utilities.showErrorMessage;
import java.sql.*;
import database.DatabaseHandle;
import records.lecturer.Lecturer;
import records.marks.Mark;
import records.student.Student;
import records.subject.Subject;
import records.user.User;

/**
 * Date : 24 July 2020
 * @author Bandile Danxa
 *
 */
public class DatabaseHandle 
{

	private Connection connect = null;
	private PreparedStatement statement = null;
	private static DatabaseHandle handler = null;
	private final String DRIVER = "org.sqlite.JDBC";
	private final String URL = "jdbc:sqlite:StudentManagementDB.sqlite";
	
	
	
	/**
	 * Constructs the database, initiate connection and create all necessary table
	 */
	private DatabaseHandle()
	{
		createConnection();
		createUserTable();
		createStudentTable();
		createSubjectTable();
		createLecturerTable();
		createMarksTable();
		defaultUser();
	}
	/**
	 * Creates
	 */
	private DatabaseHandle(User user)
	{
		createConnection();
		createUserTable();
		Insert(user);
	}
	
	/**
	 *  Set the database instance and set it as static meaning this instance will be the ONLY shared
	 *  accross the board.
	 * @return
	 */
	public static DatabaseHandle getInstance()
	{
        if (handler == null)
            handler = new DatabaseHandle();
      
        return handler;
    }
	/**
	 * Sets the database instance but with a different constructor that takes a new user to be added on the database
	 * @param user
	 * @return
	 */
	public static DatabaseHandle getInstance(User user)
	{
		if (handler == null)
            handler = new DatabaseHandle(user);
      
        return handler;
	}
	
	/**
	 *  Initializes database with the ONLY user: Admin
	 */
	private void defaultUser()
	{
		this.Insert(new User("Admin","admin","admin"));
	}

	/**
	 * Makes connection with the SQLite Server using the DRIVER and URL of the JDBC
	 * (Java Database Connectivity)
	 */
	private void createConnection() 
	{
		try 
		{
            Class.forName(DRIVER);
            connect = DriverManager.getConnection(URL);
        } 
		catch (Exception e)
		{
			showErrorMessage("Database connection Failure",e.getMessage());
        }
	}
	
	/**
	 *  Creates table into database given the schema (name and attributes)
	 * @param table
	 * @param query
	 */
	private void createTable(String table,String query)
	{
		try
		{
			statement = connect.prepareStatement("CREATE TABLE IF NOT EXISTS  " + table + query);

			DatabaseMetaData dbm = connect.getMetaData();
			ResultSet tables = dbm.getTables(null, null, table.toUpperCase(), null);

			if (!tables.next())
				statement.execute();
		}
		catch(SQLException e)
		{
			showErrorMessage("Table Creation Failure",e.getMessage());
		}
	}
	
	/**
	 * Creates User Table by formatting its attributes/fields
	 */
	private void createUserTable() 
	{
		//Make our username a primary key
		createTable("Users",  "("
					+ "	userName varchar(20) PRIMARY KEY not null,\n"
					+ "	userPassword varchar(10) not null,\n"
					+ "	userStatus varchar(6) not null\n"
					+ " )");
	}
   /**
	* Creates Student Table by formatting its attributes/fields
	*/
	private void createStudentTable() 
	{
		//Make student number a primary key
		createTable("Student",  "("
					+ "	studentNumber varchar(10) PRIMARY KEY not null,\n"
					+ "	surname varchar(20) not null\n"
					+ " )");
		
	}
   /**
	* Creates Subject Table by formatting its attributes/fields
	*/
	private void createSubjectTable() 
	{
		//Make subject code a primary key
		createTable("Subject",  "("
					+ "	subjectCode varchar(10) PRIMARY KEY not null,\n"
					+ "	title varchar(20) not null,\n"
					+ "	lecturerStaffNumber varchar(10) not null,\n"
					+ "	credit varchar(5) not null\n"
					+ " )");
		//code, title, lecturer’s staff number, credit.
	}
   /**
	* Creates Lecturer Table by formatting its attributes/fields
	*/
	private void createLecturerTable() 
	{
		//Make staff number a primary key
		createTable("Lecturer",  "("
					+ "	staffNumber varchar(20) PRIMARY KEY not null,\n"
					+ "	surname varchar(20) not null\n"
					+ " )");
		
	}
   /**
	* Creates Marks Table by formatting its attributes/fields
	*/
	private void createMarksTable()
	{
		createTable("Mark",  "("
					+ "	studentNumber varchar(10) not null,\n"
					+ "	subjectCode varchar(10) not null,\n"
					+ "	mark varchar(5) not null,\n"
					+ "FOREIGN KEY (studentNumber) REFERENCES Student(studentNumber),\n"
					+ "FOREIGN KEY (subjectCode) REFERENCES Subject(subjectCode)"
					+ " )");
		//student number, code subject, mark
	}
	
   /**
	* Get the number of rows in a given table located in the database
	* @return
	*/
	public int getNumberOfRows(String table)
	{
		int rows = 0;
		try 
		{
			ResultSet resultSet = Select("select * from " + table);
			while(resultSet.next())
				rows++;
		}
		catch(SQLException e)
		{
			showErrorMessage("Table tuples Failure",e.getMessage());
		}
		return rows;
	}
	
   /**
	* Method pulls tuples from requested table matching the criteria
	* @param query
	* @return
	*/
	public ResultSet Select(String query) 
	{
        ResultSet result;
        try 
        {
            statement = connect.prepareStatement(query);
            result = statement.executeQuery();
        } 
        catch(SQLException ex) 
        {
			showErrorMessage("Select Query Failure",ex.getMessage());
            return null;
        }
        return result;
    }
	
   /**
	* Method inserts a new User record into database
	* @param query
	* @param user
	* @return
	*/
	public void Insert(User user) 
	{
		try 
		{
			statement = connect.prepareStatement("insert or ignore into Users values (?,?,?)");
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getUserPassword());
			statement.setString(3, user.getUserStatus());
			statement.executeUpdate();
		} 
		catch(SQLException ex) 
		{
			showErrorMessage("User Insertion Failure",ex.getMessage());
		}
	}
	/**
	* Method inserts a new Lecturer record into database
	* @param query
	* @param user
	* @return
	*/
	public void Insert(Lecturer lecturer) 
	{
		try 
		{
			statement = connect.prepareStatement("insert or ignore into Lecturer values (?,?)");
			statement.setString(1, lecturer.getStaffNumber());
			statement.setString(2, lecturer.getLecturerSurname());
			statement.executeUpdate();
		} 
		catch(SQLException ex) 
		{
			showErrorMessage("Lecturer Insertion Failure",ex.getMessage());
		}
	}
	/**
	* Method inserts a new Student record into database
	* @param query
	* @param user
	* @return
	*/
	public void Insert(Student student) 
	{
		try 
		{
			statement = connect.prepareStatement("insert or ignore into Student values (?,?)");
			statement.setString(1, student.getStudentNumber());
			statement.setString(2, student.getStudentSurname());
			statement.executeUpdate();
		} 
		catch(SQLException ex) 
		{
			showErrorMessage("Student Insertion Failure",ex.getMessage());
		}
	}
	/**
	* Method inserts a new Subject record into database
	* @param query
	* @param user
	* @return
	*/
	public void Insert(Subject subject) 
	{
		try 
		{
			statement = connect.prepareStatement("insert or ignore into Subject values (?,?,?,?)");
			statement.setString(1, subject.getSubjectCode());
			statement.setString(2, subject.getSubjectTitle());
			statement.setString(3, subject.getLecturerStaffNumber());
			statement.setString(4, subject.getSubjectCredit());
			statement.executeUpdate();
		} 
		catch(SQLException ex) 
		{
			showErrorMessage("Subject Insertion Failure",ex.getMessage());
		}
	}
	/**
	* Method inserts a new Mark record into database
	* @param query
	* @param user
	* @return
	*/
	public void Insert(Mark mark)
	{
		try 
		{
			statement = connect.prepareStatement("insert or ignore into Mark values (?,?,?)");
			statement.setString(1, mark.getStudentNumber());
			statement.setString(2, mark.getSubjectCode());
			statement.setString(3, mark.getMark());
			statement.executeUpdate();
		}
		catch(SQLException ex)
		{
			showErrorMessage("Mark Insertion Failure",ex.getMessage());
		}
	}
	
  /**
   *
   */
   public void closeSession()
   {
	   	try 
	   	{
			if(statement != null)statement.close();
			if(connect != null)connect.close();
		} 
	   	catch (SQLException ex) 
	   	{
			showErrorMessage("Session Close Failure",ex.getMessage());
		}
   }
}
