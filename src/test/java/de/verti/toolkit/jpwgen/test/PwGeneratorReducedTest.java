package de.verti.toolkit.jpwgen.test;

import org.apache.commons.lang3.time.StopWatch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.verti.toolkit.jpwgen.IPasswordPolicy;
import de.verti.toolkit.jpwgen.flags.PwGeneratorFlagBuilder;
import de.verti.toolkit.jpwgen.impl.PasswordPolicy;
import de.verti.toolkit.jpwgen.utils.RandomFactory;

public class PwGeneratorReducedTest extends PwGeneratorTest {

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

	@Test(groups = { "default" }, invocationCount = 20)
	public void reducedSymbolsTest() {

		int numPasswords = 30;
		int passLength = 8;

		System.out
				.println("REDUCED SYMBOL TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		//"-N " + numPasswords + " -M 500 -m 1 -s " + passLength + " -z -r";

		PwGeneratorFlagBuilder flags = new PwGeneratorFlagBuilder();
		flags
				.setOnly1Capital()
				.setIncludeReducedSymbols();
		
		IPasswordPolicy passwordPolicy = new PasswordPolicy(
				passLength, passLength, 500, flags.build(), 
				RandomFactory.getInstance().getRandom());

		process(this.getClass().getSimpleName(), passwordPolicy, numPasswords);

		stopWatch.stop();
		System.out.println("\nREDUCED SYMBOL TEST FINISHED Runtime:"
				+ stopWatch.toString() + "\n");
	}
}
