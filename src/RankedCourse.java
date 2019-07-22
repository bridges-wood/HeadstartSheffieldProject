/**
 * This class is an object that is used to contain all attributes that matter
 * about a particular university in a single subject.
 * 
 * @author Max
 */
public class RankedCourse {
	public University uni; // The university at which the course is undertaken.
	public String subject; // The name of the subject that will be studied in the course.
	public int UCASrequirements; // The number of UCAS points required to get onto the course.
	public Qualification[] requiredQualifications; // Any required qualifications that the university has.
	public int subjectRank;
	public boolean industrialYear;
	public boolean studyAbroad;
	public double score; // The overall score that the course will get after the user's preferences are
							// acccounted for.
}
