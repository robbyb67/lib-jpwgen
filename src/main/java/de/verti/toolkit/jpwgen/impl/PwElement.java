/*
/*
 * RRZEPwGen, developed as a part of the IDMOne project at RRZE.
 * Copyright 2007, RRZE, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors. This
 * product includes software developed by the Apache Software Foundation
 * http://www.apache.org/
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * This software uses code and ideas from:
 * 	http://sourceforge.net/projects/pwgen/
 * 	Copyright (C) 2001,2002 by Theodore Ts'o
 * 
 */
package de.verti.toolkit.jpwgen.impl;

/**
 * A helper construct representing an element used for the generation of
 * passwords. An instance of this class is either a vowel or a consonant. Both
 * vowels and consonants can be marked as dipthongs. The marking is implemented
 * as a bit masked int. By using this class a pronounceable password is created
 * which should be easy to remember.
 * 
 * @author unrz205
 */
public class PwElement
{
	// the string representation of the vowel or consonant - (probably also a
	// dipthong)
	protected String value;

	// the flags describing the password element - actually its type
	// it is build up by a bit mask
	protected int type;

	/**
	 * Constructor of the Password Element class
	 * 
	 * @param value
	 *            the string representation of the vowel, consonant (dipthong)
	 * @param type
	 *            the type of the password element
	 */
	public PwElement(String value, int type)
	{
		this.value = value;
		this.type = type;
	}

	/**
	 * Returns the type of this password element
	 * 
	 * @return a bit-masked type describing the type of element
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * Sets the type of this password element
	 * 
	 * @param type The type
	 */
	public void setType(int type)
	{
		this.type = type;
	}

	/**
	 * Returns the string representation of this password element
	 * 
	 * @return the string representation of this password element
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Sets the string representation of this password element
	 * 
	 * @param value The string value
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
}
