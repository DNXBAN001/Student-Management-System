package records.subject;

import static utils.Utilities.SUBJECTPATH;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import utils.Utilities;

public class ProcessSubject 
{

	private Set<Subject> listOfSubjects;

    /**
     *  Constructs an instance of ProcessSubject and proceed to collect subject's data from
     *  specified directory and stores them into a set.
     */
    public ProcessSubject() 
    {
        this.listOfSubjects = uploadSubjects();
    }

    /**
     *  Method read data from file and transform them into
     *  subject instances (objects) before storing them into a set.
     * @return
     */
    private Set<Subject> uploadSubjects() 
    {
        return Utilities.getFileLines(getPath()).stream()
                .map(Utilities.tokenize(";"))
                .map(createSubject())
                .collect(Collectors.toSet());
    }

    /**
     * Gets the directory and path of subject's file
     * @return
     */
    private Path getPath() 
    {
        return Paths.get(Paths.get("").toAbsolutePath().toString() + SUBJECTPATH);
    }

    /**
     * Creates an instance Subject and populate its attributes with data collected from
     * file's line
     * @return
     */
    private Function<String[], Subject> createSubject() 
    {
        return t -> new Subject(t[0],t[1], t[2], t[3]);
    }


    /**
     * Gets the set of all subjects that are inside the file.
     * @return
     */
    public Set<Subject> getListName()
    {
        return this.listOfSubjects;
    }
}
