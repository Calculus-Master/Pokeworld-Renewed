package com.github.calculusmaster.pokeworld.db;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PokeworldPlayer
{
	private static final ExecutorService UPDATER = Executors.newVirtualThreadPerTaskExecutor();

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

	public static boolean exists(String userID)
	{
		if(CacheManager.PLAYER.getIfPresent(userID) != null) return true;
		else return DatabaseManager.PLAYER.contains(Filters.eq("user_id", userID));
	}

	private PokeworldPlayer(String userID, String name)
	{
		this.userID = userID;
		this.name = name;

		this.query = Filters.eq("user_id", this.userID);
	}

	private PokeworldPlayer(Document data)
	{
		this(data.getString("user_id"), data.getString("name"));
	}

	//Mongo
	private final Bson query;

	public Document serialize()
	{
		return new Document("user_id", this.userID)
				.append("name", this.name);
	}

	private void update(Bson update)
	{
		CacheManager.PLAYER.invalidate(this.userID);
		UPDATER.submit(() -> DatabaseManager.PLAYER.update(this.query, update));
	}

	//Data
	private final String userID;
	private final String name;

	public String getUserID()
	{
		return this.userID;
	}

	public String getName()
	{
		return this.name;
	}
}
