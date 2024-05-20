package com.github.calculusmaster.pokeworld;

import com.github.calculusmaster.pokeworld.commands.CommandManager;
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
		ENV = Dotenv.load();

		JDABuilder builder = JDABuilder
				.createDefault(ENV.get("BOT_TOKEN"))
				.setActivity(Activity.playing(NAME))
				.addEventListeners();

		BOT = builder.build().awaitReady();

		Pokeworld.initialize(CommandManager::init, "CommandManager");
	}

	private static void initialize(Runnable initializer, String system)
	{
		LOGGER.info("Starting initialization: {}", system);
		initializer.run();
		LOGGER.info("Finished initialization: {}", system);
	}
}
