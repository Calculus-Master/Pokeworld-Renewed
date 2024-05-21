package com.github.calculusmaster.pokeworld.listeners;

import com.github.calculusmaster.pokeworld.Pokeworld;
import com.github.calculusmaster.pokeworld.commands.CommandData;
import com.github.calculusmaster.pokeworld.commands.CommandManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandListener extends ListenerAdapter
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CommandListener.class);

	@Override
	public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event)
	{
		String name = event.getName();

		if(CommandManager.COMMANDS.containsKey(name))
		{
			long start = System.nanoTime();
			LOGGER.info("Parsing slash command: {}", name);

			CommandData data = CommandManager.COMMANDS.get(name);
			boolean result = false;

			if(data.devOnly() && !event.getUser().getId().equals(Pokeworld.ENV.get("DEVELOPER_ID")))
				event.reply("[Error] This command is only available for the developer.").queue();
			else result = CommandManager.COMMANDS.get(name).constructor().apply(event).execute(event);

			long end = System.nanoTime();
			LOGGER.info("Executed slash command: {} (Result: {}, Time: {} ms)", name, result, (end - start) / 1000000.);
		}
		else LOGGER.warn("Received unknown slash command: {}", name);
	}
}
