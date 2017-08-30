/*
 * RRZEPwGen, developed as a part of the IDMOne project at RRZE. Copyright 2007, RRZE, and individual contributors as
 * indicated by the @author tags. See the copyright.txt file in the distribution for a full listing of individual
 * contributors. This product includes software developed by the Apache Software Foundation http://www.apache.org/
 *
 * This is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any
 * later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this software; if not, write to
 * the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA, or see the FSF site:
 * http://www.fsf.org.
 *
 * This software uses code and ideas from:     http://sourceforge.net/projects/pwgen/     Copyright (C) 2001,2002 by
 * Theodore Ts'o
 *
 */
package de.verti.toolkit.jpwgen.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.verti.toolkit.jpwgen.IDefaultFilter;
import de.verti.toolkit.jpwgen.IPasswordFilter;
import de.verti.toolkit.jpwgen.IPasswordPolicy;
import de.verti.toolkit.jpwgen.IProgressListener;
import de.verti.toolkit.jpwgen.IPwDefConstants;
import de.verti.toolkit.jpwgen.IPwGenerator;
import de.verti.toolkit.jpwgen.utils.Messages;
import de.verti.toolkit.jpwgen.utils.RandomFactory;


/**
 * The program is started from this class. It performs the actual password generation. The idea of this class is to
 * generate passwords that are relatively easily to remember but at the same time complex enough. It utilizes a
 * predefined set of vowels and consonants that are brought together by an iterative algorithm. The concept of dipthong
 * is also used which can be used as an additional flag for a consonant or a vowel. Typical examples of dipthongs are
 * <em>ei</em> for vowels and <em>th</em> for consonants.
 *
 * <p>Various algorithms for random generation can be used for feeding the process of password generation. This ensures
 * unique password creation.</p>
 *
 * <p>By default two filters are registered in the PwGenerator. The first one is an empty black list filter. It can be
 * used to filter out forbidden predefined passwords. The second one is based on regular expressions and uses frequently
 * utilized rules for filtering passwords such as the number of contained symbols, digits and so on. See the help for a
 * detailed description.</p>
 *
 * <table border="1px">
 *   <caption>CLI options comparison to original pwgen</caption>
 *
 *   <tbody>
 *     <tr>
 *       <th colspan="3">pwgen</th>
 *       <th colspan="3">jpwgen</th>
 *     </tr>
 *     <tr>
 *       <th>short</th>
 *       <th>long</th>
 *       <th>description</th>
 *       <th>short</th>
 *       <th>long</th>
 *       <th>description</th>
 *     </tr>
 *     <tr>
 *       <td>-0</td>
 *       <td>--no-digits</td>
 *       <td>Don't include numbers in the generated passwords.</td>
 *       <td>-O</td>
 *       <td>--no-digits</td>
 *       <td>Don't include numbers in the generated passwords.</td>
 *     </tr>
 *     <tr>
 *       <td>-n</td>
 *       <td>--digits</td>
 *       <td>Include at least one number in the password</td>
 *       <td>-n</td>
 *       <td>--digits</td>
 *       <td>Include at least one number in the password</td>
 *     </tr>
 *     <tr>
 *       <td>-y</td>
 *       <td>--symbols</td>
 *       <td>Include at least one special symbol in the password</td>
 *       <td>-y</td>
 *       <td>--symbols</td>
 *       <td>Include at least one special symbol in the password</td>
 *     </tr>
 *     <tr>
 *       <td>-z</td>
 *       <td>--reduced-symbols</td>
 *       <td>Include at least one special symbol from the reduced set in the password</td>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *     </tr>
 *     <tr>
 *       <td>-1</td>
 *       <td>-</td>
 *       <td>Print the generated passwords one per line.</td>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *     </tr>
 *     <tr>
 *       <td>-a</td>
 *       <td>--alt-phonic</td>
 *       <td>This option doesn't do anything special; it is present only for backwards compatibility.</td>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *     </tr>
 *     <tr>
 *       <td>-B</td>
 *       <td>--ambiguous</td>
 *       <td>Don't include ambiguous characters in the password</td>
 *       <td>-B</td>
 *       <td>--ambiguous</td>
 *       <td>Don't include ambiguous characters in the password</td>
 *     </tr>
 *     <tr>
 *       <td>-c</td>
 *       <td>--capitalize</td>
 *       <td>Include at least one capital letter in the password. This is the default if the standard output is a tty
 *         device.</td>
 *       <td>-c</td>
 *       <td>--capitalize</td>
 *       <td>Include at least one capital letter in the password. This is the default if the standard output is a tty
 *         device.</td>
 *     </tr>
 *     <tr>
 *       <td>-s</td>
 *       <td>--secure</td>
 *       <td>Generate completely random passwords</td>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *     </tr>
 *     <tr>
 *       <td>-h</td>
 *       <td>--help</td>
 *       <td>Print a help message</td>
 *       <td>-h</td>
 *       <td>--help</td>
 *       <td>Print a help message</td>
 *     </tr>
 *     <tr>
 *       <td>-C</td>
 *       <td></td>
 *       <td>Print the generated passwords in columns</td>
 *       <td>-C</td>
 *       <td>--columns</td>
 *       <td>Print the generated passwords in columns</td>
 *     </tr>
 *     <tr>
 *       <td>-v</td>
 *       <td>--no-vowels</td>
 *       <td>Use sha1 hash of given file as a (not so) random generator</td>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *     </tr>
 *     <tr>
 *       <td>-H</td>
 *       <td>--sha1=path/to/file[#seed]</td>
 *       <td>Do not use any vowels so as to avoid accidental nasty words</td>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-b</td>
 *       <td>--start-no-small-letter</td>
 *       <td>Generates password starting with a character different than a small letter</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-d</td>
 *       <td>--end-no-small-letter</td>
 *       <td>Generates password ending with a character different than a small letter</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-D</td>
 *       <td>--allow-ambiguous</td>
 *       <td>Allow ambiguous characters in the password</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-e</td>
 *       <td>--start-no-uppercase-letter</td>
 *       <td>Generates password starting with a character different than a uppercase letter</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-f</td>
 *       <td>--end-no-uppercase-letter</td>
 *       <td>Generates password ending with a character different than a uppercase letter</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-g</td>
 *       <td>--end-no-digit</td>
 *       <td>Generates password ending with a character different than a digit</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-i</td>
 *       <td>--start-no-digit-letter</td>
 *       <td>Generates password starting with a character different than a digit</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-j</td>
 *       <td>--start-no-symbol-letter</td>
 *       <td>Generates password starting with a character different than a symbol</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-k</td>
 *       <td>--end-no-symbol</td>
 *       <td>Generates password ending with a character different than a symbol</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-l</td>
 *       <td>--list-sr-providers</td>
 *       <td>Lists the available security service providers for SecureRandom and exits</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-L</td>
 *       <td>--list-providers</td>
 *       <td>Lists all available security providers and algorithms</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-m</td>
 *       <td>--one-upercase</td>
 *       <td>Generates password containing exactly one uppercase letter</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-M</td>
 *       <td>--max-attempts &lt;arg&gt;</td>
 *       <td>Sets the maximum number of attempts for generating a password with the provided policies</td>
 *     </tr>
 *     <tr>
 *       <td>second parameter</td>
 *       <td>second parameter</td>
 *       <td></td>
 *       <td>-N</td>
 *       <td>--number &lt;arg&gt;</td>
 *       <td>The number of passwords to be generated</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-o</td>
 *       <td>--single-symbol</td>
 *       <td>Generates password containing exactly one symbol</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-p</td>
 *       <td>--two-symbol</td>
 *       <td>Generates password containing at least two symbols</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-q</td>
 *       <td>--single-digit</td>
 *       <td>Generates password containing exactly one digit</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-r</td>
 *       <td>--random</td>
 *       <td>Use simple random for password generation</td>
 *     </tr>
 *     <tr>
 *       <td>first parameter</td>
 *       <td>first parameter</td>
 *       <td>The length of the generated password</td>
 *       <td>-s</td>
 *       <td>--size &lt;arg&gt;</td>
 *       <td>The length of the generated password</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-S</td>
 *       <td>--set-algorithm &lt;arg&gt;</td>
 *       <td>set-algorithm</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-t</td>
 *       <td>--term-width &lt;arg&gt;</td>
 *       <td>Sets the character width of the jpwgen terminal</td>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <td></td>
 *       <td></td>
 *       <td>-u</td>
 *       <td>--two-digits</td>
 *       <td>Generates password containing at least two digits</td>
 *     </tr>
 *   </tbody>
 * </table>
 *
 * <h3>Example:</h3>
 * <code><br>
 * String flags = "-N 50 -M 100 -y -s 16 -m -o -q ";<br>
 * <br>
 * flags = BlankRemover.itrim(flags);<br>
 * String[] ar = flags.split(" ");<br>
 * </code>
 *
 * <p>List&lt;String&gt; passwords = PwGenerator.process(ar);<br>
 * int count = 0;<br>
 * System.out.printf("\n");<br>
 * for (Iterator iter = passwords.iterator(); iter.hasNext();)<br>
 * {<br>
 * <br>
 * String element = (String) iter.next();<br>
 * System.out.printf("%3d Password: * %s\n", ++count, element);<br>
 * <br>
 * </p>
 *
 * @author  unrz205
 */
public class PwGenerator implements IPwDefConstants, IDefaultFilter, IPwGenerator {

	// A static list of predefined vowels and consonants dipthongs. Suitable for
	// English speaking people.
	// This can be exchanged or extended with a different one if needed.
	public static final PwElement[] PW_ELEMENTS = {
		new PwElement("a", VOWEL), new PwElement("ae", VOWEL | DIPTHONG), //$NON-NLS-1$ //$NON-NLS-2$
		new PwElement("ah", VOWEL | DIPTHONG), //$NON-NLS-1$
		new PwElement("ai", VOWEL | DIPTHONG), //$NON-NLS-1$
		new PwElement("b", CONSONANT), new PwElement("c", CONSONANT), //$NON-NLS-1$ //$NON-NLS-2$
		new PwElement("ch", CONSONANT | DIPTHONG), //$NON-NLS-1$
		new PwElement("d", CONSONANT), new PwElement("e", VOWEL), //$NON-NLS-1$ //$NON-NLS-2$
		new PwElement("ee", VOWEL | DIPTHONG), //$NON-NLS-1$
		new PwElement("ei", VOWEL | DIPTHONG), //$NON-NLS-1$
		new PwElement("f", CONSONANT), new PwElement("g", CONSONANT), //$NON-NLS-1$ //$NON-NLS-2$
		new PwElement("gh", CONSONANT | DIPTHONG | NOT_FIRST), //$NON-NLS-1$
		new PwElement("h", CONSONANT), new PwElement("i", VOWEL), //$NON-NLS-1$ //$NON-NLS-2$
		new PwElement("ie", VOWEL | DIPTHONG), //$NON-NLS-1$
		new PwElement("j", CONSONANT), new PwElement("k", CONSONANT), //$NON-NLS-1$ //$NON-NLS-2$
		new PwElement("l", CONSONANT), new PwElement("m", CONSONANT), //$NON-NLS-1$ //$NON-NLS-2$
		new PwElement("n", CONSONANT), //$NON-NLS-1$
		new PwElement("ng", CONSONANT | DIPTHONG | NOT_FIRST), //$NON-NLS-1$
		new PwElement("o", VOWEL), new PwElement("oh", VOWEL | DIPTHONG), //$NON-NLS-1$ //$NON-NLS-2$
		new PwElement("oo", VOWEL | DIPTHONG), //$NON-NLS-1$
		new PwElement("p", CONSONANT), //$NON-NLS-1$
		new PwElement("ph", CONSONANT | DIPTHONG), //$NON-NLS-1$
		new PwElement("qu", CONSONANT | DIPTHONG), //$NON-NLS-1$
		new PwElement("r", CONSONANT), new PwElement("s", CONSONANT), //$NON-NLS-1$ //$NON-NLS-2$
		new PwElement("sh", CONSONANT | DIPTHONG), new PwElement("t", CONSONANT), //$NON-NLS-1$
		new PwElement("th", CONSONANT | DIPTHONG), //$NON-NLS-1$
		new PwElement("u", VOWEL), new PwElement("v", CONSONANT), //$NON-NLS-1$ //$NON-NLS-2$
		new PwElement("w", CONSONANT), new PwElement("x", CONSONANT), //$NON-NLS-1$ //$NON-NLS-2$
		new PwElement("y", CONSONANT), new PwElement("z", CONSONANT)
	}; //$NON-NLS-1$ //$NON-NLS-2$

	private static final Logger LOGGER = Logger.getLogger(PwGenerator.class
			.getName());

	private final IPasswordFilter defaultFilter = new DefaultFilter();

	private Map<String, IPasswordFilter> filters = new HashMap<String, IPasswordFilter>();

	private IPasswordPolicy passwordPolicy;

	public PwGenerator(final IPasswordPolicy passwordPolicy) {
		this.passwordPolicy = passwordPolicy;
		defaultFilter.getBlacklist().addAll(passwordPolicy.getBlackList());
		filters.put(defaultFilter.getId(), defaultFilter);
	}

	public IPasswordPolicy getPasswordPolicy() {
		return passwordPolicy;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.verti.toolkit.jpwgen.impl.IPwGenerator#addFilter(de.verti.toolkit.jpwgen.IPasswordFilter
	 * )
	 */

	@Override
	public synchronized IPasswordFilter addFilter(final IPasswordFilter filter) {
		return filters.put(filter.getId(), filter);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.verti.toolkit.jpwgen.impl.IPwGenerator#removeFilter(de.verti.toolkit.jpwgen.IPasswordFilter
	 * )
	 */

	@Override
	public synchronized IPasswordFilter removeFilter(final IPasswordFilter filter) {
		return filters.remove(filter.getId());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.verti.toolkit.jpwgen.impl.IPwGenerator#removeFilter(java.lang.String)
	 */

	@Override
	public synchronized IPasswordFilter removeFilter(final String id) {
		return filters.remove(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.verti.toolkit.jpwgen.impl.IPwGenerator#validate(java.lang.String)
	 */
	@Override
	public synchronized Map<String, Map<String, String>> validate(final String password) {

		Long passwordFlags = passwordPolicy.getFlags();

		Map<String, Map<String, String>> problems = new HashMap<String, Map<String, String>>();

		Set<String> filterIDs = filters.keySet();

		if (password.length() > passwordPolicy.getMaxPwLength()) {
			Map<String, String> prob = new HashMap<String, String>();
			prob.put("maxPwLength",
				Integer.toString(passwordPolicy.getMaxPwLength()));
			prob.put("maxPwLength.explain", Integer.toString(password.length()));
			problems.put("PasswordPolicy", prob);
		}

		if (password.length() < passwordPolicy.getMinPwLength()) {
			Map<String, String> prob = new HashMap<String, String>();
			prob.put("minPwLength",
				Integer.toString(passwordPolicy.getMinPwLength()));
			prob.put("minPwLength.explain", Integer.toString(password.length()));
			problems.put("PasswordPolicy", prob);
		}

		for (Iterator<String> iter = filterIDs.iterator(); iter.hasNext();) {
			String filterId = (String) iter.next();
			IPasswordFilter filter = filters.get(filterId);

			Map<String, String> checked = filter
					.filter(passwordFlags, password);

			if (checked.size() > 0) {
				problems.put(filterId, checked);
			}
		}

		List<IPasswordFilter> ppFilters = passwordPolicy.getFilters();

		for (IPasswordFilter ppFilter : ppFilters) {
			Map<String, String> checked = ppFilter.filter(passwordFlags,
					password);

			if (checked.size() > 0) {
				problems.put(ppFilter.getId(), checked);
			}
		}

		return problems;
	}

	private Long preprocess(final int passwordLength, Long passwordFlags) {

		if (passwordLength <= 2) {
			passwordFlags &= ~PW_CAPITALS;
			LOGGER.fine(Messages.getString("PwGenerator.WARN_PL_UPERCASE_OFF")); //$NON-NLS-1$
		}

		if (passwordLength <= 1) {
			passwordFlags &= ~PW_DIGITS;
			LOGGER.fine(Messages.getString("PwGenerator.WARN_PL_DIGITS_OFF")); //$NON-NLS-1$
		}

		if (passwordLength <= 2) {
			passwordFlags &= ~PW_SYMBOLS;
			LOGGER.fine(Messages.getString("PwGenerator.WARN_PL_SYMBOLS_OFF")); //$NON-NLS-1$
		}

		if (passwordLength <= 2) {
			passwordFlags &= ~PW_SYMBOLS_REDUCED;
			LOGGER.fine(Messages
				.getString("PwGenerator.WARN_PL_SYMBOLS_REDUCED_OFF")); //$NON-NLS-1$
		}

		return passwordFlags;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.verti.toolkit.jpwgen.impl.IPwGenerator#generatePasswords(int, int,
	 * de.verti.toolkit.jpwgen.IProgressListener)
	 */
	@Override
	@SuppressWarnings("squid:S3776") // SONAR: reducing complexity would make code harder to read
	public synchronized List<String> generate(final int passwordCount,
		final int iterationsCount,
		final IProgressListener progressListener) {

		Set<String> passwords = new HashSet<String>();

		try {
			int numberOfPasswords = DEFAULT_NUMBER_OF_PASSWORDS;

			if (passwordCount > 0) {
				numberOfPasswords = passwordCount;
			}

			int iterations = DEFAULT_MAX_ITERATIONS;

			if (iterationsCount > 0) {
				iterations = iterationsCount;
			}

			// -------------------------------------------------------------------

			int attempts = 0;

			while (passwords.size() != numberOfPasswords
					&& attempts < iterations) {
				String password = generate();

				if (password != null) {
					passwords.add(password);

					if (progressListener != null) {
						boolean stopped = progressListener.progress(
								passwords.size(), numberOfPasswords);

						if (stopped) {
							break;
						}
					}

					// in case we are in a back up loop(refill so to say)
					if (passwords.size() >= numberOfPasswords) {
						break;
					}
				}

				attempts++;
			}
		} catch (Exception e) {
			LOGGER.warning(Messages.getString("PwGenerator.NUM_FORM_ERROR") + e.getLocalizedMessage()); //$NON-NLS-1$
		}

		List<String> result = new ArrayList<String>();
		result.addAll(passwords);

		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.verti.toolkit.jpwgen.impl.IPwGenerator#generate(int, int, int,
	 * java.util.Random)
	 */

	@Override
	public synchronized String generate() {

		int minPwLength = passwordPolicy.getMinPwLength();
		int maxPwLength = passwordPolicy.getMaxPwLength();
		Long passwordFlags = passwordPolicy.getFlags();
		int maxAttempts = passwordPolicy.getMaxAttempts();
		Random random = passwordPolicy.getRandom();

		passwordFlags = preprocess(minPwLength, passwordFlags);

		if (maxAttempts < 1) {
			maxAttempts = DEFAULT_MAX_ATTEMPTS;
		}

		random = getRandom(random);

		String password = null;

		for (int i = 0; i < maxAttempts; i++) {

			int passwordLength = minPwLength;

			if (minPwLength != maxPwLength) {

				// +1 because nextInt is exclusive
				passwordLength = random.nextInt(maxPwLength - minPwLength + 1)
					+ minPwLength;
			}

			password = phonemes(passwordLength, passwordFlags, random);

			if (validate(password).size() > 0) {
				password = null;

				continue;
			}

			if (password != null) {
				break;
			}

			if (LOGGER.isLoggable(Level.FINE)) {
				StringBuilder logMsg = new StringBuilder(Messages.getString("PwGenerator.TRACE_ATTEMPT"))
						.append(Messages.getString("PwGenerator.TRACE_ATTEMPT_GENERATE")).append(passwordFlags);
				LOGGER.fine(logMsg.toString());
			}
		}

		return password;
	}

	private Random getRandom(Random random) {

		if (random == null) {
			random = RandomFactory.getInstance().getRandom();
		}

		return random;
	}

	/**
	 * The real password generation is performed in this method.
	 *
	 * @param   size      the length of the password
	 * @param   pw_flags  the settings for the password
	 * @param   random    DOCUMENT ME!
	 *
	 * @return  the newly created password
	 */
	@SuppressWarnings(
		{ "squid:S3776", "squid:S1066" }
	) // SONAR: reducing complexity and merged if statements would make code harder to read
	private synchronized String phonemes(final int size, final Long pw_flags, final Random random) {
		int c, i, len, flags;
		Long feature_flags;
		int prev, should_be;
		boolean first;
		String str;
		char ch;
		StringBuilder buf = new StringBuilder();

		do {
			buf.delete(0, buf.length());
			feature_flags = pw_flags;
			c = 0;
			prev = 0;
			first = true;
			should_be = random.nextBoolean() ? VOWEL : CONSONANT;

			while (c < size) {

				i = random.nextInt(PW_ELEMENTS.length);
				str = PW_ELEMENTS[i].getValue();
				len = str.length();
				flags = PW_ELEMENTS[i].getType();

				/* Filter on the basic type of the next element */
				if ((flags & should_be) == 0) {
					continue;
				}

				/* Handle the NOT_FIRST flag */
				if (first && ((flags & NOT_FIRST) != 0)) {
					continue;
				}

				/* Don't allow VOWEL followed a Vowel/Dipthong pair */
				if (((prev & VOWEL) != 0) && ((flags & VOWEL) != 0)
						&& ((flags & DIPTHONG) != 0)) {
					continue;
				}

				/* Don't allow us to overflow the buffer */
				if (len > size - c) {
					continue;
				}

				/*
				 * OK, we found an element which matches our criteria, let's do it!
				 */
				buf.append(str);

				c += len;

				/* Time to stop? */
				if (c >= size) {

					break;
				}

				/*
				 * Handle PW_DIGITS
				 */
				if ((pw_flags & PW_DIGITS) != 0) {

					if (!first && (random.nextInt(10) < 2)) {

						do {
							ch = (Integer.valueOf(random.nextInt(10))).toString()
									.charAt(0);
						} while (((pw_flags & PW_AMBIGUOUS) != 0)
							&& (PW_AMBIGUOUS_SYMBOLS.indexOf(ch) != -1));

						c++;
						buf = buf.append(ch);
						feature_flags &= ~PW_DIGITS;

						first = true;
						prev = 0;
						should_be = random.nextBoolean() ? VOWEL : CONSONANT;

						continue;
					}
				}

				/* Handle PW_SYMBOLS */
				if ((pw_flags & PW_SYMBOLS) != 0) {

					if (!first && (random.nextInt(10) < 2)) {

						do {
							ch = PW_SPECIAL_SYMBOLS.charAt(random
									.nextInt(PW_SPECIAL_SYMBOLS.length()));
						} while (((pw_flags & PW_AMBIGUOUS) != 0)
							&& (PW_AMBIGUOUS_SYMBOLS.indexOf(ch) != -1));

						c++;
						buf = buf.append(ch);
						feature_flags &= ~PW_SYMBOLS;
					}
				} else if ((pw_flags & PW_SYMBOLS_REDUCED) != 0) {

					if (!first && (random.nextInt(10) < 2)) {

						do {
							ch = PW_SPECIAL_SYMBOLS_REDUCED.charAt(random
									.nextInt(PW_SPECIAL_SYMBOLS_REDUCED
										.length()));
						} while (((pw_flags & PW_AMBIGUOUS) != 0)
							&& (PW_AMBIGUOUS_SYMBOLS.indexOf(ch) != -1));

						c++;
						buf = buf.append(ch);
						feature_flags &= ~PW_SYMBOLS_REDUCED;
					}
				}

				/* Handle PW_UPPERS */
				if ((pw_flags & PW_CAPITALS) != 0) {

					if ((first || ((flags & CONSONANT) != 0))
							&& (random.nextInt(10) < 3)) {
						int lastChar = buf.length() - 1;
						char c1 = buf.charAt(lastChar);

						if (Character.isLetter(c1)) {
							buf.setCharAt(lastChar, Character.toUpperCase(c1));
							feature_flags &= ~PW_CAPITALS;
						}
					}
				}

				/* Handle the AMBIGUOUS flag */
				if ((pw_flags & PW_AMBIGUOUS) != 0) {
					int k = -1;

					for (int j = 0; j < PW_AMBIGUOUS_SYMBOLS.length(); j++) {
						k = buf.indexOf(String.valueOf(PW_AMBIGUOUS_SYMBOLS
									.charAt(j)));

						if (k != -1) {
							break;
						}
					}

					if (k != -1) {
						char curr = buf.charAt(k);
						buf.deleteCharAt(k);
						c = buf.length();

						if (Character.isUpperCase(curr)
								&& (pw_flags & PW_CAPITALS) != 0) {
							feature_flags |= PW_CAPITALS;
						}

						if (Character.isDigit(curr)
								&& (pw_flags & PW_DIGITS) != 0) {
							feature_flags |= PW_DIGITS;
						}

						if (PW_SPECIAL_SYMBOLS_REDUCED.contains(Character.toString(curr))
								&& (pw_flags & PW_SYMBOLS_REDUCED) != 0) {
							feature_flags |= PW_SYMBOLS_REDUCED;
						}

						if (PW_SPECIAL_SYMBOLS.contains(Character.toString(curr))
								&& (pw_flags & PW_SYMBOLS) != 0) {
							feature_flags |= PW_SYMBOLS;
						}
					}
				}

				/*
				 * OK, figure out what the next element should be
				 */
				if (should_be == CONSONANT) {
					should_be = VOWEL;
				} else { /* should_be == VOWEL */

					if (((prev & VOWEL) != 0) || ((flags & DIPTHONG) != 0)
							|| (random.nextInt(10) > 3)) {
						should_be = CONSONANT;
					} else {
						should_be = VOWEL;
					}
				}

				prev = flags;
				first = false;

			}
		} while ((feature_flags & (PW_CAPITALS | PW_DIGITS | PW_SYMBOLS | PW_SYMBOLS_REDUCED)) != 0);

		return buf.toString();
	}

}
