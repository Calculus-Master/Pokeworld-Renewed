package com.github.calculusmaster.pokeworld.battle.players;

import com.github.calculusmaster.pokeworld.db.PokeworldPlayer;

public class UserPlayer extends AbstractPlayer
{
	private final PokeworldPlayer player;

	public UserPlayer(PokeworldPlayer player)
	{
		this.player = player;
	}

	@Override
	public String getID()
	{
		return this.player.getUserID();
	}

	@Override
	public String getName()
	{
		return this.player.getName();
	}
}
