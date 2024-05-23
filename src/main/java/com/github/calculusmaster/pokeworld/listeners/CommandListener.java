package com.github.calculusmaster.pokeworld.listeners;

import com.github.calculusmaster.pokeworld.Pokeworld;
import com.github.calculusmaster.pokeworld.commands.CommandData;
import com.github.calculusmaster.pokeworld.commands.CommandManager;
import com.github.calculusmaster.pokeworld.db.PokeworldPlayer;
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

			String id = event.getUser().getId();

			if(!data.isStart() && !PokeworldPlayer.exists(id))
				event.reply("You'll need to start your adventure first to use this command. Use `/start` to begin!").queue();
			else if(data.devOnly() && !id.equals(Pokeworld.ENV.get("DEVELOPER_ID")))
				event.reply("[Error] This command is only available for the developer.").queue();
			else result = CommandManager.COMMANDS.get(name).constructor().apply(event).execute(event);

			long end = System.nanoTime();
			LOGGER.info("Executed slash command: {} ({} | Time: {} ms)", name, result ? "Succeeded" : "Failed", (end - start) / 1000000.);
		}
		else LOGGER.warn("Received unknown slash command: {}", name);
	}
}
