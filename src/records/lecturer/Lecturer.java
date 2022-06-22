package records.lecturer;

public class Lecturer
{
	private String staffNumber;
	private String lecturerSurname;
	
	public Lecturer(String staffNumber, String lecturerSurname)
	{
		this.staffNumber = staffNumber;
		this.lecturerSurname = lecturerSurname;
	}

	public String getStaffNumber()
	{
		return staffNumber;
	}

	public String getLecturerSurname()
	{
		return lecturerSurname;
	}
	
	
	
}