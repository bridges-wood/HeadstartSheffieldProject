import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import com.google.gson.Gson;

public class GenerateJSON {

	public LinkedList<University> unis;
	public ArrayList<UniversityRanks> ranked;

	public String go(String preferences) {
		generateUnis();
		rankUnis();
		String toReturn = generateJson(preferences);
		return toReturn;
	}

	/**
	 * Ranks the universities based on the category of data.
	 * 
	 * @param i
	 *            The ith data field on the University Object;
	 */
	private void doubleRanker(int i) {
		TreeMap<Double, University> map = new TreeMap<Double, University>(Collections.reverseOrder());
		for (University u : unis) {
			switch (i) {
			case 1:
				map.put(u.studentSatisfaction, u);
				break;
			case 4:
				map.put(u.costOfLiving, u);
				break;
			case 5:
				map.put(u.studentFacultyRatio, u);
				break;
			case 8:
				map.put(u.internationalStudentsRatio, u);
				break;
			case 9:
				map.put(u.graduateProspects, u);
				break;
			}
		}
		Set<Entry<Double, University>> doubleSet = map.entrySet();
		Iterator<Entry<Double, University>> doubleIt = doubleSet.iterator();
		int counter = 1;
		while (doubleIt.hasNext()) {
			Map.Entry<Double, University> me = (Map.Entry<Double, University>) doubleIt.next();
			if (i == 1) {
				UniversityRanks temp = new UniversityRanks();
				temp.name = me.getValue().name;
				temp.studentSatisfactionRank = counter;
			} else {
				for (UniversityRanks u : ranked) {
					switch (i) {
					case 4:
						u.costOfLivingRank = counter;
						break;
					case 5:
						u.studentFacultyRatioRank = counter;
						break;
					case 8:
						u.internationalStudentsRatioRank = counter;
						break;
					case 9:
						u.graduateProspectsRank = counter;
						break;
					}
				}
			}
			counter++;
		}

	}

	/**
	 * Ranks the universities based on the category of data.
	 * 
	 * @param i
	 *            The ith data field on the University Object;
	 */
	private void intRanker(int i) {
		TreeMap<Integer, University> map = new TreeMap<Integer, University>(Collections.reverseOrder());
		for (University u : unis) {
			switch (i) {
			case 2:
				map.put(u.nationwideRanking, u);
				break;
			case 3:
				map.put(u.subjectSpecificRanking, u);
				break;
			case 6:
				map.put(u.researchOutput, u);
				break;
			case 7:
				map.put(u.entryRequirements, u);
				break;
			}
		}
		Set<Entry<Integer, University>> intSet = map.entrySet();
		Iterator<Entry<Integer, University>> intIt = intSet.iterator();
		int counter = 1;
		while (intIt.hasNext()) {
			Map.Entry<Integer, University> me = (Map.Entry<Integer, University>) intIt.next();
			for (UniversityRanks u : ranked) {
				if (u.name.equals(me.getValue().name)) {
					u.nationwideRank = counter;
					switch (i) {
					case 2:
						u.nationwideRank = counter;
						break;
					case 3:
						u.subjectRank = counter;
						break;
					case 6:
						u.researchOutputRank = counter;
						break;
					case 7:
						u.entryRequirementsRank = counter;
						break;
					}
				}
			}
			counter++;
		}
	}

	/**
	 * Manager for ranking of universities in all major categories.
	 */
	private void rankUnis() {
		for (int i = 0; i < 9; i++) {
			switch (i) {
			case 0:
				doubleRanker(0);
				break;
			case 1:
				intRanker(1);
				break;
			case 2:
				intRanker(2);
				break;
			case 3:
				intRanker(3);
				break;
			case 4:
				doubleRanker(4);
				break;
			case 5:
				doubleRanker(5);
				break;
			case 6:
				intRanker(6);
				break;
			case 7:
				intRanker(7);
				break;
			case 8:
				doubleRanker(8);
				break;
			}
		}
	}

	/**
	 * Generates all universities from stored csv file of university data.
	 */
	private void generateUnis() {
		String[] lines = readFile("res/unis.csv");
		for (String line : lines) {
			String[] attributes = line.split(",");
			University temp = new University();
			temp.name = attributes[1];
			temp.type = attributes[2];
			temp.isRussellGroup = Boolean.parseBoolean(attributes[3]);
			temp.studentSatisfaction = Double.parseDouble(attributes[4]);
			temp.nationwideRanking = Integer.parseInt(attributes[5]);
			temp.subjectSpecificRanking = Integer.parseInt(attributes[6]);
			temp.costOfLiving = Double.parseDouble(attributes[7]);
			temp.studentFacultyRatio = Double.parseDouble(attributes[8]);
			temp.researchOutput = Integer.parseInt(attributes[9]);
			temp.entryRequirements = Integer.parseInt(attributes[10]);
			; // UCAS points
			temp.internationalStudentsRatio = Double.parseDouble(attributes[11]);
			temp.extraYear = Boolean.parseBoolean(attributes[12]);
			temp.graduateProspects = Double.parseDouble(attributes[13]);
			unis.add(temp);
		}
	}

	private String generateJson(String prefs) {
		Gson g = new Gson();
		Preferences p = g.fromJson(prefs, Preferences.class);
		TreeMap<Double, String> universityScores = new TreeMap<Double, String>(Collections.reverseOrder());
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
		
		return prefs;
	}

	private String[] readFile(String filename) {
		List<String> words = new ArrayList<String>();
		File file = new File(filename);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.err.println(e.toString());
		}
		while (sc.hasNextLine()) { // Ensures that the scanner can still access the next set of data.
			words.add(sc.nextLine());
		}
		sc.close();
		return words.toArray(new String[0]);
	}

}
