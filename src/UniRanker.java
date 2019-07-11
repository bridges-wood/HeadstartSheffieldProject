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

	public static LinkedList<University> unis = new LinkedList<University>();
	public static ArrayList<UniversityRanks> ranked = new ArrayList<UniversityRanks>();

	/**
	 * Ranks the universities based on the category of data.
	 * 
	 * @param i
	 *            The ith data field on the University Object;
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
				UniversityRanks temp = new UniversityRanks();
				temp.name = me.getValue().name;
				temp.studentSatisfactionRank = counter;
				ranked.add(temp);
				System.out.println(ranked.size());
			} else {
				for (UniversityRanks u : ranked) {
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
	 * @param i
	 *            The ith data field on the University Object;
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
	public static ArrayList<UniversityRanks> rankUnis() {
		generateUnis();
		//for(University u : unis) {
		//	System.out.println(u.name);
		//}
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
			temp.name = attributes[1];
			temp.type = attributes[2];
			temp.isRussellGroup = Boolean.parseBoolean(attributes[3]);
			temp.studentSatisfaction = Double.parseDouble(attributes[4]);
			temp.nationwideRanking = numOfUnis - Integer.parseInt(attributes[5]);
			temp.subjectSpecificRanking = numOfUnis - Integer.parseInt(attributes[6]);
			temp.costOfLiving = Double.parseDouble(attributes[7]);
			temp.studentFacultyRatio = Double.parseDouble(attributes[8]);
			temp.researchOutput = (int) Double.parseDouble(attributes[9]); //THIS LINE IS INCORRECT
			temp.entryRequirements = Integer.parseInt(attributes[10]); // UCAS points
			temp.internationalStudentsRatio = Double.parseDouble(attributes[11]);
			temp.extraYear = Boolean.parseBoolean(attributes[12]);
			temp.graduateProspects = Double.parseDouble(attributes[13]);
			unis.add(temp);
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
