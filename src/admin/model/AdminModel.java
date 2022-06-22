package admin.model;

import java.util.Set;

import database.DatabaseHandle;
import javafx.concurrent.Task;
import records.lecturer.Lecturer;
import records.marks.Mark;
import records.student.Student;
import records.subject.Subject;
import records.user.User;

public class AdminModel extends Task<Boolean>
{

	private Set<User> listOfAllUsers;
	private Set<Lecturer> lecturerList;
	private Set<Student> studentList;
	private Set<Subject> subjectList;
    private DatabaseHandle database = DatabaseHandle.getInstance();
    private long count = 0; // keeps count of how many data has been processed with respect to size



   /**
    * Constructs the Background Task given the list of Users to store into database
    * @param userList
    */
    public AdminModel(Set<User> userList, Set<Lecturer> lecturerList, Set<Student> studentList, Set<Subject> subjectList)
    {
        this.listOfAllUsers = userList; // initialize list of users to store into database
        this.lecturerList = lecturerList;// initialize list of lecturers to store into database
        this.studentList = studentList;// initialize list of students to store into database
        this.subjectList = subjectList;// initialize list of subjects to store into database
    }

   /**
    * Method uploads all users received into database
    * @param list
    */
    private void uploadUsersToDatabase(Set<User> list)
    {
        list.stream().parallel()
                .forEach(this::storeUsersIntoDb);
        count = 0;
    }
  /**
   * Method uploads all lecturers received into database
   * @param list
   */
   private void uploadLecturersToDatabase(Set<Lecturer> list)
   {
	     list.stream().parallel()
	             .forEach(this::storeLecturersIntoDb);
	     count = 0;
   }
 /**
  * Method uploads all students received into database
  * @param list
  */
  private void uploadStudentsToDatabase(Set<Student> list)
  {
      list.stream().parallel()
              .forEach(this::storeStudentsIntoDb);
      count = 0;
  }
  
  /**
   * Method uploads all subjects received into database
   * @param list
   */
   private void uploadSujectsToDatabase(Set<Subject> list)
   {
       list.stream().parallel()
               .forEach(this::storeSubjectsIntoDb);
       count = 0;
   }
   
   /**
    * Method inserts ONE user into database and updates progress bar and label on the GUI with
    * the current user details (user name, password, user status)
    * @param user
    */
    private void storeUsersIntoDb(User user) 
    {
        updateProgress(this.count,listOfAllUsers.size()); this.count++;
        updateMessage(user.getUserName()+ " " + user.getUserPassword() + " " + user.getUserStatus());
        database.Insert(user);
    }
    /**
     * This method inserts the lecturer received into the database
     * @param lecturer
     */
    private void storeLecturersIntoDb(Lecturer lecturer) 
    {
        database.Insert(lecturer);
    }
    /**
     * This method inserts the student received into the database
     * @param student
     */
    private void storeStudentsIntoDb(Student student) 
    {
        database.Insert(student);
    }
    /**
     * This method inserts the subject received into the database
     * @param student
     */
    private void storeSubjectsIntoDb(Subject subject)
    {
        database.Insert(subject);
    }

   /**
    * Core Method that execute the Background Task.
    * @return
    */
    @Override
    protected Boolean call() 
    {
        uploadUsersToDatabase(listOfAllUsers);
        uploadLecturersToDatabase(lecturerList);
        uploadStudentsToDatabase(studentList);
        uploadSujectsToDatabase(subjectList);
        return true;
    }

}
