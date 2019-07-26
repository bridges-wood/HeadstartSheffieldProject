
/**
 * Object to store the preferences that the user inputs, this acts as a
 * intermediary between the JSON and the code.
 * 
 * @author Max
 */
public class Preferences {
	public String subject;
	public Qualification[] qualifications; // Need to check interpretation between string and this structure.
	public double leagueTablePref;
	public double studentSatisfactionPref;
	public double employabilityPref;
	public double researchQualityPref;
	public double studentToStaffPref;
	public double costOfLivingPref;
	public double internationalStudentPref;
	public double distanceFromHomePref;
	public int UCASpoints;
}
