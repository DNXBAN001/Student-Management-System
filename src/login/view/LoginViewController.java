package login.view;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import login.model.LoginModel;
import records.user.User;
import utils.Utilities;

/**
 * Date : 24 July 2020
 * @author Bandile Danxa
 *
 */
public class LoginViewController implements Utilities
{
    @FXML private Button loginButton;
    @FXML private PasswordField passwordField;
    @FXML public TextField usernameField;
    @FXML private Label loginStatus;
    
    public String title= "";
    private LoginModel task;

    
    @FXML
    void processLogin(ActionEvent event) 
    {
    	executeTask();
    }
    /**
     *  Creates a background with user's details to authenticate against database. The background is
     *  wrapped into a thread for its execution.
     */
    private void executeTask()
    {
        task = new LoginModel(new User(usernameField.getText(),passwordField.getText(),""));
        
        task.setOnSucceeded(e -> processUser()); // set method to execute on task completion
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(task);
        service.shutdown();
    }

    /**
     * Collects user from database if they exists.
     */
    private void processUser() 
    {
        User user = task.getValue(); // get user from background task on completion

        if(user != null)
        {
        	//if the status of the user is 'admin' then load the admin view
            if (user.getUserStatus().equalsIgnoreCase("admin"))
                loadAdminView("Admin", "/admin/view/AdminView.fxml");//
            
        
          //if the status of the user is 'lecturer' then load the lecturer view
            else if (user.getUserStatus().equalsIgnoreCase("lecturer"))
            	loadUserView(user.getUserName(), "/user/view/LecturerView.fxml");
                
            else
            {
            	 loginStatus.setText("Username or password incorrect");
                 Utilities.showSimpleMessage("Login Failure", "Username or password incorrect");
            }
        }
       
    }

	/**
	 * This method will close the Login interface in order to make way for the User interface
	 * which will allow any end-user to interact with it. 
	 * @throws IOException
	 */
    private void loadUserView(String title,  String path) 
    {
    	this.title = title;
    	loadFXMLView(title, path);
        Utilities.closeFXMLView(loginButton);//close the login window
        //System.out.println("Lecturer view has been activated");
    }
   /**
    * This method will close the Login interface and make a way for the Admin interface
    * which will allow only the Admin to interact with it.
    *@throws IOException
    */
    private void loadAdminView(String title, String path)
    {
    	this.title = title;
    	loadFXMLView(title, path);
        Utilities.closeFXMLView(loginButton);//close the login window
        //System.out.println("Admin view has been activated");
    }
    public String getTitle()
    {
    	return this.title;
    }
}