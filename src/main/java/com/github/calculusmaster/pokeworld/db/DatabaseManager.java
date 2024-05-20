package com.github.calculusmaster.pokeworld.db;

import com.github.calculusmaster.pokeworld.Pokeworld;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseManager
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseManager.class);

	private static MongoClient CLIENT;
	public static PokeworldCollection PLAYER;

	public static void init()
	{
		ConnectionString connectionString = new ConnectionString(Pokeworld.ENV.get("MONGO_CONNECTION_STRING"));
		ServerApi api = ServerApi.builder().version(ServerApiVersion.V1).build();
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).serverApi(api).build();

		try
		{
			CLIENT = MongoClients.create(settings);
			MongoDatabase database = CLIENT.getDatabase("Pokeworld");

			PLAYER = new PokeworldCollection(database, "player");
		} catch(MongoException e)
		{
			LOGGER.error("Failed to connect to MongoDB: {}", e.getMessage());
		}
	}
}
