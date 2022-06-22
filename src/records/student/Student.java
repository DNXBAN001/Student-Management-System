package records.student;

public class Student
{
	private String studentNumber;
	private String studentSurname;
	
	public Student(String studentNumber, String studentSurname)
	{
		this.studentNumber = studentNumber;
		this.studentSurname = studentSurname;
	}

	public String getStudentNumber()
	{
		return studentNumber;
	}

	public String getStudentSurname()
	{
		return studentSurname;
	}
	
}