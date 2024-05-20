package com.github.calculusmaster.pokeworld.db;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.bson.Document;

import java.time.Duration;

public class CacheManager
{
	public static Cache<String, Document> PLAYER = Caffeine.newBuilder()
			.expireAfterAccess(Duration.ofHours(2))
			.maximumSize(200)
			.build();
}
