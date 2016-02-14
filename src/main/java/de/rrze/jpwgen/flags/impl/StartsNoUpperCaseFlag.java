package de.rrze.jpwgen.flags.impl;

import de.rrze.jpwgen.flags.AbstractCliFlag;
import de.rrze.jpwgen.utils.Messages;

public class StartsNoUpperCaseFlag extends AbstractCliFlag {

	private static final long serialVersionUID = 1L;

	public static final String CLI_SHORT = "e";

	public static final String CLI_LONG = "start-no-uppercase-letter";

	public static final String CLI_DESCRIPTION = Messages
			.getString("IPwGenCommandLineOptions.CL_REGEX_STARTS_NO_UPPERCASE_DESC");

	public StartsNoUpperCaseFlag() {
		mask = REGEX_STARTS_NO_UPPERCASE_FLAG;

		this.cliShort = CLI_SHORT;
		this.cliLong = CLI_LONG;
		this.cliDescription = CLI_DESCRIPTION;
	}

	@Override
	public String toString() {
		return "StartsNoUpperCaseFlag [cliShort=" + cliShort + ", cliLong="
				+ cliLong + ", cliDescription=" + cliDescription
				+ ", cliShortDisable=" + cliShortDisable + ", cliLongDisable="
				+ cliLongDisable + ", cliDescriptionDisable="
				+ cliDescriptionDisable + ", mask=" + mask + "]";
	}

}
