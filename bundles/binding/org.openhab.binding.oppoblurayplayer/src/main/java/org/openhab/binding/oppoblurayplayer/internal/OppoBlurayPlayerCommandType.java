/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.oppoblurayplayer.internal;

import java.io.InvalidClassException;

import org.openhab.core.items.Item;
import org.openhab.core.library.items.ContactItem;
import org.openhab.core.library.items.NumberItem;
import org.openhab.core.library.items.StringItem;
import org.openhab.core.library.items.SwitchItem;

/**
 * Represents all valid command types which could be processed by this
 * binding.
 * 
 * @author netwolfuk
 * @since 1.5.1
 */
public enum OppoBlurayPlayerCommandType {

	POWER ("Power", SwitchItem.class),
	POWER_STATE ("PowerState", StringItem.class),
	PLAYBACK_STATUS ("PlayBackStatus", StringItem.class),
	TRAY_POSITION ("TrayPosition", ContactItem.class),
	LAMP_TIME ("LampTime", NumberItem.class),
	KEY_CODE ("KeyCode", NumberItem.class),
	VKEYSTONE ("VerticalKeystone", NumberItem.class),
	HKEYSTONE ("HorizontalKeystone", NumberItem.class),
	AKEYSTONE ("AutoKeystone", NumberItem.class),
	ASPECT_RATIO ("AspectRatio", StringItem.class),
	LUMINANCE ("Luminance", StringItem.class),
	SOURCE ("Source", StringItem.class),
	DIRECT_SOURCE ("DirectSource", NumberItem.class),
	BRIGHTNESS ("Brightness", NumberItem.class),
	CONTRAST ("Contrast", NumberItem.class),
	DENSITY ("Density", NumberItem.class),
	TINT ("Tint", NumberItem.class),
	SHARP ("Sharpness", NumberItem.class),
	COLOR_TEMP ("ColorTemperature", NumberItem.class),
	FLESH_TEMP ("FleshTemperature", NumberItem.class),
	COLOR_MODE ("ColorMode", StringItem.class),
	HPOSITION ("HorizontalPosition", NumberItem.class),
	VPOSITION ("VerticalPosition", NumberItem.class),
	TRACKING ("Tracking", NumberItem.class),
	SYNC ("Sync", NumberItem.class),
	OFFSET_RED ("OffsetRed", NumberItem.class),
	OFFSET_GREEN ("OffsetGreen", NumberItem.class),
	OFFSET_BLUE ("OffsetBlue", NumberItem.class),
	GAIN_RED ("GainRed", NumberItem.class),
	GAIN_GREEN ("GainGreen", NumberItem.class),
	GAIN_BLUE ("GainBlue", NumberItem.class),
	GAMMA ("Gamma", StringItem.class),
	GAMMA_STEP ("GammaStep", NumberItem.class),
	COLOR ("Color", StringItem.class),
	MUTE ("Mute", SwitchItem.class),
	HREVERSE ("HorizontalReverse", SwitchItem.class),
	VREVERSE ("VerticalReverse", SwitchItem.class),
	BACKGROUND ("Background", StringItem.class),
	ERR_CODE ("ErrCode", NumberItem.class),
	ERR_MESSAGE ("ErrMessage", StringItem.class), 
	VERBOSITY ("VerboseMode", StringItem.class),
	;

	private final String text;
	private Class<? extends Item> itemClass;

	private OppoBlurayPlayerCommandType(final String text, Class<? extends Item> itemClass) {
		this.text = text;
		this.itemClass = itemClass;
	}

	@Override
	public String toString() {
		return text;
	}

	public Class<? extends Item> getItemClass() {
		return itemClass;
	}

	/**
	 * Procedure to validate command type string.
	 * 
	 * @param commandTypeText
	 *            command string e.g. RawData, Command, Brightness
	 * @return true if item is valid.
	 * @throws IllegalArgumentException
	 *             Not valid command type.
	 * @throws InvalidClassException
	 *             Not valid class for command type.
	 */
	public static boolean validateBinding(String commandTypeText,
			Class<? extends Item> itemClass) throws IllegalArgumentException,
			InvalidClassException {

		for (OppoBlurayPlayerCommandType c : OppoBlurayPlayerCommandType.values()) {
			if (c.text.equals(commandTypeText)) {

				if (c.getItemClass().equals(itemClass)) {
					return true;
				} else {
					throw new InvalidClassException(
							"Not valid class for command type");
				}
			}
		}

		throw new IllegalArgumentException("Not valid command type");

	}

	/**
	 * Procedure to convert command type string to command type class.
	 * 
	 * @param commandTypeText
	 *            command string e.g. RawData, Command, Brightness
	 * @return corresponding command type.
	 * @throws InvalidClassException
	 *             Not valid class for command type.
	 */
	public static OppoBlurayPlayerCommandType getCommandType(String commandTypeText)
			throws IllegalArgumentException {

		for (OppoBlurayPlayerCommandType c : OppoBlurayPlayerCommandType.values()) {
			if (c.text.equals(commandTypeText)) {
				return c;
			}
		}

		throw new IllegalArgumentException("Not valid command type");
	}

	public static void findStatusEvent(String command) {
		// TODO Auto-generated method stub
		
	}

}
