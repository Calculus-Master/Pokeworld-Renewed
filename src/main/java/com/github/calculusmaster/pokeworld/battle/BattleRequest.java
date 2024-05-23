package com.github.calculusmaster.pokeworld.battle;

import java.util.HashSet;
import java.util.Set;

public class BattleRequest
{
	private final String initiator;
	private final Set<String> requested = new HashSet<>();
	private final Set<String> accepted = new HashSet<>();

	BattleRequest(String initiator)
	{
		this.initiator = initiator;
		this.requested.add(initiator);
	}

	public void addRequested(String playerID)
	{
		this.requested.add(playerID);
	}

	public void setAccepted(String playerID)
	{
		this.accepted.add(playerID);
	}

	public boolean isReady()
	{
		return this.accepted.size() == this.requested.size();
	}

	public boolean isInitiator(String playerID)
	{
		return this.initiator.equals(playerID);
	}

	public Set<String> getPlayers()
	{
		return this.requested;
	}
}
