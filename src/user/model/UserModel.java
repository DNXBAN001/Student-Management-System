package user.model;

import java.sql.*;
import java.util.*;
import database.DatabaseHandle;
import javafx.concurrent.Task;
import records.marks.Mark;
import utils.Utilities;

public class UserModel extends Task<Boolean>
{

	private Set<Mark> marksList;
    private DatabaseHandle database = DatabaseHandle.getInstance();
    private long count = 0;



   /**
    * Constructs the Background Task given the list of Marks to store into database
    * @param userList
    */
    public UserModel(Set<Mark> marksList)
    {
        this.marksList = marksList;// initialize list of marks to store into database
    }

   /**
    * Method uploads all marks received into database
    * @param list
    */
    private void uploadMarksToDatabase(Set<Mark> list)
    {
        list.stream().parallel()
                .forEach(this::storeMarksIntoDb);
        count = 0;
    }
 
   
    private void storeMarksIntoDb(Mark mark) 
    {
        database.Insert(mark);
    }

   /**
    * Core Method that execute the Background Task.
    * @return
    */
    @Override
    protected Boolean call() 
    {
        uploadMarksToDatabase(marksList);
        return true;
    }
    
}
