package records.marks;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import login.model.LoginModel;
import utils.Utilities;

public class ProcessMark 
{

	private Set<Mark> listOfMarks;
	private String path;


    /**
     *  Constructs an instance of ProcessMarks and proceed to collect marks' data from
     *  specified directory and stores them into a set.
     */
    public ProcessMark()
    {
        this.listOfMarks = uploadMarks();
        //this.path = "/Excel Files/Lecturers/marks  Lecturer "+username+".csv";
    }

    /**
     *  Method read data from file and transform them into
     *  marks instances (objects) before storing them into a set.
     * @return
     */
    private Set<Mark> uploadMarks() 
    {
        return Utilities.getFileLines(getPath()).stream()
                .map(Utilities.tokenize(";"))
                .map(createMark())
                .collect(Collectors.toSet());
    }

    /**
     * Gets the directory and path of marks' file
     * @return
     */
    private Path getPath()
    {
        return Paths.get(Paths.get("").toAbsolutePath().toString() + "/Excel Files/marks.csv");
    }

    /**
     * Creates an instance Mark and populate its attributes with data collected from
     * file's line
     * @return
     */
    private Function<String[], Mark> createMark() 
    {
        return t -> new Mark(t[0], t[1], t[2]);
    }

    public void setMark(Set<Mark> listOfMarks)
    {
		this.listOfMarks = listOfMarks;
	}

    /**
     * Gets the set of all marks that are inside the file.
     * @return
     */
    public Set<Mark> getListName()
    {
        return this.listOfMarks;
    }
}
