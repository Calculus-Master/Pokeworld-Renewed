package com.github.calculusmaster.pokeworld.commands.logic;

import com.github.calculusmaster.pokeworld.commands.PokeworldCommand;
import com.github.calculusmaster.pokeworld.db.PokeworldPlayer;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class StartCommand extends PokeworldCommand
{
	public StartCommand(GenericInteractionCreateEvent event)
	{
		super(event);
	}

	@Override
	public boolean execute(SlashCommandInteractionEvent event)
	{
		OptionMapping starterOption = event.getOption("starter");

		if(PokeworldPlayer.exists(this.user.getId())) return this.error(event, "You have already started your adventure!");

		PokeworldPlayer player = PokeworldPlayer.add(this.user.getId(), this.user.getName());
		event.reply("Started!").queue();

		return true;
	}

	@Override
	public void handleAutocomplete(CommandAutoCompleteInteractionEvent event)
	{

	}
}
