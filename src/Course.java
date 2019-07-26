/**
 * This class is an object that is used to contain all attributes that matter
 * about a particular university in a single subject.
 * 
 * @author Max
 */
public class Course {
	public University uni; // The university at which the course is undertaken.
	public String subject; // The name of the subject that will be studied in the course.
	public String courseName;
	public int UCASrequirements; // The number of UCAS points required to get onto the course.
	public Qualification[] requiredQualifications; // Any required qualifications that the university has.
	public int subjectRank;
	public boolean industrialYear;
	public boolean studyAbroad;
}
