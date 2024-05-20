package com.github.calculusmaster.pokeworld.commands;

import com.github.calculusmaster.pokeworld.Pokeworld;
import com.github.calculusmaster.pokeworld.commands.logic.StartCommand;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class CommandManager
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CommandManager.class);
	public static final Map<String, CommandData> COMMANDS = new HashMap<>();

	public static void init()
	{
		LOGGER.info("Initializing {} commands...", CommandEntry.values().length);

		Pokeworld.BOT.updateCommands().addCommands(COMMANDS.values().stream().map(CommandData::slashCommandData).toList()).queue();

		LOGGER.info("Successfully registered {} commands.", COMMANDS.size());
	}

	public enum CommandEntry
	{
		START(StartCommand::new, Commands.slash("start", "Start your Pokemon adventure!")),

		;

		CommandEntry(Supplier<? extends PokeworldCommand> constructor, SlashCommandData data)
		{
			COMMANDS.put(data.getName(), new CommandData(constructor, data));
		}
	}
}
