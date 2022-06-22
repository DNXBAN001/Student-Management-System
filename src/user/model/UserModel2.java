package user.model;

import database.DatabaseHandle;
import javafx.concurrent.Task;
import records.marks.Mark;
import utils.Utilities;

import java.sql.*;
import java.util.*;

public class UserModel2 extends Task<Set<Mark>>
{
	
	private DatabaseHandle databaseHandle;
    private  long count = 0;

   /**
    * Creates a task and initializes its database instance.
    * @param database
    */
    public UserModel2(DatabaseHandle database)
    {
       this.databaseHandle = database;
    }

   /**
    * Fetch all the marks from database (Table Marks)
    * @param resultSet
    * @return
    */
    private Set<Mark> collectMarksFromDB(ResultSet resultSet) 
    {
        Set<Mark> list = new HashSet<>();
        int size;
        try
        {
            size = databaseHandle.getNumberOfRows("Mark");
            while(resultSet.next())
            {
                list.add(new Mark(resultSet.getString(1), resultSet.getString(2),resultSet.getString(3)));
                count++;
                updateProgress(count,size);
            }
        }
        catch (SQLException ex)
        {
            Utilities.showErrorMessage("Select Marks Failure",ex.getMessage());
        }
        System.out.println("The size of the fetched data set is : "+list.size());
        return  list;
    }
  
   /**
    * Core of task: this method executes the task
    * @return
    */
    @Override
    protected Set<Mark> call()
    {
        Set<Mark> list;
        ResultSet resultSet = databaseHandle.Select("select * from Mark");
        list = collectMarksFromDB(resultSet);
        updateMessage("Task completed");
        return list;
    }
}
