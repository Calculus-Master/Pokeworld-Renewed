package com.github.calculusmaster.pokeworld.commands.logic;

import com.github.calculusmaster.pokeworld.commands.PokeworldCommand;
import com.github.calculusmaster.pokeworld.db.PokeworldPlayer;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class CreditsCommand extends PokeworldCommand
{
	public CreditsCommand(GenericInteractionCreateEvent event)
	{
		super(event);
	}

	@Override
	public boolean execute(SlashCommandInteractionEvent event)
	{
		PokeworldPlayer player = this.getPlayer();

		event.reply("You have **%d** credits.".formatted(player.getCredits())).queue();

		return true;
	}
}
