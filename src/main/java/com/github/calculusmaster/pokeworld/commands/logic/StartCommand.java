package com.github.calculusmaster.pokeworld.commands.logic;

import com.github.calculusmaster.pokeworld.commands.PokeworldCommand;
import com.github.calculusmaster.pokeworld.db.PokeworldPlayer;
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
		PokeworldPlayer player = PokeworldPlayer.add(this.user.getId(), this.user.getName());
		event.reply("Started!").queue();
	}

	@Override
	public void handleAutocomplete(CommandAutoCompleteInteractionEvent event)
	{

	}
}
