package com.github.calculusmaster.pokeworld.commands;

import com.github.calculusmaster.pokeworld.Pokeworld;
import com.github.calculusmaster.pokeworld.commands.logic.BattleCommand;
import com.github.calculusmaster.pokeworld.commands.logic.CreditsCommand;
import com.github.calculusmaster.pokeworld.commands.logic.ShutdownCommand;
import com.github.calculusmaster.pokeworld.commands.logic.StartCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
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
		testServer.updateCommands().addCommands(COMMANDS.values().stream().map(CommandData::slashCommandData).toList()).queue();

		LOGGER.info("Successfully registered {} commands.", COMMANDS.size());
	}

	public enum CommandEntry
	{
		START(StartCommand::new, Commands
				.slash("start", "Start your Pokemon adventure!")
				.addOption(OptionType.STRING, "starter", "The starter Pokemon you choose. Use this command without this option to see your choices.", false)
		),
		SHUTDOWN(ShutdownCommand::new, true, Commands
				.slash("shutdown", "[Developer only] Shuts down the bot.")
		),
		CREDITS(CreditsCommand::new, Commands
				.slash("credits", "Check how many credits you have!")
		),
		BATTLE(BattleCommand::new, Commands
			.slash("battle", "Participate in Pokemon battles!")
				.addSubcommands(
						new SubcommandData("challenge", "Challenge other players to a battle. You can challenge up to 3 other players!")
								.addOption(OptionType.USER, "player2", "A player you want to challenge", true)
								.addOption(OptionType.USER, "player3", "A player you want to challenge", false)
								.addOption(OptionType.USER, "player4", "A player you want to challenge", false),
						new SubcommandData("accept", "Accept a battle request from another player.")
				)
		)

		;

		CommandEntry(Function<GenericInteractionCreateEvent, ? extends PokeworldCommand> constructor, SlashCommandData data)
		{
			this(constructor, false, data);
		}

		CommandEntry(Function<GenericInteractionCreateEvent, ? extends PokeworldCommand> constructor, boolean devOnly, SlashCommandData data)
		{
			COMMANDS.put(data.getName(), new CommandData(constructor, devOnly, data));
		}
	}
}
