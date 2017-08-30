package de.verti.toolkit.jpwgen.test;

import java.lang.management.ManagementFactory;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.lang3.time.StopWatch;
import org.testng.annotations.Test;

import de.verti.toolkit.jpwgen.IPasswordPolicy;
import de.verti.toolkit.jpwgen.IPwGenerator;
import de.verti.toolkit.jpwgen.flags.PwGeneratorFlagBuilder;
import de.verti.toolkit.jpwgen.impl.PasswordPolicy;
import de.verti.toolkit.jpwgen.impl.PwGenerator;
import de.verti.toolkit.jpwgen.impl.SimpleRegexFilter;

public class ValidateTest {

	@Test(groups = { "instance" }, invocationCount = 30)
	public void conformTest() throws Exception {
		int numPasswords = 10;
		int passLength = 8;

		System.out.println("INSTANCE TEST STARTED: Generating passwords:");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		PwGeneratorFlagBuilder flags = new PwGeneratorFlagBuilder();
		flags.setIncludeDigits().setIncludeCapitals()
				.setIncludeReducedSymbols().setFilterAmbiguous()
				.setDoNotEndWithSymbol().setDoNotEndWithDigit()
				.setDisableConsecSymbols();

		IPasswordPolicy passwordPolicy = new PasswordPolicy(passLength,
				passLength, 0, flags.build(), null);
		passwordPolicy.addFilter(new SimpleRegexFilter("lala",
				"(?=.{8,})(?=.*?[^\\w\\s])(?=.*?[0-9])(?=.*?[A-Z]).*?[a-z].*",
				false, false));
		passwordPolicy.addFilter(new SimpleRegexFilter("lala1", "^a.*4.*",
				false, false));
		passwordPolicy.getBlackList().add("nazi");

		String password = "nazi4_#a";

		IPwGenerator pw = new PwGenerator(passwordPolicy);

		List<String> passwords = pw.generate(numPasswords, 0, null);

		assertLengthCount(this.getClass().getSimpleName(), passLength,
				numPasswords, passwords);

		/** part two */

		Map<String, Map<String, String>> validationResult = pw
				.validate(password);

		for (String filterId : validationResult.keySet()) {
			System.out.println(filterId);
			System.out.println(validationResult.get(filterId));
		}

		Assert.assertTrue(validationResult.size() > 0);

		stopWatch.stop();
		System.out.println("\nFLAG BUILDER TEST FINISHED Runtime:"
				+ stopWatch.toString() + "\n");

	}

	protected void assertLengthCount(String test, int passLength,
			int numPasswords, List<String> passwords) {

		int count = 0;
		for (Iterator<String> iter = passwords.iterator(); iter.hasNext();) {
			++count;
			String password = (String) iter.next();
			if (password.length() != passLength) {
				System.out
						.printf("#####========>%s %s %s %d Wrong number of characters: %d vs. %d for %s\n",
								ManagementFactory.getRuntimeMXBean().getName(),
								Thread.currentThread().getName(), test, count,
								password.length(), passLength, password);
				Assert.assertEquals(password.length(), passLength);
			}
			System.out.printf("%s %s %s, %3d  Password: %s -> %d\n",
					ManagementFactory.getRuntimeMXBean().getName(), Thread
							.currentThread().getName(), test, count, password,
					password.length());

		}
	}

}