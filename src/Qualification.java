
/**
 * Object containing all relevant information to qualifications.
 * 
 * @author Max
 */
public class Qualification {
	public static String type;
	public String subject;
	public static String grade;

	public static Qualification[] parseQualificationArray(String s) {
		// Extract specifically formatted data into the format of qualification.
		return new Qualification[0];
	}

	public static int calculateUCASpoints(Qualification q) {
		switch (type) {
		case "A-level":
			switch (grade) {
			case "A*":
				return 56;
			case "A":
				return 48;
			case "B":
				return 40;
			case "C":
				return 32;
			case "D":
				return 24;
			case "E":
				return 16;
			default:
				return 0;
			}
		default:
			return 0;
		}
	}
}
