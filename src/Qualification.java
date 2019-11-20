import com.google.gson.Gson;

/**
 * Object containing all relevant information to qualifications.
 * 
 * @author Max
 */
public class Qualification {
	public String type;
	public String subject;
	public String grade;
	
	public Qualification(String type, String subject, String grade) {
		this.type = type;
		this.subject = subject;
		this.grade = grade;
	}

	public static Qualification[] parseQualificationArray(String s) {
		Gson g = new Gson();
		return g.fromJson(s, Qualification[].class);
	}

	public static int calculateUCASpoints(Qualification q) {
		switch (q.type) {
		case "A-level":
			switch (q.grade) {
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
