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
	public static TreeMap<String, LinkedList<Course>> courses = new TreeMap<String, LinkedList<Course>>();
	public static TreeMap<String, LinkedList<RankedCourse>> rankedCourses = new TreeMap<String, LinkedList<RankedCourse>>();

	public UniRanker(String[] subjectsSupported) {
		init(subjectsSupported);
	}

	/**
	 * The initialisation method for the UniRanker Class. Generates all the
	 * universities and courses from the stored csv file, and ranks all the courses
	 * by subject.
	 */
	private static void init(String[] subjectsSupported) {
		generateUnis();
		generateCourses();
		rankCourses();
	}

	public static LinkedList<RankedCourse> fetchCourses(String subject, Qualification[] qualifications) {
		LinkedList<RankedCourse> toReturn = rankedCourses.get(subject);
		int UCAStotal = 0;
		for (Qualification q : qualifications) {
			UCAStotal += Qualification.calculateUCASpoints(q);
		}
		for (RankedCourse rc : toReturn) {
			if (rc.UCASrequirements > UCAStotal) {
				toReturn.remove(rc);
			}
			if (!rc.requiredQualifications.equals(null)) {
				for (Qualification q : rc.requiredQualifications) {
					for (int i = 0; i < qualifications.length; i++) {
						if (q.subject.equals(qualifications[i].subject)) {
							if (Qualification.calculateUCASpoints(q) > Qualification
									.calculateUCASpoints(qualifications[i])) {
								toReturn.remove(rc);
								break;
							}
						}
					}
					toReturn.remove(rc); /*
											 * If this causes concurrent modification exceptions, just create a list of
											 * everything that has to be removed and do it all at once. Max - 26/07/19
											 */

				}
			}
		}
		return null;
	}

	/**
	 * Ranks the universities based on the category of data.
	 * 
	 * @param i              The ith data field on the University Object;
	 * @param subjectCourses
	 */
	private static void doubleRanker(int i, LinkedList<Course> subjectCourses) {
		TreeMap<Double, Course> map = new TreeMap<Double, Course>(Collections.reverseOrder());
		for (Course c : subjectCourses) {
			switch (i) {
			case 2:
				map.put(c.uni.studentSatisfaction, c);
				break;
			case 4:
				map.put(1 - c.uni.costOfLiving, c); // In reverse order as lower is better.
				break;
			case 5:
				map.put(c.uni.studentFacultyRatio, c);
				break;
			case 7:
				map.put(c.uni.internationalStudentsRatio, c);
				break;
			case 8:
				map.put(c.uni.graduateProspects, c);
				break;
			}
		}
		Set<Entry<Double, Course>> doubleSet = map.entrySet();
		Iterator<Entry<Double, Course>> doubleIt = doubleSet.iterator();
		int counter = 1;
		while (doubleIt.hasNext()) {
			Map.Entry<Double, Course> me = (Map.Entry<Double, Course>) doubleIt.next();
			LinkedList<RankedCourse> ranked = rankedCourses.get(subjectCourses.getFirst().subject);
			RankedCourse rc = new RankedCourse();
			for (RankedCourse course : ranked) {
				if (course.courseName.equals(me.getValue().courseName) && course.uni.equals(me.getValue().uni)) {
					rc = course;
					break;
				}
			}
			switch (i) {
			case 2:
				rc.studentFacultyRatioRank = counter;
			case 4:
				rc.costOfLivingRank = counter;
				break;
			case 5:
				rc.studentFacultyRatioRank = counter;
				break;
			case 7:
				rc.internationalStudentsRatioRank = counter;
				break;
			case 8:
				rc.graduateProspectsRank = counter;
				break;
			}
			counter++;
		}
	}

	/**
	 * Ranks the universities based on the category of data.
	 * 
	 * @param i              The ith data field on the University Object;
	 * @param subjectCourses
	 */
	private static void intRanker(int i, LinkedList<Course> subjectCourses) {
		TreeMap<Integer, Course> map = new TreeMap<Integer, Course>(Collections.reverseOrder());
		for (Course c : subjectCourses) {
			switch (i) {
			case 1:
				map.put(1 - c.subjectRank, c); // In reverse order as lower is better.
				break;
			case 3:
				map.put(1 - c.uni.nationwideRanking, c);
				break;
			case 6:
				map.put(c.uni.researchOutput, c);
				break;
			}
		}
		Set<Entry<Integer, Course>> integerSet = map.entrySet();
		Iterator<Entry<Integer, Course>> integerIt = integerSet.iterator();
		int counter = 1;
		while (integerIt.hasNext()) {
			Map.Entry<Integer, Course> me = (Map.Entry<Integer, Course>) integerIt.next();
			LinkedList<RankedCourse> ranked = rankedCourses.get(subjectCourses.getFirst().subject);
			RankedCourse rc = new RankedCourse();
			for (RankedCourse course : ranked) {
				if (course.courseName.equals(me.getValue().courseName) && course.uni.equals(me.getValue().uni)) {
					rc = course;
					break;
				}
			}
			switch (i) {
			case 1:
				rc.subjectRank = counter;
				break;
			case 3:
				rc.nationwideRank = counter;
				break;
			case 6:
				rc.researchOutputRank = counter;
				break;
			}
			counter++;
		}
	}

	/**
	 * Manager for ranking of courses in all major categories.
	 */
	private static void rankCourses() {
		for (String subject : courses.keySet()) {
			LinkedList<Course> subjectCourses = courses.get(subject);
			for (int i = 1; i <= 8; i++) {
				switch (i) {
				case 1:
					// Subject rank
					intRanker(1, subjectCourses);
					break;
				case 2:
					// Student satisfaction
					intRanker(2, subjectCourses);
					break;
				case 3:
					// Nationwide ranking
					doubleRanker(3, subjectCourses);
					break;
				case 4:
					// Cost of living
					doubleRanker(4, subjectCourses);
					break;
				case 5:
					// Student to faculty ratio
					doubleRanker(5, subjectCourses);
					break;
				case 6:
					// Research Output
					intRanker(6, subjectCourses);
					break;
				case 7:
					// International students
					doubleRanker(7, subjectCourses);
					break;
				case 8:
					// Graduate prospects
					doubleRanker(8, subjectCourses);
					break;
				}
			}
		}
	}

	/**
	 * Generates all universities from stored csv file of university data.
	 */
	private static void generateUnis() {
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

	private static void generateCourses() {
		String[] lines = readFile("resources/coursesdata.csv");
		for (String line : lines) {
			String[] attributes = line.split(",");
			Course temp = new Course();
			temp.uni = unis.get(attributes[0]);
			temp.subject = attributes[1];
			temp.courseName = attributes[2];
			temp.UCASrequirements = Integer.parseInt(attributes[3]);
			temp.requiredQualifications = Qualification.parseQualificationArray(attributes[4]);
			temp.subjectRank = Integer.parseInt(attributes[5]);
			temp.industrialYear = Boolean.parseBoolean(attributes[6]);
			temp.studyAbroad = Boolean.parseBoolean(attributes[7]);
			/*
			 * Edit this to show the desired structure of the courses CSV file.
			 */

			// Add courses to all desired data structures.
			unis.get(attributes[0]).courses.add(temp);
			if (courses.containsKey(temp.subject)) {
				LinkedList<Course> courseList = courses.get(temp.subject);
				courseList.add(temp);
				courses.put(temp.subject, courseList);
			} else {
				LinkedList<Course> courseList = new LinkedList<Course>();
				courseList.add(temp);
				courses.put(temp.subject, courseList);
			}
			if (rankedCourses.containsKey(temp.subject)) {
				LinkedList<RankedCourse> courseList = rankedCourses.get(temp.subject);
				courseList.add(RankedCourse.parseCourse(temp));
				rankedCourses.put(temp.subject, courseList);
			} else {
				LinkedList<RankedCourse> courseList = new LinkedList<RankedCourse>();
				courseList.add(RankedCourse.parseCourse(temp));
				rankedCourses.put(temp.subject, courseList);
			}
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
