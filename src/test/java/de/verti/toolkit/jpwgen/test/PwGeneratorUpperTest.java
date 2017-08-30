package de.verti.toolkit.jpwgen.test;

import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.verti.toolkit.jpwgen.IPasswordPolicy;
import de.verti.toolkit.jpwgen.flags.PwGeneratorFlagBuilder;
import de.verti.toolkit.jpwgen.impl.PasswordPolicy;
import de.verti.toolkit.jpwgen.utils.RandomFactory;

public class PwGeneratorUpperTest extends PwGeneratorTest {

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

	@Test(groups = { "default" }, invocationCount = 50)
	public void upperSymbolsTest() {

		int numPasswords = 30;
		int passLength = 8;

		System.out.println("UPPER TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		// "-N " + numPasswords + " -M 500 -m 1 -s " + passLength + " -z -r -c";
		PwGeneratorFlagBuilder flags = new PwGeneratorFlagBuilder();
		flags
				.setOnly1Capital()
				.setIncludeCapitals()
				.setIncludeReducedSymbols();
		
		IPasswordPolicy passwordPolicy = new PasswordPolicy(
				passLength, passLength, 500, flags.build(), 
				RandomFactory.getInstance().getRandom());

		List<String> passwords = process(this.getClass().getSimpleName(),
				passwordPolicy, numPasswords);

		for (String password : passwords) {
			char[] cs = password.toCharArray();
			boolean found = false;
			for (int i = 0; i < cs.length; i++) {
				if (Character.isUpperCase(cs[i]))
					found = true;
			}
			if (!found)
				throw new RuntimeException("No upper case letters");
		}

		stopWatch.stop();
		System.out.println("\nUPPER TEST FINISHED Runtime:"
				+ stopWatch.toString() + "\n");
	}
}
