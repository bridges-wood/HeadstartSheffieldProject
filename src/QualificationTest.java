import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

class QualificationTest {

	@Test
	void testParseQualificationArray() {
		Gson g = new Gson();
		Qualification a = new Qualification("A-Level", "History of Art", "A");
		Qualification b = new Qualification("A-Level", "Mathematics", "D");
		Qualification[] qs = {a,b};
		String json = g.toJson(qs);
		Qualification[] qsOut = g.fromJson(json, Qualification[].class);
		assertTrue(qsOut[0].grade.equals("A"));
		assertTrue(qsOut[0].type.equals("A-Level"));
		assertTrue(qsOut[0].subject.equals("History of Art"));
		assertTrue(qsOut[1].grade.equals("D"));
		assertTrue(qsOut[1].type.equals("A-Level"));
		assertTrue(qsOut[1].subject.equals("Mathematics"));
	}

}
