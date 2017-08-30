package de.verti.toolkit.jpwgen.flags.impl;

import de.verti.toolkit.jpwgen.IGeneratorOption;
import de.verti.toolkit.jpwgen.flags.AbstractCliFlag;
import de.verti.toolkit.jpwgen.utils.Messages;


public class PwDigitsFlag extends AbstractCliFlag implements IGeneratorOption {

	private static final long serialVersionUID = 1L;

	public static final String CLI_SHORT = "n";

	public static final String CLI_LONG = "digits";

	public static final String CLI_DESCRIPTION = Messages
			.getString("IPwGenCommandLineOptions.CL_DIGITS_DESC");

	public static final String CLI_SHORT_DISABLE = "O";

	public static final String CLI_LONG_DISABLE = "no-digits";

	public static final String CLI_DESCRIPTION_DISABLE = Messages
			.getString("IPwGenCommandLineOptions.CL_NO_DIGITS_DESC");

	public PwDigitsFlag() {
		mask = PW_DIGITS;

		this.cliShort = CLI_SHORT;
		this.cliLong = CLI_LONG;
		this.cliDescription = CLI_DESCRIPTION;

		this.cliShortDisable = CLI_SHORT_DISABLE;
		this.cliLongDisable = CLI_LONG_DISABLE;
		this.cliDescriptionDisable = CLI_DESCRIPTION_DISABLE;
	}

	@Override
	public Long unmask(final Long flags) {
		return new AtLeast2DigitsFlag().unmask(new SingleDigitFlag()
				.unmask(super.unmask(flags)));
	}

	@Override
	public String toString() {
		return "PwDigitsFlag [cliShort=" + cliShort + ", cliLong=" + cliLong
			+ ", cliDescription=" + cliDescription + ", cliShortDisable="
			+ cliShortDisable + ", cliLongDisable=" + cliLongDisable
			+ ", cliDescriptionDisable=" + cliDescriptionDisable
			+ ", mask=" + mask + "]";
	}

}
