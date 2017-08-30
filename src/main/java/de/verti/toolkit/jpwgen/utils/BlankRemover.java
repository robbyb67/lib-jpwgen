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

/**
 * This class provides functionality for handling white-spaces at different places. It can clean up leading or trailing
 * white-spaces. It can also replace several white-spaces with a single one.<br>
 * <br>
 * Example:
 *
 * <ul>
 *   <li>String oldStr = " &gt; &lt;1-2-1-2-1-2-1-2-1-2-1-----2-1-2-1-2-1-2-1-2-1-2-1-2&gt; &lt; ";</li>
 *   <li>String newStr = oldStr.replaceAll("-", " ");</li>
 *   <li>System.out.println(newStr);</li>
 *   <li>System.out.println(BlankRemover.ltrim(newStr));</li>
 *   <li>System.out.println(BlankRemover.rtrim(newStr));</li>
 *   <li>System.out.println(BlankRemover.itrim(newStr));</li>
 *   <li>System.out.println(BlankRemover.lrtrim(newStr));</li>
 * </ul>
 *
 * <p>Results in:</p>
 *
 * <ul>
 *   <li>&gt; &lt;1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2&gt; &lt;</li>
 *   <li>&gt; &lt;1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2&gt; &lt;</li>
 *   <li>&gt; &lt;1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2&gt; &lt;</li>
 *   <li>&gt; &lt;1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2&gt; &lt;</li>
 *   <li>&gt; &lt;1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2&gt; &lt;</li>
 * </ul>
 *
 * @author  unrz205
 */
public class BlankRemover {

	private BlankRemover() {
		super();
	}

	/**
	 * Removes leading whitespaces if any. Uses the following <b>regex</b> <em>"^\\s+"</em>
	 *
	 * @param   source  the string to be manipulated
	 *
	 * @return  a resulting string with leading whitespace removed
	 */
	public static String ltrim(final String source) {
		return source.replaceAll("^\\s+", "");
	}

	/**
	 * Removes trailing whitespaces if any. Uses the following <b>regex</b> <em>"\\s+$"</em>
	 *
	 * @param   source  the string to be manipulated
	 *
	 * @return  a resulting string with trailing whitespace removed
	 */
	public static String rtrim(final String source) {
		return source.replaceAll("\\s+$", "");
	}

	/**
	 * Replace multiple whitespaces between words with a single one. Uses the following <b>regex</b> <em>
	 * "\\b\\s{2,}\\b"</em>
	 *
	 * @param   source  the string to be manipulated
	 *
	 * @return  a resulting string with cleaned up whitespaces
	 */
	public static String itrim(final String source) {
		return source.replaceAll("\\b\\s{2,}\\b", " ");
	}

	/**
	 * Removes all superfluous whitespaces in source string . Uses the following sequence of calls to other methods <em>
	 * "itrim(ltrim(rtrim(source)))"</em>
	 *
	 * @param   source  the string to be manipulated
	 *
	 * @return  a resulting string with cleaned up whitespaces
	 */
	public static String trim(final String source) {
		return itrim(ltrim(rtrim(source)));
	}

	/**
	 * Removes leading and trailing whitespaces in source string . Uses the following sequence of calls to other methods
	 * <em>"ltrim(rtrim(source))"</em>
	 *
	 * @param   source  the string to be manipulated
	 *
	 * @return  a resulting string with cleaned up leading and trailing whitespaces
	 */
	public static String lrtrim(final String source) {
		return ltrim(rtrim(source));
	}


}
