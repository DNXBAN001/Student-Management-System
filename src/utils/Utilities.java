package utils;

import static java.nio.file.Files.readAllLines;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public interface Utilities 
{

	String USERPATH = "/Excel Files/Admin/users.csv";
	String LECTURERPATH = "/Excel Files/Admin/lecturers.csv";
	String SUBJECTPATH = "/Excel Files/Admin/subject.csv";
	String STUDENTPATH = "/Excel Files/Admin/students.csv";
	
	static String getMarksPath(String path)
	{
		return "/Excel Files/Admin/marks  Lecturer "+path+".csv";
	}
	
	/**
     * Method load a given FXML file on a brand new stage (window) and set the title of
     * the window
     * @param title
     * @param fxmlPath
     */
    default void loadFXMLView(String title, String fxmlPath)
    {
        try	{
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(title.toUpperCase());
            stage.show();
        }
        catch(Exception e) {
            showErrorMessage("FXML Loading Failure",e.getMessage());
        }
    }

    /**
     * Method displays an informative feedback to user using a dialogBox (pop-up message)
     * @param title
     * @param Content
     */
    static void showSimpleMessage(String title, String Content)
    {
        Platform.runLater(() -> {
            Alert alertBox = new Alert(Alert.AlertType.INFORMATION);
            alertBox.setTitle(title);
            alertBox.setContentText(Content);
            alertBox.setHeaderText(null);
            Stage stage = (Stage)alertBox.getDialogPane().getScene().getWindow();
            stage.showAndWait();
        });
    }

    /**
     * Method displays an error feedback to user using a dialogBox (pop-up message)
     * @param title
     * @param content
     */
    static void showErrorMessage(String title, String content)
    {
        Platform.runLater(() -> {
            Alert alertBox = new Alert(Alert.AlertType.ERROR);
            alertBox.setTitle(title);
            alertBox.setContentText(content);
            alertBox.setHeaderText(null);
            Stage stage = (Stage)alertBox.getDialogPane().getScene().getWindow();
            stage.showAndWait();
        });
    }

    /**
     * Method closes the window given any Node.
     * @param node
     */
    static void closeFXMLView(Node node)
    {
        ((Stage)node.getScene().getWindow()).close();// retrieve the stage and close it
    }
    
    /**
     * Method opens a directory dialogBox to allow the user to select a directory containing the
     * data of interest.
     * @return
     */
    static File loadFile()
    {
         DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(Paths.get("").toAbsolutePath().toFile());
         return  directoryChooser.showDialog(null);

    }

    /**
     * This method will retrieve all file's lines given the file's path.
     * @param p: path of file containing the babies details
     * @return: list of string of file' lines
     * @throws IOException
     */
    static List<String> getFileLines(Path p) 
    {
        List<String> list = new ArrayList<>();
        try 
        {
            list  = readAllLines(p);
        }
        catch (IOException e) 
        {
            showErrorMessage("Reading File Failure, Try Checking if Path is Correct", e.getMessage());
        }
        return  list;
    }
    
    static Function<? super String, ? extends String[]> tokenize(String separator) {
        return t -> t.split(separator);
    }

}
