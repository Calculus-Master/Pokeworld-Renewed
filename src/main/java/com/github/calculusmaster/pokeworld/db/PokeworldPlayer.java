package com.github.calculusmaster.pokeworld.db;

import com.mongodb.client.model.Filters;
import org.bson.Document;

public class PokeworldPlayer
{
	//Object creation

	public static PokeworldPlayer add(String userID, String username)
	{
		PokeworldPlayer player = new PokeworldPlayer(userID, username);

		Document data = player.serialize();
		DatabaseManager.PLAYER.insert(data);
		CacheManager.PLAYER.put(userID, data);

		return player;
	}

	public static PokeworldPlayer get(String userID)
	{
		Document data = CacheManager.PLAYER.getIfPresent(userID);

		if(data == null)
		{
			data = DatabaseManager.PLAYER.get(Filters.eq("user_id", userID));
			if(data == null) return null;

			CacheManager.PLAYER.put(userID, data);
		}

		return new PokeworldPlayer(data);
	}

	private PokeworldPlayer(String userID, String name)
	{
		this.userID = userID;
		this.name = name;
	}

	private PokeworldPlayer(Document data)
	{
		this(data.getString("user_id"), data.getString("name"));
	}

	//Data
	private final String userID;
	private final String name;

	public Document serialize()
	{
		return new Document("user_id", this.userID)
				.append("name", this.name);
	}
}
