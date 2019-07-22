import java.util.LinkedList;

/**
 * This object holds information about a given university.
 * 
 * @author Max
 */
public class University {
	public String name;
	public String type;
	public boolean isRussellGroup;
	public double studentSatisfaction; // 1
	public int nationwideRanking;
	public double costOfLiving;
	public double studentFacultyRatio; // 5
	public int researchOutput;
	public double internationalStudentsRatio; // 8
	public double graduateProspects; // 9
	public LinkedList<RankedCourse> courses;
}