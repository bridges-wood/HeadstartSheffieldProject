import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tester {

	public static void main(String[] args) {
		String[] lines = readFile("res/unis.csv");
		int numOfUnis = lines.length + 1;
		for (String line : lines) {
			String[] attributes = line.split(",");
			University temp = new University();
			temp.name = attributes[1];
			temp.type = attributes[2];
			temp.isRussellGroup = Boolean.parseBoolean(attributes[3]);
			System.out.println(temp.isRussellGroup);
			temp.studentSatisfaction = Double.parseDouble(attributes[4]);
			temp.nationwideRanking = numOfUnis - Integer.parseInt(attributes[5]);
			temp.subjectSpecificRanking = numOfUnis - Integer.parseInt(attributes[6]);
			temp.costOfLiving = Double.parseDouble(attributes[7]);
			temp.studentFacultyRatio = Double.parseDouble(attributes[8]);
			temp.researchOutput = Integer.parseInt(attributes[9]);
			temp.entryRequirements = Integer.parseInt(attributes[10]); // UCAS points
			temp.internationalStudentsRatio = Double.parseDouble(attributes[11]);
			temp.extraYear = Boolean.parseBoolean(attributes[12]);
			temp.graduateProspects = Double.parseDouble(attributes[13]);
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
