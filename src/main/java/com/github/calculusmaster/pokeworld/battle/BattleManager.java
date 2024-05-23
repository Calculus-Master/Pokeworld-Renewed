package com.github.calculusmaster.pokeworld.battle;

import com.github.calculusmaster.pokeworld.battle.versions.AbstractPokemonBattle;
import com.github.calculusmaster.pokeworld.battle.versions.PlayerPokemonBattle;
import com.github.calculusmaster.pokeworld.db.PokeworldPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BattleManager
{
	private static final Map<String, BattleRequest> ACTIVE_REQUESTS = new HashMap<>();
	private static final Map<String, AbstractPokemonBattle> ACTIVE_BATTLES = new HashMap<>();

	// Requests
	public static void createRequest(String initiator, List<String> requested)
	{
		BattleRequest request = new BattleRequest(initiator);
		requested.forEach(request::addRequested);

		ACTIVE_REQUESTS.put(initiator, request);
		requested.forEach(id -> ACTIVE_REQUESTS.put(id, request));
	}

	public static boolean hasRequest(String playerID)
	{
		return ACTIVE_REQUESTS.containsKey(playerID);
	}

	public static BattleRequest getRequest(String playerID)
	{
		assert BattleManager.hasRequest(playerID);
		return ACTIVE_REQUESTS.get(playerID);
	}

	public static void removeRequest(String playerID)
	{
		assert BattleManager.hasRequest(playerID);
		ACTIVE_REQUESTS.remove(playerID);
	}

	// Battles
	public static AbstractPokemonBattle create(String player1ID)
	{
		assert BattleManager.getRequest(player1ID).isReady();
		BattleRequest request = ACTIVE_REQUESTS.get(player1ID);

		List<PokeworldPlayer> players = new ArrayList<>();
		request.getPlayers().forEach(id -> players.add(PokeworldPlayer.get(id)));
		AbstractPokemonBattle battle = new PlayerPokemonBattle(players);

		ACTIVE_BATTLES.put(player1ID, battle);
		return battle;
	}

	public static AbstractPokemonBattle get(String playerID)
	{
		assert BattleManager.isInBattle(playerID);
		return ACTIVE_BATTLES.get(playerID);
	}

	public static boolean isInBattle(String playerID)
	{
		return ACTIVE_BATTLES.containsKey(playerID);
	}
}
