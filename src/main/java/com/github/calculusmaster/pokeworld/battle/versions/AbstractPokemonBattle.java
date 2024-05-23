package com.github.calculusmaster.pokeworld.battle.versions;

import com.github.calculusmaster.pokeworld.battle.players.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPokemonBattle
{
	private final List<AbstractPlayer> players;

	public AbstractPokemonBattle()
	{
		this.players = new ArrayList<>();
	}
}
