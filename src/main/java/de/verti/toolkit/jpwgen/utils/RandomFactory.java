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
 *
 */
package de.verti.toolkit.jpwgen.utils;

import de.verti.toolkit.jpwgen.IRandomFactory;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A singleton that provides various.
 */
public class RandomFactory implements IRandomFactory {

	// The class instance
	private static RandomFactory instance;

	private static final Logger LOGGER = Logger.getLogger(RandomFactory.class
			.getName());

	/**
	 * Constructor.
	 */
	private RandomFactory() {
	}

	/**
	 * Accessor to the instance.
	 *
	 * @return  the singleton instance
	 */
	public static RandomFactory getInstance() {

		if (instance == null) {
			instance = new RandomFactory();
		}

		return instance;
	}

	/**
	 * Create a two pseudo random generator by utilizing the <em>SecureRandom</em> class provided by SUN. Uses a two
	 * step procedure for feeding the generator seed with two separate SecureRandom instances.
	 *
	 * @param   algorithm  The algorithm used for creating the pseudo random generator
	 * @param   provider   the provider identifier
	 *
	 * @return  a seeded <em>SecureRandom</em>
	 *
	 * @throws  NoSuchAlgorithmException  DOCUMENT ME!
	 * @throws  NoSuchProviderException   DOCUMENT ME!
	 *
	 * @see     http ://java.sun.com/j2se/1.4.2/docs/api/java/security/SecureRandom.html
	 */
	@SuppressWarnings(
		{ "squid:S2629", "squid:S1226" }
	) // SONAR: log formatting is ok this way, parameter reuse is intended
	private SecureRandom initSecureRandom(final String algorithm, String provider) throws NoSuchAlgorithmException,
		NoSuchProviderException {

		if (LOGGER.isLoggable(Level.FINE)) {
			LOGGER.fine("Initializing random with: " + algorithm + " : " + provider);
		}

		if (provider == null) {
			provider = PROVIDER_DEFAULT;
		}

		// Create a secure random number generator
		SecureRandom sr = SecureRandom.getInstance(algorithm, provider);

		// Get 1024 random bits
		byte[] bytes = new byte[1024 / 8];
		sr.nextBytes(bytes);

		// Create two secure number generators with the same seed
		int seedByteCount = 10;
		byte[] seed = sr.generateSeed(seedByteCount);

		sr = SecureRandom.getInstance(algorithm, provider);
		sr.setSeed(seed);

		SecureRandom sr2 = SecureRandom.getInstance(algorithm, provider);
		sr2.setSeed(seed);

		return sr2;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.rrze.idmone.utils.pwgen.IRandomFactory#getAlgorithms()
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Set<String> getAlgorithms() {
		Set<String> result = new HashSet<String>();

		// All providers
		Provider[] providers = Security.getProviders();

		for (int i = 0; i < providers.length; i++) {

			// Get services provided by each provider
			Set keys = providers[i].keySet();

			for (Iterator<String> it = keys.iterator(); it.hasNext();) {
				String key = it.next();
				String value = (String) providers[i].get(key);
				result.add(value);
			}
		}

		return result;
	}

	/**
	 * Returns a cleaned up version of the service providers.
	 *
	 * @return  a set of service providers that can be used for SecureRandom feed.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Set<String> getServiceProviders() {
		Set<String> result = new HashSet<String>();

		// All providers
		Provider[] providers = Security.getProviders();

		for (int i = 0; i < providers.length; i++) {

			// Get services provided by each provider
			Set keys = providers[i].keySet();

			for (Iterator<String> it = keys.iterator(); it.hasNext();) {
				String key = it.next();
				key = key.split(" ")[0]; //$NON-NLS-1$

				if (key.startsWith(ALG_PARSE_STRING)) {

					// Strip the alias
					key = key.substring(10);
				}

				int ix = key.indexOf('.');
				result.add(key.substring(0, ix));
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.rrze.idmone.utils.pwgen.IRandomFactory#getServiceProviderFor(java.
	 * lang.String)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Set<String> getServiceProviderFor(final String type) {
		Set<String> result = new HashSet<String>();

		Provider[] providers = Security.getProviders();

		for (int i = 0; i < providers.length; i++) {
			// Get services provided by each provider

			Set keys = providers[i].keySet();

			for (Iterator it = keys.iterator(); it.hasNext();) {
				String key = (String) it.next();
				key = key.split(" ")[0]; //$NON-NLS-1$

				if (key.startsWith(type + ".")) //$NON-NLS-1$
				{
					result.add(key.substring(type.length() + 1));
				} else if (key.startsWith(ALG_PARSE_STRING + type + ".")) //$NON-NLS-1$
				{

					// This is an alias
					result.add(key.substring(type.length() + 11));
				}
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.rrze.idmone.utils.pwgen.IRandomFactory#getProviders()
	 */
	@Override
	public Provider[] getProviders() {
		return Security.getProviders();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.rrze.idmone.utils.pwgen.IRandomFactory#getRandom()
	 */
	@Override
	public Random getRandom() {
		return new Random(System.currentTimeMillis());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.rrze.idmone.utils.pwgen.IRandomFactory#getRandom(long)
	 */
	@Override
	public Random getRandom(final long seed) {
		return new Random(seed);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.rrze.idmone.utils.pwgen.IRandomFactory#getSecureRandom()
	 */
	@Override
	public Random getSecureRandom() throws NoSuchAlgorithmException, NoSuchProviderException {
		return initSecureRandom(ALG_SHA1PRNG, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.rrze.idmone.utils.pwgen.IRandomFactory#getSecureRandom()
	 */
	@Override
	public Random getSimpleSecureRandom() throws NoSuchAlgorithmException, NoSuchProviderException {

		// Get 1024 random bits
		byte[] bytes = new byte[1024 / 8];
		Random r = new Random(System.currentTimeMillis());
		r.nextBytes(bytes);

		// Create a secure random number generator
		SecureRandom sr = new SecureRandom(bytes);

		// Create two secure number generators with the same seed
		int seedByteCount = 10;
		byte[] seed = sr.generateSeed(seedByteCount);

		sr = new SecureRandom(seed);
		sr.setSeed(seed);

		return sr;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.rrze.idmone.utils.pwgen.IRandomFactory#getSecureRandom(java.lang.String
	 * )
	 */
	@Override
	public Random getSecureRandom(final String algorithm) throws NoSuchAlgorithmException, NoSuchProviderException {
		return initSecureRandom(algorithm, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.rrze.idmone.utils.pwgen.IRandomFactory#getSecureRandom(java.lang.String
	 * , java.lang.String)
	 */
	@Override
	public Random getSecureRandom(final String algorithm, final String provider) throws NoSuchAlgorithmException,
		NoSuchProviderException {
		return initSecureRandom(algorithm, provider);
	}

}
