package com.github.calculusmaster.pokeworld.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;

public abstract class PokeworldCommand
{
	protected final GenericInteractionCreateEvent event;
	protected User user;
	protected Guild server;

	public PokeworldCommand(GenericInteractionCreateEvent event)
	{
		this.event = event;

		this.user = event.getUser();
		this.server = event.getGuild();
	}

	// Logic

	public abstract boolean execute(SlashCommandInteractionEvent event);
	public abstract void handleAutocomplete(CommandAutoCompleteInteractionEvent event);

	public boolean handleButton(ButtonInteractionEvent event) { return false; }
	public boolean handleModal(ModalInteractionEvent event) { return false; }

	// Utilities

	protected boolean error(IReplyCallback callback, String message)
	{
		callback.reply(message).setEphemeral(true).queue();
		return false;
	}
}