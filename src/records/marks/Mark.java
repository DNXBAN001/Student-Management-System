package records.marks;

public class Mark
{
	private String studentNumber;
	private String subjectCode;
	private String mark;
	
	public Mark(String studentNumber, String subjectCode, String mark)
	{
		this.studentNumber = studentNumber;
		this.subjectCode = subjectCode;
		this.mark = mark;
	}
	public String getStudentNumber() 
	{
		return studentNumber;
	}

	public String getSubjectCode() 
	{
		return subjectCode;
	}

	public String getMark() 
	{
		return mark;
	}
	
}
