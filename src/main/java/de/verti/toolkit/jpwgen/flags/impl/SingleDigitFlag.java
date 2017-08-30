package de.verti.toolkit.jpwgen.flags.impl;

import de.verti.toolkit.jpwgen.flags.AbstractCliFlag;
import de.verti.toolkit.jpwgen.utils.Messages;


public class SingleDigitFlag extends AbstractCliFlag {

	private static final long serialVersionUID = 1L;

	public static final String CLI_SHORT = "q";

	public static final String CLI_LONG = "single-digit";

	public static final String CLI_DESCRIPTION = Messages
			.getString("IPwGenCommandLineOptions.CL_REGEX_SINGLE_DIGIT_DESC");

	public SingleDigitFlag() {
		mask = REGEX_SINGLE_DIGIT_FLAG;

		this.cliShort = CLI_SHORT;
		this.cliLong = CLI_LONG;
		this.cliDescription = CLI_DESCRIPTION;
	}

	@Override
	public Long mask(final Long flags) {
		Long tmp = new AtLeast2DigitsFlag().unmask(flags);

		return super.mask(new PwDigitsFlag().mask(tmp));
	}

}
