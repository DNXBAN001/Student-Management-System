package records.subject;


public class Subject
{
	private String subjectCode;
	private String subjectTitle;
	private String lecturerStaffNumber;
	private String subjectCredit;
	
	public Subject(String subjectCode, String subjectTitle, String lecturerStaffNumber, String subjectCredit)
	{
		this.subjectCode = subjectCode;
		this.subjectTitle = subjectTitle;
		this.lecturerStaffNumber = lecturerStaffNumber;
		this.subjectCredit = subjectCredit;
	}

	public String getSubjectCode() 
	{
		return subjectCode;
	}

	public String getSubjectTitle() 
	{
		return subjectTitle;
	}

	public String getLecturerStaffNumber() 
	{
		return lecturerStaffNumber;
	}

	public String getSubjectCredit() 
	{
		return subjectCredit;
	}
	
	
	
}