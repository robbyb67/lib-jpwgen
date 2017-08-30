package de.verti.toolkit.jpwgen.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.verti.toolkit.jpwgen.IPasswordFilter;
import de.verti.toolkit.jpwgen.IPasswordPolicy;
import de.verti.toolkit.jpwgen.flags.PwGeneratorFlagBuilder;
import de.verti.toolkit.jpwgen.impl.PasswordPolicy;
import de.verti.toolkit.jpwgen.impl.SimpleRegexFilter;
import de.verti.toolkit.jpwgen.utils.RandomFactory;

public class PwGeneratorFilterTest extends PwGeneratorDefaultTest {
	@BeforeClass
	public void setUp() {
		System.out.println("======================== "
				+ this.getClass().getSimpleName()
				+ " ================================");
	}

	@AfterClass
	public void finish() {
		System.out.println("======================== "
				+ this.getClass().getSimpleName()
				+ " ================================");
	}

	@Test(groups = { "regex" }, invocationCount = 1)
	public void reducedSymbolsTest() {

		int numPasswords = 30;
		int passLength = 8;

		System.out
				.println("USER REGEX FILTER TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		//"-N " + numPasswords + " -M 500 -m 1 -s " + passLength + " -z -r"

		PwGeneratorFlagBuilder flags = new PwGeneratorFlagBuilder();
		flags.setOnly1Capital().setIncludeReducedSymbols();
		
		List<IPasswordFilter> filters = new ArrayList<IPasswordFilter>();
		filters.add(new SimpleRegexFilter("anyLetter", "[\\w]+", true, false));

		IPasswordPolicy passwordPolicy = new PasswordPolicy(
				passLength, passLength, 500, flags.build(), 
				RandomFactory.getInstance().getRandom());
		passwordPolicy.getFilters().addAll(filters);
		
		process(this.getClass().getSimpleName(), passwordPolicy, numPasswords);

		stopWatch.stop();
		System.out.println("\nUSER REGEX FILTER TEST FINISHED Runtime:"
				+ stopWatch.toString() + "\n");
	}
}
