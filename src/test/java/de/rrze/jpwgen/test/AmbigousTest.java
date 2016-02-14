package de.rrze.jpwgen.test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Random;

import org.apache.commons.lang3.time.StopWatch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.rrze.jpwgen.IPasswordPolicy;
import de.rrze.jpwgen.flags.PwGeneratorFlagBuilder;
import de.rrze.jpwgen.impl.PasswordPolicy;
import de.rrze.jpwgen.utils.RandomFactory;

// Error reported for:
//--digits --ambiguous
//--capitalize --max-attempts 100 --number 10 --size 8 
public class AmbigousTest extends PwGeneratorTest {

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

	@Test(groups = { "default" }, invocationCount = 100)
	public void ambigousTest() {

		int numPasswords = 10;

		System.out.println("AMBIGOUS TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		//--digits --ambiguous --capitalize --max-attempts 100 --size 8
		PwGeneratorFlagBuilder flags = new PwGeneratorFlagBuilder();
		flags.setIncludeDigits().setFilterAmbiguous().setIncludeCapitals();

		Random random = null;
		try {
			random = RandomFactory.getInstance().getSecureRandom();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			random = RandomFactory.getInstance().getRandom();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			random = RandomFactory.getInstance().getRandom();
		}
		IPasswordPolicy passwordPolicy = new PasswordPolicy(8, 8, 100,
				flags.build(), random);

		process(this.getClass().getSimpleName(), passwordPolicy, numPasswords);

		stopWatch.stop();
		System.out.println("\nAMBIGOUS TEST FINISHED Runtime:"
				+ stopWatch.toString() + "\n");
	}

}
