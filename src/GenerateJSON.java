import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.google.gson.Gson;

public class GenerateJSON {

	public static ArrayList<Course> relevantCourses = new ArrayList<Course>();

	/**
	 * Creates the JSON file to be returned to the client.
	 * 
	 * @param preferences A JSON containing the data from the website.
	 * @param ranked      The ArrayList of all ranked university objects.
	 * @return The JSON to be returned to the client as a string.
	 */
	public static String go(Preferences p, LinkedList<RankedCourse> ranked) {
		String toReturn = generateJson(p, ranked);
		return toReturn;
	}

	private static String generateJson(Preferences p, LinkedList<RankedCourse> ranked) {
		Gson g = new Gson();
		TreeMap<Double, RankedCourse> courseScores = new TreeMap<Double, RankedCourse>();
		for (RankedCourse rc : ranked) {
			double league = p.leagueTablePref * (double) (rc.nationwideRank + rc.subjectRank);
			double satisfaction = p.studentSatisfactionPref * (double) (rc.studentSatisfactionRank);
			double employability = p.employabilityPref * (double) (rc.graduateProspectsRank);
			double research = p.researchQualityPref * (double) (rc.researchOutputRank);
			double staff = p.studentToStaffPref * (double) (rc.studentFacultyRatioRank);
			double cost = p.costOfLivingPref * (double) (rc.costOfLivingRank);
			double international = p.internationalStudentPref * (double) (rc.internationalStudentsRatioRank);
			rc.score = league + satisfaction + employability + research + staff + cost + international;
			courseScores.put(rc.score, rc);
		}
		String[] unisInOrder = new String[courseScores.size()];
		Set<Entry<Double, RankedCourse>> doubleSet = courseScores.entrySet();
		Iterator<Entry<Double, RankedCourse>> doubleIt = doubleSet.iterator();
		int counter = 0;
		while (doubleIt.hasNext()) {
			Map.Entry<Double, RankedCourse> me = (Map.Entry<Double, RankedCourse>) doubleIt.next();
			unisInOrder[counter] = me.getValue().uni.name + " " + me.getValue().courseName;
			counter++;
		}
		return g.toJson(unisInOrder);
	}

}
