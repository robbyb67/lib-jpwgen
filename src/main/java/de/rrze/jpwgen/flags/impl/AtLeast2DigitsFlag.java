package de.rrze.jpwgen.flags.impl;

import de.rrze.jpwgen.flags.AbstractCliFlag;
import de.rrze.jpwgen.utils.Messages;

public class AtLeast2DigitsFlag extends AbstractCliFlag {

	private static final long serialVersionUID = 1L;

	public static final String CLI_SHORT = "u";

	public static final String CLI_LONG = "two-digits";

	public static final String CLI_DESCRIPTION = Messages
			.getString("IPwGenCommandLineOptions.CL_REGEX_AT_LEAST_2_DIGITS_DESC");

	public AtLeast2DigitsFlag() {
		mask = REGEX_AT_LEAST_2_DIGITS_FLAG;
		
		this.cliShort = CLI_SHORT;
		this.cliLong = CLI_LONG;
		this.cliDescription = CLI_DESCRIPTION;
	}

	public Long mask(Long flags) {
		Long tmp = new SingleDigitFlag().unmask(flags);

		return super.mask(new PwDigitsFlag().mask(tmp));
	}

	@Override
	public String toString() {
		return "AtLeast2DigitsFlag [cliShort=" + CLI_SHORT + ", cliLong="
				+ cliLong + ", cliDescription=" + cliDescription
				+ ", cliShortDisable=" + cliShortDisable + ", cliLongDisable="
				+ cliLongDisable + ", cliDescriptionDisable="
				+ cliDescriptionDisable + ", mask=" + mask + "]";
	}

}
