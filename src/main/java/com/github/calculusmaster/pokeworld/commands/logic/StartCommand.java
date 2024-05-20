package com.github.calculusmaster.pokeworld.commands.logic;

import com.github.calculusmaster.pokeworld.commands.PokeworldCommand;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class StartCommand extends PokeworldCommand
{
	public StartCommand(GenericInteractionCreateEvent event)
	{
		super(event);
	}

	@Override
	public void execute(SlashCommandInteractionEvent event)
	{
		event.reply("Start!").queue();
	}

	@Override
	public void handleAutocomplete(CommandAutoCompleteInteractionEvent event)
	{

	}
}
