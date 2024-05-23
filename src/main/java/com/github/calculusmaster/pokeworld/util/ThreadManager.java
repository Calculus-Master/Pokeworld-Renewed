package com.github.calculusmaster.pokeworld.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadManager
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadManager.class);

	public static final ExecutorService DB_UPDATER = Executors.newVirtualThreadPerTaskExecutor();
	public static final ScheduledExecutorService BATTLE_REQUEST_EXPIRY = Executors.newSingleThreadScheduledExecutor();

	public static void shutdown() throws InterruptedException
	{
		DB_UPDATER.shutdown();
		if(!DB_UPDATER.awaitTermination(10, TimeUnit.SECONDS)) LOGGER.error("DB Updater ExecutorService did not terminate in time.");

		BATTLE_REQUEST_EXPIRY.shutdownNow();
	}
}
