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
package de.rrze.jpwgen;

import de.rrze.jpwgen.utils.Messages;


/**
 * This interface defines the command line options of the password generator. All options,
 *
 * @author  unrz205
 */
@SuppressWarnings(
	{ "squid:S2068", "squid:S1214" }
) // SONAR: false positive. no hardcoded passwords here, constants will not be enums
public interface IPwGenCommandLineOptions {

	/**
	 * Sets a terminal length, used for printing more than one password on a terminal.
	 */
	int DEFAULT_TERM_WIDTH = 80;

	/**
	 * Default number of columns to be used when printing out passwords on a terminal.
	 */
	int DEFAULT_NUMBER_OF_COLUMNS = -1;

	/**
	 * Should columns be used or not(for print out).
	 */
	boolean DEFAULT_DO_COLUMNS = false;

	/**
	 * The number of passwords to be generated.
	 */
	String CL_NUMBER_PASSWORD = "N";

	/**
	 * The number of passwords to be generated.
	 */
	String CL_NUMBER_PASSWORD_LONG = "number";

	/**
	 * The description of the -N and the --number command line options.
	 */
	String CL_NUMBER_PASSWORD_DESC = Messages
			.getString("IPwGenCommandLineOptions.CL_NUMBER_PASSWORD_DESC");

	/**
	 * The length of the generated password.
	 */
	String CL_PASSWORD_LENGTH = "s";

	/**
	 * The length of the generated password.
	 */
	String CL_PASSWORD_LENGTH_LONG = "size";

	/**
	 * The description of the -s and the --size command line options.
	 */
	String CL_PASSWORD_LENGTH_DESC = Messages
			.getString("IPwGenCommandLineOptions.CL_PASSWORD_LENGTH_DESC");

	/**
	 * Print a help message.
	 */
	String CL_HELP = "h";

	/**
	 * Print a help message.
	 */
	String CL_HELP_LONG = "help";

	/**
	 * The description of the -h and the --help command line options.
	 */
	String CL_HELP_DESC = Messages
			.getString("IPwGenCommandLineOptions.CL_HELP_DESC");

	/**
	 * Use simple random for password generation.
	 */
	String CL_RANDOM = "r";

	/**
	 * Use simple random for password generation.
	 */
	String CL_RANDOM_LONG = "random";

	/**
	 * The description of the -r and the --random command line options.
	 */
	String CL_RANDOM_DESC = Messages
			.getString("IPwGenCommandLineOptions.CL_RANDOM_DESC");

	/**
	 * Print the generated passwords in columns.
	 */
	String CL_COLUMN = "C";

	/**
	 * Print the generated passwords in columns.
	 */
	String CL_COLUMN_LONG = "columns";

	/**
	 * The description of the -C and the --columns command line options.
	 */
	String CL_COLUMN_DESC = Messages
			.getString("IPwGenCommandLineOptions.CL_COLUMN_DESC");

	/**
	 * Sets the character width of the PwGen terminal.
	 */
	String CL_TERM_WIDTH = "t";

	/**
	 * Sets the character width of the PwGen terminal.
	 */
	String CL_TERM_WIDTH_LONG = "term-width";

	/**
	 * The description of the -t and the --term-width command line options.
	 */
	String CL_TERM_WIDTH_DESC = Messages
			.getString("IPwGenCommandLineOptions.CL_TERM_WIDTH_DESC");

	/**
	 * Lists the available security service providers for SecureRandom and exits.
	 */
	String CL_SR_PROVIDERS = "l";

	/**
	 * Lists the available security service providers for SecureRandom and exits.
	 */
	String CL_SR_PROVIDERS_LONG = "list-sr-providers";

	/**
	 * The description of the -l and the --list-sr-providers command line options.
	 */
	String CL_SR_PROVIDERS_DESC = Messages
			.getString("IPwGenCommandLineOptions.CL_SR_PROVIDERS_DESC");

	/**
	 * Lists all available security providers and algorithms.
	 */
	String CL_PROVIDERS = "L";

	/**
	 * Lists all available security providers and algorithms.
	 */
	String CL_PROVIDERS_LONG = "list-providers";

	/**
	 * The description of the -L and the --list-providers command line options.
	 */
	String CL_PROVIDERS_DESC = Messages
			.getString("IPwGenCommandLineOptions.CL_PROVIDERS_DESC");

	/**
	 * Sets the random algorithm used by SecureRandom.
	 */
	String CL_SR_ALGORITHM = "S";

	/**
	 * Sets the random algorithm used by SecureRandom.
	 */
	String CL_SR_ALGORITHM_LONG = "set-algorithm";

	/**
	 * The description of the -S and the --set-algorithm command line options.
	 */
	String CL_SR_ALGORITHM_DESC = Messages
			.getString("IPwGenCommandLineOptions.CL_SR_ALGORITHM_DESC");

	/**
	 * Sets the maximum number of attempts for generating a password with the provided policies.
	 */
	String CL_MAX_ATTEMPTS = "M";

	/**
	 * Sets the maximum number of attempts for generating a password with the provided policies.
	 */
	String CL_MAX_ATTEMPTS_LONG = "max-attempts";

	/**
	 * The description of the -M and the --max-attempts command line options.
	 */
	String CL_MAX_ATTEMPTS_DESC = Messages
			.getString("IPwGenCommandLineOptions.CL_MAX_ATTEMPTS_DESC");

	String ALPHA_LOW = "abcdefghijklmnopqrstuvwxyz";

	String ALPHA_UP = "ABCDEFGHIKLMNOPQRSTVXYZ";
}
