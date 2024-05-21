package com.github.calculusmaster.pokeworld.commands.logic;

import com.github.calculusmaster.pokeworld.Pokeworld;
import com.github.calculusmaster.pokeworld.commands.PokeworldCommand;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ShutdownCommand extends PokeworldCommand
{
	public ShutdownCommand(GenericInteractionCreateEvent event)
	{
		super(event);
	}

	@Override
	public boolean execute(SlashCommandInteractionEvent event)
	{
		event.reply("Initiating shutdown...").setEphemeral(true).queue();

		Executors.newSingleThreadScheduledExecutor().schedule(Pokeworld::shutdown, 2, TimeUnit.SECONDS);

		return true;
	}
}
