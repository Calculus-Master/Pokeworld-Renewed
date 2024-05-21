package com.github.calculusmaster.pokeworld.commands;

import com.github.calculusmaster.pokeworld.Pokeworld;
import com.github.calculusmaster.pokeworld.commands.logic.StartCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public final class CommandManager
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CommandManager.class);
	public static final Map<String, CommandData> COMMANDS = new HashMap<>();

	public static void init()
	{
		LOGGER.info("Initializing {} commands...", CommandEntry.values().length);

		Guild testServer = Objects.requireNonNull(Pokeworld.BOT.getGuildById(Pokeworld.ENV.get("TEST_SERVER_ID")));
		testServer.updateCommands().addCommands().queue();
		testServer.updateCommands().addCommands(COMMANDS.values().stream().map(CommandData::slashCommandData).toList()).queue();

		LOGGER.info("Successfully registered {} commands.", COMMANDS.size());
	}

	public enum CommandEntry
	{
		START(StartCommand::new, Commands
				.slash("start", "Start your Pokemon adventure!")
				.addOption(OptionType.STRING, "starter", "The starter Pokemon you choose. Use this command without this option to see your choices.", false)
		),

		;

		CommandEntry(Function<GenericInteractionCreateEvent, ? extends PokeworldCommand> constructor, SlashCommandData data)
		{
			this(constructor, data, false);
		}

		CommandEntry(Function<GenericInteractionCreateEvent, ? extends PokeworldCommand> constructor, SlashCommandData data, boolean devOnly)
		{
			COMMANDS.put(data.getName(), new CommandData(constructor, data, devOnly));
		}
	}
}
