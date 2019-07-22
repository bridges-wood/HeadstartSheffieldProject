import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class UniRanker {

	public static TreeMap<String, University> unis = new TreeMap<String, University>();
	public static ArrayList<RankedCourse> relevantCourses = new ArrayList<RankedCourse>();

	/**
	 * Ranks the universities based on the category of data.
	 * 
	 * @param i The ith data field on the University Object;
	 */
	private static void doubleRanker(int i) {
		TreeMap<Double, University> map = new TreeMap<Double, University>(Collections.reverseOrder());
		for (University u : unis) {
			switch (i) {
			case 0:
				map.put(u.studentSatisfaction, u);
				break;
			case 3:
				map.put(u.costOfLiving, u);
				break;
			case 4:
				map.put(u.studentFacultyRatio, u);
				break;
			case 7:
				map.put(u.internationalStudentsRatio, u);
				break;
			case 8:
				map.put(u.graduateProspects, u);
				break;
			}
		}
		Set<Entry<Double, University>> doubleSet = map.entrySet();
		Iterator<Entry<Double, University>> doubleIt = doubleSet.iterator();
		int counter = 1;
		while (doubleIt.hasNext()) {
			Map.Entry<Double, University> me = (Map.Entry<Double, University>) doubleIt.next();
			if (i == 0) {
				RankedCourse temp = new RankedCourse();
				temp.universityName = me.getValue().name;
				temp.studentSatisfactionRank = counter;
				ranked.add(temp);
				System.out.println(ranked.size());
			} else {
				for (RankedCourse u : ranked) {
					switch (i) {
					case 3:
						u.costOfLivingRank = counter;
						break;
					case 4:
						u.studentFacultyRatioRank = counter;
						break;
					case 7:
						u.internationalStudentsRatioRank = counter;
						break;
					case 8:
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
	 * @param i The ith data field on the University Object;
	 */
	private static void intRanker(int i) {
		TreeMap<Integer, University> map = new TreeMap<Integer, University>(Collections.reverseOrder());
		for (University u : unis) {
			switch (i) {
			case 1:
				map.put(u.nationwideRanking, u);
				break;
			case 2:
				map.put(u.subjectSpecificRanking, u);
				break;
			case 5:
				map.put(u.researchOutput, u);
				break;
			case 6:
				map.put(u.entryRequirements, u);
				break;
			}
		}
		Set<Entry<Integer, University>> intSet = map.entrySet();
		Iterator<Entry<Integer, University>> intIt = intSet.iterator();
		int counter = 1;
		while (intIt.hasNext()) {
			Map.Entry<Integer, University> me = (Map.Entry<Integer, University>) intIt.next();
			for (RankedCourse u : ranked) {
				if (u.universityName.equals(me.getValue().name)) {
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
	public static ArrayList<RankedCourse> rankUnis() {
		generateUnis();
		// for(University u : unis) {
		// System.out.println(u.name);
		// }
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
		return ranked;
	}

	/**
	 * Generates all universities from stored csv file of university data.
	 */
	public static void generateUnis() {
		String[] lines = readFile("resources/unidata.csv");
		int numOfUnis = lines.length + 1;
		for (String line : lines) {
			String[] attributes = line.split(",");
			University temp = new University();
			temp.name = attributes[0];
			temp.type = attributes[1];
			temp.isRussellGroup = Boolean.parseBoolean(attributes[2]);
			temp.studentSatisfaction = Double.parseDouble(attributes[3]);
			temp.nationwideRanking = numOfUnis - Integer.parseInt(attributes[4]);
			temp.costOfLiving = Double.parseDouble(attributes[5]);
			temp.studentFacultyRatio = Double.parseDouble(attributes[6]);
			temp.researchOutput = (int) Double.parseDouble(attributes[7]); // THIS LINE IS INCORRECT
			temp.internationalStudentsRatio = Double.parseDouble(attributes[8]);
			temp.graduateProspects = Double.parseDouble(attributes[9]);
			/*
			 * Structure of CSV file containing general university data: NAME, TYPE (City or
			 * Campus), RUSSELL GROUP, STUDENT SATISFACTION, NATIONWIDE RANKING (Overall),
			 * COST OF LIVING (According to appropriate cost of living index), STUDENT
			 * FACULTY RATIO, RESEARCH OUTPUT, INTERNATIONAL STUDENT RATIO and GRADUATE
			 * PROSPECTS (How many graduates in full-time employment or education after an
			 * appropriate period).
			 */
			unis.put(temp.name, temp);
		}
	}

	public static void generateCourses() {
		String[] lines = readFile("resources/coursesdata.csv");
		for (String line : lines) {
			String[] attributes = line.split(",");
			RankedCourse temp = new RankedCourse();
			temp.uni = unis.get(attributes[0]);
			temp.subject = attributes[1];
			temp.UCASrequirements = Integer.parseInt(attributes[2]);
			temp.requiredQualifications = Qualification.parseQualificationArray(attributes[3]);
			temp.subjectRank = 0; // Need to count the number of courses within a particular subject and rank
									// them.
			temp.industrialYear = Boolean.parseBoolean(attributes[4]);
			temp.studyAbroad = Boolean.parseBoolean(attributes[5]);
			/*
			 * Edit this to show the desired structure of the courses CSV file.
			 */
			unis.get(attributes[0]).courses.add(temp);
		}
	}

	private static String[] readFile(String filename) {
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
