import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.google.gson.Gson;

public class GenerateJSON {



	public static String go(String preferences, ArrayList<UniversityRanks> ranked) {
		String toReturn = generateJson(preferences, ranked);
		return toReturn;
	}


	private static String generateJson(String prefs, ArrayList<UniversityRanks> ranked) {
		System.out.println("Here");
		for(UniversityRanks u : ranked) {
			System.out.println(u.name);
		}
		Gson g = new Gson();
		Preferences p = g.fromJson(prefs, Preferences.class);
		TreeMap<Double, String> universityScores = new TreeMap<Double, String>();
		for (UniversityRanks u : ranked) {
			double league = p.leagueTablePref * (double) (u.nationwideRank + u.subjectRank);
			double satisfaction = p.studentSatisfactionPref * (double) (u.studentSatisfactionRank);
			double employability = p.employabilityPref * (double) (u.graduateProspectsRank);
			double research = p.researchQualityPref * (double) (u.researchOutputRank);
			double staff = p.studentToStaffPref * (double) (u.studentFacultyRatioRank);
			double cost = p.costOfLivingPref * (double) (u.costOfLivingRank);
			double international = p.internationalStudentPref * (double) (u.internationalStudentsRatioRank);
			u.score = league + satisfaction + employability + research + staff + cost + international;
			universityScores.put(u.score, u.name);
		}
		String[] unisInOrder = new String[universityScores.size()];
		Set<Entry<Double, String>> doubleSet = universityScores.entrySet();
		Iterator<Entry<Double, String>> doubleIt = doubleSet.iterator();
		int counter = 0;
		while (doubleIt.hasNext()) {
			Map.Entry<Double, String> me = (Map.Entry<Double, String>) doubleIt.next();
			unisInOrder[counter] = me.getValue();
			counter++;
		}
		return g.toJson(unisInOrder);
	}


}
