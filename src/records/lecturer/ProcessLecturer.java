package records.lecturer;

import static utils.Utilities.LECTURERPATH;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import utils.Utilities;

public class ProcessLecturer 
{

	private Set<Lecturer> listOfLecturers;

    /**
     *  Constructs an instance of ProcessLecturer and proceed to collect lecturer's data from
     *  specified directory and stores them into a set.
     */
    public ProcessLecturer() 
    {
        this.listOfLecturers = uploadLecturers();
    }

    /**
     *  Method read data from file and transform them into
     *  lecturer instances (objects) before storing them into a set.
     * @return
     */
    private Set<Lecturer> uploadLecturers() 
    {
        return Utilities.getFileLines(getPath()).stream()
                .map(Utilities.tokenize(","))
                .map(createLecturer())
                .collect(Collectors.toSet());
    }

    /**
     * Gets the directory and path of lecturer's file
     * @return
     */
    private Path getPath() 
    {
        return Paths.get(Paths.get("").toAbsolutePath().toString() + LECTURERPATH);
    }

    /**
     * Creates an instance Lecturer and populate its attributes with data collected from
     * file's line
     * @return
     */
    private Function<String[], Lecturer> createLecturer()
    {
        return t -> new Lecturer(t[0],t[1]);
    }


    /**
     * Gets the set of all lecturers that are inside the file.
     * @return
     */
    public Set<Lecturer> getListName()
    {
        return this.listOfLecturers;
    }
}
