package de.verti.toolkit.jpwgen.test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.apache.commons.lang3.time.StopWatch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.verti.toolkit.jpwgen.IPasswordPolicy;
import de.verti.toolkit.jpwgen.IPwDefConstants;
import de.verti.toolkit.jpwgen.flags.PwGeneratorFlagBuilder;
import de.verti.toolkit.jpwgen.impl.PasswordPolicy;
import de.verti.toolkit.jpwgen.utils.RandomFactory;

public class PwGeneratorSecureRandomTest extends PwGeneratorTest {

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

	@Test(groups = { "secure" }, invocationCount = 20)
	public void secureTest() {

		int numPasswords = 20;
		int passLength = 12;

		System.out
				.println("SECURE TEST STARTED: Generating password with secure random:");

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		//"-N " + numPasswords + " -s " + passLength + " -C -Y -S SHA1PRNG SUN";

		PwGeneratorFlagBuilder flags = new PwGeneratorFlagBuilder();
		flags
				.setDisableConsecSymbols()
				.setIncludeReducedSymbols();
		
		try {
			IPasswordPolicy passwordPolicy = new PasswordPolicy(
					passLength, passLength, IPwDefConstants.DEFAULT_MAX_ATTEMPTS, 
					flags.build(), 
					RandomFactory.getInstance().getSecureRandom("SHA1PRNG", "SUN"));

			process(this.getClass().getSimpleName(), passwordPolicy, numPasswords);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		
		stopWatch.stop();
		System.out.println("\nSECURE TEST FINISHED:" + stopWatch.toString()
				+ "\n");
	}

}
