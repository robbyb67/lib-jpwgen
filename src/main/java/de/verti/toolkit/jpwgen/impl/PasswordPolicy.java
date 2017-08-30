package de.verti.toolkit.jpwgen.impl;

import de.verti.toolkit.jpwgen.IDefaultFilter;
import de.verti.toolkit.jpwgen.IPasswordFilter;
import de.verti.toolkit.jpwgen.IPasswordPolicy;
import de.verti.toolkit.jpwgen.IPwDefConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PasswordPolicy implements IPasswordPolicy {

	private int minPwLength = IPwDefConstants.DEFAULT_PASSWORD_LENGTH;

	private int maxPwLength = IPwDefConstants.DEFAULT_PASSWORD_LENGTH;

	private int maxAttempts = IPwDefConstants.DEFAULT_MAX_ATTEMPTS;

	private Long flags = IDefaultFilter.DEFAULT_FLAGS;

	private Random random;

	private final List<IPasswordFilter> filters = new ArrayList<IPasswordFilter>();

	private final List<String> blackList = new ArrayList<String>();

	public PasswordPolicy() {
	}

	@SuppressWarnings("squid:S1226") // SONAR: Parameter reuse is intended
	public PasswordPolicy(int minPwLength,
		int maxPwLength,
		final int maxAttempts,
		final Long flags,
		final Random random) {

		if (minPwLength < 1) {
			minPwLength = IPwDefConstants.DEFAULT_PASSWORD_LENGTH;
		}

		this.minPwLength = minPwLength;

		if (maxPwLength < 1 || maxPwLength < minPwLength) {
			maxPwLength = minPwLength;
		}

		this.maxPwLength = maxPwLength;

		this.maxAttempts = maxAttempts;
		this.flags = flags;
		this.random = random;
	}

	@Override
	public List<String> getBlackList() {
		return blackList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.verti.toolkit.jpwgen.impl.IPasswordPolicy#getLength()
	 */
	@Override
	public int getMinPwLength() {
		return minPwLength;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.verti.toolkit.jpwgen.impl.IPasswordPolicy#setLength(int)
	 */
	@Override
	public void setMaxPwLength(final int length) {
		this.minPwLength = length;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.verti.toolkit.jpwgen.impl.IPasswordPolicy#getLength()
	 */
	@Override
	public int getMaxPwLength() {
		return maxPwLength;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.verti.toolkit.jpwgen.impl.IPasswordPolicy#setLength(int)
	 */
	@Override
	public void setMinPwLength(final int length) {
		this.maxPwLength = length;
	}


	@Override
	public List<IPasswordFilter> getFilters() {
		return filters;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.verti.toolkit.jpwgen.impl.IPasswordPolicy#getMaxAttempts()
	 */
	@Override
	public int getMaxAttempts() {
		return maxAttempts;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.verti.toolkit.jpwgen.impl.IPasswordPolicy#setMaxAttempts(int)
	 */
	@Override
	public void setMaxAttempts(final int maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.verti.toolkit.jpwgen.impl.IPasswordPolicy#getFlags()
	 */
	@Override
	public Long getFlags() {
		return flags;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.verti.toolkit.jpwgen.impl.IPasswordPolicy#setFlags(int)
	 */
	@Override
	public void setFlags(final Long flags) {
		this.flags = flags;
	}

	@Override
	public Random getRandom() {
		return random;
	}

	@Override
	public void setRandom(final Random random) {
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.verti.toolkit.jpwgen.impl.IPasswordPolicy#addFilter(de.verti.toolkit.jpwgen.IPasswordFilter
	 * )
	 */
	@Override
	public boolean addFilter(final IPasswordFilter filter) {
		return filters.add(filter);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.verti.toolkit.jpwgen.impl.IPasswordPolicy#removeFilter(de.verti.toolkit.jpwgen.
	 * IPasswordFilter)
	 */
	@Override
	public boolean removeFilter(final IPasswordFilter filter) {
		return filters.remove(filter);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.verti.toolkit.jpwgen.impl.IPasswordPolicy#removeFilter(java.lang.String)
	 */
	@Override
	public boolean removeFilter(final String id) {
		boolean result = false;

		for (IPasswordFilter filter : filters) {

			if (filter.getId().equals(id)) {
				result = filters.remove(filter);
			}
		}

		return result;
	}

	@Override
	public String toString() {
		return "PasswordPolicy [minPwLength=" + minPwLength + ", maxPwLength="
			+ maxPwLength + ", maxAttempts=" + maxAttempts + ", flags="
			+ flags + ", random=" + random + ", filters=" + filters
			+ ", blackList=" + blackList + "]";
	}

}
