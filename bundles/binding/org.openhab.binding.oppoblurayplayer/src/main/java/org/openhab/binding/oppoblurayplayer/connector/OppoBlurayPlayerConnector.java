/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.oppoblurayplayer.connector;

import org.openhab.binding.oppoblurayplayer.internal.OppoBlurayPlayerException;
import org.openhab.binding.oppoblurayplayer.internal.core.OppoBlurayPlayerCommandReceiver;


/**
 * Base class for Oppo Bluray Player communication.
 * 
 * @author netwolfuk
 * @since 1.5.1
 */
public interface OppoBlurayPlayerConnector {

	/**
	 * Procedure for connecting to projector.
	 * 
	 * @throws EpsonProjectorException
	 */
	void connect() throws OppoBlurayPlayerException;

	/**
	 * Procedure for disconnecting to projector controller.
	 * 
	 * @throws EpsonProjectorException
	 */
	void disconnect() throws OppoBlurayPlayerException;

	/**
	 * Procedure for send raw data to player.
	 * 
	 * @param data
	 *            Message to send.
	 * 
	 * @param timeout
	 *            timeout to wait response in milliseconds.
	 * 
	 * @throws OppoBlurayPlayerException
	 */
	void sendMessage(String data, int timeout) throws OppoBlurayPlayerException;
	
	/**
	 * Procedure for receive raw data to player.
	 * 
	 * @return data
	 * 			  Message received from player
	 * 
	 * @throws OppoBlurayPlayerException
	 * 
	 */
	void messageReceived(String message) throws OppoBlurayPlayerException;
	
	/**
	 * Procedure for registering an OppoBlurayCommandReceiver.
	 * 
	 * An OppoBlurayCommandReceiver is expected to listen for events
	 * sent from the Bluray Player, decipher them and place any updates
	 * on the OpenHAB event bus.
	 * 
	 * @param OppoBlurayPlayerCommandReceiver instance.
	 * 
	 * @throws OppoBlurayPlayerException if the command cannot be deciphered.
	 */
	void registerCommandReceiver(OppoBlurayPlayerCommandReceiver receiver);
	
	
	void unregisterCommandReceiver(OppoBlurayPlayerCommandReceiver receiver);

}
