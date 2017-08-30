package de.verti.toolkit.jpwgen.flags.impl;

import de.verti.toolkit.jpwgen.flags.AbstractCliFlag;
import de.verti.toolkit.jpwgen.utils.Messages;


public class SingleCapitalFlag extends AbstractCliFlag {

	private static final long serialVersionUID = 1L;

	public static final String CLI_SHORT = "m";

	public static final String CLI_LONG = "one-upercase";

	public static final String CLI_DESCRIPTION = Messages
			.getString("IPwGenCommandLineOptions.CL_REGEX_SINGLE_CAPITAL_DESC");

	public SingleCapitalFlag() {
		mask = REGEX_SINGLE_CAPITAL_FLAG;

		this.cliShort = CLI_SHORT;
		this.cliLong = CLI_LONG;
		this.cliDescription = CLI_DESCRIPTION;
	}

	@Override
	public Long mask(final Long flags) {
		return new PwCapitalizeFlag().mask(super.mask(flags));
	}

	@Override
	public String toString() {
		return "SingleCapitalFlag [cliShort=" + cliShort + ", cliLong="
			+ cliLong + ", cliDescription=" + cliDescription
			+ ", cliShortDisable=" + cliShortDisable + ", cliLongDisable="
			+ cliLongDisable + ", cliDescriptionDisable="
			+ cliDescriptionDisable + ", mask=" + mask + "]";
	}

}
