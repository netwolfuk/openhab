package org.openhab.binding.oppoblurayplayer.internal;

import org.openhab.core.items.Item;
import org.openhab.core.library.items.DimmerItem;
import org.openhab.core.library.items.NumberItem;
import org.openhab.core.library.items.RollershutterItem;
import org.openhab.core.library.items.StringItem;
import org.openhab.core.library.items.SwitchItem;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.openhab.core.types.UnDefType;

public enum OppoBlurayPlayerCommand {
	

	/*
	 QPL 
	 
		OK NO DISC
		OK LOADING
		OK OPEN
		OK CLOSE
		OK PLAY
		OK PAUSE
		OK STOP
		OK STEP
		OK FREV
		OK FFWD
		OK SFWD
		OK SREV
		OK SETUP
		OK HOME MENU
		OK MEDIA CENTER
	 */
	
	/*
	 UPL
	 
		DISC – No disc
		LOAD – Loading disc
		OPEN – Tray is open
		CLOS – Tray is closing
		PLAY – Playback is starting
		PAUS – Playback is paused
		STOP – Playback is stopped
		STPF – Forward frame-by-frame step mode
		STPR – Reverse frame-by-frame step mode
		FFWn – Fast forward mode. Where n is a number of 1... 5 to indicate the speed level
		FRVn – Fast reverse mode. Where n is a number of 1... 5 to indicate the	speed level
		SFWn – Slow forward mode. Where n is a number of 1...4 to indicate the speed level (1 = 1⁄2, 2 = 1⁄4, 3 = 1/8, 4 = 1/16)
		SRVn – Slow reverse mode. Where n is a number of 1...4 to indicate the speed level (1 = 1⁄2, 2 = 1⁄4, 3 = 1/8, 4 = 1/16)
		HOME – in home menu
		MCTR – in media center
	 */
	
	POWER_ON (OppoBlurayPlayerCommandType.POWER, OnOffType.ON,  "QPW", "PON", "OK ON", "UPW 1"),
	POWER_OFF (OppoBlurayPlayerCommandType.POWER,OnOffType.OFF,  "QPW", "POF", "OK OFF", "UPW 0"),
	
	PLAYBACK_STATUS_NO_DISC (OppoBlurayPlayerCommandType.PLAYBACK_STATUS, StringType.EMPTY, "QPL", "???", "OK NO DISC", "UPL DISC"),
	PLAYBACK_STATUS_LOADING (OppoBlurayPlayerCommandType.PLAYBACK_STATUS, StringType.EMPTY, "QPL", "???", "OK LOADING", "UPL LOAD"),
	PLAYBACK_STATUS_OPEN    (OppoBlurayPlayerCommandType.PLAYBACK_STATUS, StringType.EMPTY, "QPL", "EJT", "OK OPEN", "UPL OPEN"),
	PLAYBACK_STATUS_CLOSING (OppoBlurayPlayerCommandType.PLAYBACK_STATUS, StringType.EMPTY, "QPL", "EJT", "OK CLOSE", "UPL CLOS"),
	PLAYBACK_STATUS_PLAYING (OppoBlurayPlayerCommandType.PLAYBACK_STATUS, StringType.EMPTY, "QPL", "PLA", "OK PLAY", "UPL PLAY"),
	PLAYBACK_STATUS_PAUSED  (OppoBlurayPlayerCommandType.PLAYBACK_STATUS, StringType.EMPTY, "QPL", "PAU", "OK PAUSE", "UPL PAUS"),
	PLAYBACK_STATUS_STOPPED (OppoBlurayPlayerCommandType.PLAYBACK_STATUS, StringType.EMPTY, "QPL", "STP", "OK STOP", "UPL STOP"),
	PLAYBACK_STATUS_SETUP   (OppoBlurayPlayerCommandType.PLAYBACK_STATUS, StringType.EMPTY, "QPL", "SET", "OK SETUP", "????"),
	PLAYBACK_STATUS_HOME    (OppoBlurayPlayerCommandType.PLAYBACK_STATUS, StringType.EMPTY, "QPL", "HOM", "OK HOME MENU", "UPL HOME"),
	PLAYBACK_STATUS_MCTR    (OppoBlurayPlayerCommandType.PLAYBACK_STATUS, StringType.EMPTY, "QPL", "???", "OK MEDIA CENTER", "UPL MCTR"), 
	
	VERBOSE_MODE_0			(OppoBlurayPlayerCommandType.VERBOSITY, StringType.EMPTY, "QVM", "SVM 0", "OK 0", "SVM OK 0"),
	VERBOSE_MODE_1			(OppoBlurayPlayerCommandType.VERBOSITY, StringType.EMPTY, "QVM", "SVM 1", "OK 1", "SVM OK 1"),
	VERBOSE_MODE_2			(OppoBlurayPlayerCommandType.VERBOSITY, StringType.EMPTY, "QVM", "SVM 2", "OK 2", "SVM OK 2"),
	VERBOSE_MODE_3			(OppoBlurayPlayerCommandType.VERBOSITY, StringType.EMPTY, "QVM", "SVM 3", "OK 3", "SVM OK 3");
	
	final static String TO_PLAYER_PREFIX="#";		
	final static String FROM_PLAYER_PREFIX="@";		
	
	private OppoBlurayPlayerCommandType type;
	private State state;
	private String query;
	private String command;
	private String response;
	private String queryResponseVerbose;
	private String commandResponseVerbose;
	private String eventVerbose;
	

	private OppoBlurayPlayerCommand(final OppoBlurayPlayerCommandType type, 
									final State state,
									final String query,
									final String command,
									final String response,
									final String event
								   ) {
		this.state = state;
		this.type = type;
		this.query = TO_PLAYER_PREFIX + query;
		this.command = TO_PLAYER_PREFIX + command;
		this.response = FROM_PLAYER_PREFIX + response;
		this.queryResponseVerbose = FROM_PLAYER_PREFIX + query + " " + response;
		this.commandResponseVerbose = FROM_PLAYER_PREFIX + command + " " + response;
		this.eventVerbose = FROM_PLAYER_PREFIX + event;
	}
			
	public static OppoBlurayPlayerCommand findMessageForCommand(OppoBlurayPlayerCommandType commandType, Command message) throws OppoBlurayPlayerException {
		for (OppoBlurayPlayerCommand c : OppoBlurayPlayerCommand.values()) {
			if (c.type.equals(commandType)){
				State s = convertValueToOpenHabState(c.type.getItemClass(), message.toString());
				if (c.state.equals(s)){
					return c;
				}
			}
		}
		throw new OppoBlurayPlayerUnknownCommandException("No command found to match message " + message);
		
	}
	
	public static OppoBlurayPlayerCommand findMatchingCommandFromResponse(String message) throws OppoBlurayPlayerUnknownCommandException {
		for (OppoBlurayPlayerCommand c : OppoBlurayPlayerCommand.values()) {
			if (
					c.response.equals(message) || 
					c.queryResponseVerbose.equals(message) || 
					c.commandResponseVerbose.equals(message) || 
					c.eventVerbose.equals(message)
					) {
				
				return c;
			}
		}
		throw new OppoBlurayPlayerUnknownCommandException("No command found to match message " + message);
		
	}
	
	public OppoBlurayPlayerCommandType getOppoBlurayPlayerCommandType(){
		return type;
	}

	public String getCommandString() {
		return command;
	}
	
	public String getQuery() {
		return query;
	}
	
	public Class<? extends Item> getItemType() {
		return type.getItemClass();
	}
	
	/**
	 * Convert player value to OpenHAB state.
	 * 
	 * @param itemType
	 * @param data
	 * 
	 * @return
	 * @throws OppoBlurayPlayerException 
	 */
	 public static State convertValueToOpenHabState(Class<? extends Item> itemType, String data) throws OppoBlurayPlayerException {
		State state = UnDefType.UNDEF;

		try {
		
			if (itemType == SwitchItem.class) {
				state = data.equalsIgnoreCase("OFF") ? OnOffType.OFF : OnOffType.ON;
				
			} else if (itemType == NumberItem.class) {
				state = new DecimalType(data);
				
			} else if (itemType == DimmerItem.class) {
				state = new PercentType(data);
				
			} else if (itemType == RollershutterItem.class) {
				state = new PercentType(data);
				
			} else if (itemType == StringItem.class) {
				state = new StringType(data);
			}
		} catch (Exception e) {
			throw new OppoBlurayPlayerException("Cannot convert value '" + data + "' to data type " + itemType);
		}
		
		return state;
	 }
	 
	 public State getState() {
		return state;
	 }

}
