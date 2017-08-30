package de.verti.toolkit.jpwgen.test;

import org.apache.commons.lang3.time.StopWatch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.verti.toolkit.jpwgen.IPasswordPolicy;
import de.verti.toolkit.jpwgen.flags.PwGeneratorFlagBuilder;
import de.verti.toolkit.jpwgen.impl.PasswordPolicy;
import de.verti.toolkit.jpwgen.utils.RandomFactory;

public class PwGeneratorDefaultTest extends PwGeneratorTest {

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
	public void defaultTest() {

		int numPasswords = 15;
		int passLength = 10;

		System.out.println("DEFAULT TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		//"-N " + numPasswords + " -M 200 -m 1 -y  -q 1 -k  -s " + passLength + " -i  -j -r";

		PwGeneratorFlagBuilder flags = new PwGeneratorFlagBuilder();
		flags
				.setOnly1Capital()
				.setOnly1Digit()
				.setOnly1Symbol()
				.setDoNotEndWithSymbol()
				.setDoNotStartsWithSymbol()
				.setDoNotStartWithDigit();

		IPasswordPolicy passwordPolicy = new PasswordPolicy(
				passLength, passLength, 200, flags.build(), 
				RandomFactory.getInstance().getRandom());

		process(this.getClass().getSimpleName(), passwordPolicy, numPasswords);

		stopWatch.stop();
		System.out.println("\nDEFAULT TESTS FINISHED Runtime:"
				+ stopWatch.toString() + "\n");

	}

}