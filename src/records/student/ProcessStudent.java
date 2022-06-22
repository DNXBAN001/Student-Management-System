package records.student;

import static utils.Utilities.STUDENTPATH;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import utils.Utilities;

public class ProcessStudent 
{

	private Set<Student> listOfStudents;

    /**
     *  Constructs an instance of ProcessStudent and proceed to collect student's data from
     *  specified directory and stores them into a set.
     */
    public ProcessStudent() 
    {
        this.listOfStudents = uploadStudents();
    }

    /**
     *  Method read data from file and transform them into
     *  student instances (objects) before storing them into a set.
     * @return
     */
    private Set<Student> uploadStudents() 
    {
        return Utilities.getFileLines(getPath()).stream()
                .map(Utilities.tokenize(";"))
                .map(createStudent())
                .collect(Collectors.toSet());
    }

    /**
     * Gets the directory and path of student's file
     * @return
     */
    private Path getPath() 
    {
        return Paths.get(Paths.get("").toAbsolutePath().toString() + STUDENTPATH);
    }

    /**
     * Creates an instance Student and populate its attributes with data collected from
     * file's line
     * @return
     */
    private Function<String[], Student> createStudent() 
    {
        return t -> new Student(t[0],t[1]);
    }


    /**
     * Gets the set of all students that are inside the file.
     * @return
     */
    public Set<Student> getListName()
    {
        return this.listOfStudents;
    }
}
