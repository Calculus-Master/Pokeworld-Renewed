package com.github.calculusmaster.pokeworld.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public abstract class PokeworldCommand
{
	protected User user;
	protected Guild server;

	public PokeworldCommand(GenericInteractionCreateEvent event)
	{
		this.user = event.getUser();
		this.server = event.getGuild();
	}

	public abstract void execute(SlashCommandInteractionEvent event);
	public abstract void handleAutocomplete(CommandAutoCompleteInteractionEvent event);

	public void handleButton(ButtonInteractionEvent event) {}
	public void handleModal(ModalInteractionEvent event) {}
}