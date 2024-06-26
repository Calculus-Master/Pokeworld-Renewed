package com.github.calculusmaster.pokeworld;

import com.github.calculusmaster.pokeworld.commands.CommandManager;
import com.github.calculusmaster.pokeworld.db.DatabaseManager;
import com.github.calculusmaster.pokeworld.listeners.CommandListener;
import com.github.calculusmaster.pokeworld.util.ThreadManager;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pokeworld
{
	private static final Logger LOGGER = LoggerFactory.getLogger(Pokeworld.class);

	public static final String NAME = "PokeWorld";
	public static Dotenv ENV;
	public static JDA BOT;

	public static void main(String[] args) throws InterruptedException
	{
		long start = System.nanoTime();

		ENV = Dotenv.load();

		JDABuilder builder = JDABuilder
				.createDefault(ENV.get("BOT_TOKEN"))
				.setActivity(Activity.playing(NAME))
				.addEventListeners(new CommandListener());

		BOT = builder.build().awaitReady();

		Pokeworld.initialize(DatabaseManager::init, "DatabaseManager");
		Pokeworld.initialize(CommandManager::init, "CommandManager");

		long end = System.nanoTime();

		LOGGER.info("Bot initialization complete! (Time: {} ms)", (end - start) / 1000000.);
	}

	private static void initialize(Runnable initializer, String system)
	{
		LOGGER.info("Starting initialization: {}", system);
		long start = System.nanoTime();
		initializer.run();
		long end = System.nanoTime();
		LOGGER.info("Finished initialization: {} (Time: {} ms)", system, (end - start) / 1000000.);
	}

	public static void shutdown()
	{
		BOT.shutdown();

		try
		{
			ThreadManager.shutdown();

			BOT.awaitShutdown();

			LOGGER.info("Bot has successfully shutdown.");
			System.exit(0);
		} catch (InterruptedException e)
		{
			LOGGER.error("Failed to shutdown the bot.", e);
			System.exit(1);
		}
	}
}
