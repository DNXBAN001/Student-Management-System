package user.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import admin.model.AdminModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import login.model.LoginModel;
import login.view.LoginViewController;
import records.marks.Mark;
import records.marks.ProcessMark;
import records.user.User;
import user.model.UserModel;
import user.model.UserModel2;
import utils.Utilities;
import database.DatabaseHandle;

/**
 * Date : 24 July 2020
 * @author Bandile Danxa
 *
 */
public class UserViewController implements Utilities, Initializable
{   
    @FXML private Button signOutButton;
    @FXML private Button uploadFileButton;
    @FXML private TableView<Mark> marksTable;
    @FXML private TableColumn<Mark, String> studentNumber;
    @FXML private TableColumn<Mark, String> subjectCode;
    @FXML private TableColumn<Mark, String> mark;

    private Set<Mark> marksList;
    private ProcessMark processMark;
    private UserModel task;
    private UserModel2 task2;
    private DatabaseHandle database;
    
    
    @FXML
    void backToMain(ActionEvent event)
    {
    	loadFXMLView("Login", "/login/view/LoginView.fxml");
    	Utilities.closeFXMLView(signOutButton);
    }

    @FXML
    void uploadFiles(ActionEvent event) 
    {
    	File directory = Utilities.loadFile(); // collect folder containing data
		if (directory != null) 
		{
			processMark = new ProcessMark();
			Set<Mark> markList = processMark.getListName();
			executeTask(markList);
		}

		//fillingMarksTable();
    }
    private void executeTask(Set<Mark> markList)
	{
		task = new UserModel(markList);
		task.setOnCancelled(event -> System.out.println("Task cancelled"));
		new Thread(task).start();
	}
    
    private void fillingMarksTable()
    {
    	studentNumber.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
    	subjectCode.setCellValueFactory(new PropertyValueFactory<>("subjectCode"));
    	mark.setCellValueFactory(new PropertyValueFactory<>("mark"));
       
        if(marksList != null)
        {
            ObservableList list = FXCollections.observableArrayList(marksList);
            marksTable.setItems(list);
        }
    }

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		
		database = DatabaseHandle.getInstance();
        task2 = new UserModel2(database);
        processMark = new ProcessMark();
        
		initializeTask();
		fillingMarksTable();
	}
	
	   /**
    *
    */
   private void initializeTask()
   {
	   task2.setOnSucceeded(e -> manageSucceed());

       // create ExecutorService to manage threads
       ExecutorService executorService = Executors.newFixedThreadPool(1); // pool of one thread
       executorService.execute(task2); // start task2
       executorService.shutdown();
   }

   /**
    *
    */
   private void manageSucceed()
   {
	   marksList = task2.getValue();
	   System.out.println("The size of the marksList is : "+marksList.size());
       if(marksList != null)
         processMark.setMark(marksList);
   }
    
    
    
}
