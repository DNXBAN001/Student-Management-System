package admin.view;

import java.io.File;
import java.net.URL;

import admin.model.AdminModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import records.lecturer.Lecturer;
import records.lecturer.ProcessLecturer;
import records.marks.Mark;
import records.student.ProcessStudent;
import records.student.Student;
import records.subject.ProcessSubject;
import records.subject.Subject;
import records.user.ProcessUser;
import records.user.User;
import utils.Utilities;
import java.util.*;

public class AdminViewController implements Initializable, Utilities
{
	@FXML private Button uploadButton;
	@FXML private ProgressBar progressBar;
	@FXML private Button backToLogin;
	@FXML private Label updateLabel;
	@FXML private Button viewAllUsersButton;

	private ProcessUser processUser;
	private ProcessLecturer processLecturer;
	private ProcessStudent processStudent;
	private ProcessSubject processSubject;
	private AdminModel task;

	@FXML
	void manualSignUp(ActionEvent event)
	{
		System.out.println("Manual sign up clicked");
	}

	@FXML
	void backToMain(ActionEvent event)
	{
		loadFXMLView("Login", "/login/view/LoginView.fxml");
		Utilities.closeFXMLView(viewAllUsersButton);
	}

	@FXML
	void uploadAllFiles(ActionEvent event)
	{
		File directory = Utilities.loadFile(); // collect folder containing data

		if (directory != null) 
		{
			updateStatus(true);

			processUser = new ProcessUser(); // initialize user instance
			processLecturer = new ProcessLecturer();
			processStudent = new ProcessStudent();
			processSubject = new ProcessSubject();

			Set<User> userList = processUser.getListName();
			Set<Lecturer> lecturerList = processLecturer.getListName();
			Set<Student> studentList = processStudent.getListName();
			Set<Subject> subjectList = processSubject.getListName();

			executeTask(userList, lecturerList, studentList, subjectList);
		}
	}

	/**
	 * 
	 * @param list
	 * @param userList
	 */
	private void executeTask(Set<User> userList, Set<Lecturer> lecturerList, Set<Student> studentList,Set<Subject> subjectList)
	{
		task = new AdminModel(userList, lecturerList, studentList, subjectList);
		task.setOnSucceeded(e -> updateStatus(false));
		task.setOnCancelled(event -> System.out.println("Task cancelled"));
		setEvents();
		new Thread(task).start();
	}

	/**
	 * Once the user closes this window the task must be cancelled.
	 */
	private void killTask()
	{
		if (task.isRunning())
			task.cancel(true);
	}

	/**
	 * Method to enable/disable the visibility of progress bar and update label
	 */
	private void updateStatus(boolean state) 
	{
		progressBar.setVisible(state);
		updateLabel.setVisible(state);
	}

	/**
	 * Method that links the progressbar and updatelabel to task's properties
	 * (progress, message)
	 */
	private void setEvents() 
	{
		progressBar.progressProperty().bind(task.progressProperty());
		updateLabel.textProperty().bind(task.messageProperty());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		updateStatus(false);
	}

}