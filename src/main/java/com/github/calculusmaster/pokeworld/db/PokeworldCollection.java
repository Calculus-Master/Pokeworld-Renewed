package com.github.calculusmaster.pokeworld.db;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PokeworldCollection
{
	private static final Logger LOGGER = LoggerFactory.getLogger(PokeworldCollection.class);

	private final String name;
	private final MongoCollection<Document> collection;
	
	public PokeworldCollection(MongoDatabase database, String collection)
	{
		this.name = collection;
		this.collection = database.getCollection(collection);
	}
	
	public void insert(Document data)
	{
		this.collection.insertOne(data);
		LOGGER.info("DB Insert -> {} | Data: {}", this.name, data);
	}
	
	public void update(Bson query, Bson update)
	{
		this.collection.updateOne(query, update);
		LOGGER.info("DB Update -> {} | Data: {}", this.name, update);
	}
	
	public void delete(Bson query)
	{
		this.collection.deleteOne(query);
		LOGGER.info("DB Delete -> {} | Query: {}", this.name, query);
	}

	@Nullable
	public Document get(Bson query)
	{
		Document data = this.collection.find(query).first();
		if(data == null) return null;

		LOGGER.info("DB Get -> {} | Query: {} | Data: {}", this.name, query, data);
		return data;
	}
}
