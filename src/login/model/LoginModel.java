package login.model;

import java.sql.*;
import database.DatabaseHandle;
import javafx.concurrent.Task;
import records.user.User;
import utils.Utilities;

/**
 * Date : 24 July 2020
 * @author Bandile Danxa
 *
 */
public class LoginModel extends Task<User>
{

	private User user;
    private DatabaseHandle database = DatabaseHandle.getInstance();

   /**
    * Creates Task instance and initializes the current user
    * @param user
    */
    public LoginModel(User user)
    {
        this.user = user;
    }

   /**
    * Query database for the current logged user....
    */
    private void collectUserFromDatabase() 
    {
        ResultSet resultSet = database.Select("select * from Users where userName = '" + user.getUserName() + "' and userPassword = '" +
                user.getUserPassword() + "'");
        user = getUser(resultSet);
    }

   /**
    * Validates whether the user exists or not into database: Authentication
    * @param resultSet
    * @return
    */
    private User getUser(ResultSet resultSet)
    {
        User myUser =  new User("","","");

        try 
        {
            while(resultSet.next())
            {
                myUser = new User(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
            }
        } 
        catch (SQLException e) 
        {
            Utilities.showErrorMessage("User Credentials Failure",e.getMessage());
        }
        return myUser;
    }

   /**
    * Core Method that execute the task: User Authentication against database
    * @return
    */
    @Override
    protected User call()
    {
        collectUserFromDatabase();
        return user;
    }
}
