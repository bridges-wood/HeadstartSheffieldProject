
/**
 * Holds information about a single course, and its rank with
 * competing courses.
 * 
 * @author Max
 *
 */
public class RankedCourse {

	public University uni; // The university at which the course is undertaken.
	public String subject; // The name of the subject that will be studied in the course.
	public String courseName;
	public int UCASrequirements; // The number of UCAS points required to get onto the course.
	public Qualification[] requiredQualifications; // Any required qualifications that the university has.
	public boolean industrialYear;
	public boolean studyAbroad;
	public String type;
	public boolean isRussellGroup;
	public int subjectRank; // 1 int
	public int studentSatisfactionRank; // 2 double
	public int nationwideRank; // 3 int
	public int costOfLivingRank; // 4 double
	public int studentFacultyRatioRank; // 5 double
	public int researchOutputRank; // 6 int
	public int internationalStudentsRatioRank; // 7 double
	public int graduateProspectsRank; // 8 double
	public double score;

	public static RankedCourse parseCourse(Course c) {
		RankedCourse rc = new RankedCourse();
		rc.uni = c.uni;
		rc.subject = c.subject;
		rc.courseName = c.courseName;
		rc.UCASrequirements = c.UCASrequirements;
		rc.requiredQualifications = c.requiredQualifications;
		rc.industrialYear = c.industrialYear;
		rc.studyAbroad = c.studyAbroad;
		rc.isRussellGroup = c.uni.isRussellGroup;
		rc.type = c.uni.type;
		return rc;
	}
}
