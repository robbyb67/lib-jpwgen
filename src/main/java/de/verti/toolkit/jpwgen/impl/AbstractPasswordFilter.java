package de.verti.toolkit.jpwgen.impl;

import de.verti.toolkit.jpwgen.IPasswordFilter;
import de.verti.toolkit.jpwgen.IPwDefConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public abstract class AbstractPasswordFilter implements IPasswordFilter, IPwDefConstants {

	// A list that stores the forbidden words
	protected List<String> blacklist = new ArrayList<String>();

	protected String id = AbstractPasswordFilter.class.getSimpleName();
	protected String description = AbstractPasswordFilter.class.getSimpleName();

	AbstractPasswordFilter() {
		id = this.getClass().getSimpleName();
		description = this.getClass().getSimpleName();
	}

	AbstractPasswordFilter(final List<String> blacklist) {
		this.blacklist = blacklist;
		id = this.getClass().getSimpleName();
		description = this.getClass().getSimpleName();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public List<String> filter(final int passwordFlags, final List<String> passwords) {
		List<String> suitable = new ArrayList<String>();

		for (Iterator<String> iter = passwords.iterator(); iter.hasNext();) {
			String element = iter.next();

			if (filter(passwordFlags, passwords) != null) {
				suitable.add(element);
			}
		}

		return suitable;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.rrze.idmone.utils.pwgen.IPassowrdFilter#getBlacklist()
	 */
	@Override
	public synchronized List<String> getBlacklist() {
		return blacklist;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.rrze.idmone.utils.pwgen.IPassowrdFilter#setBlacklist(java.util.List)
	 */
	@Override
	public synchronized void setBlacklist(final List<String> blacklist) {
		this.blacklist = blacklist;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.rrze.idmone.utils.pwgen.IPassowrdFilter#addToBlacklist(java.lang.String
	 * )
	 */
	@Override
	public synchronized boolean addToBlacklist(final String blackWord) {
		return blacklist.add(blackWord);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.rrze.idmone.utils.pwgen.IPassowrdFilter#removeFromBlacklist(java.lang
	 * .String)
	 */
	@Override
	public synchronized boolean removeFromBlacklist(final String blackWord) {
		return blacklist.remove(blackWord);
	}

}
