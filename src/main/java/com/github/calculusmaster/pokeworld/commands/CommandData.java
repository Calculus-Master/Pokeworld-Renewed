package com.github.calculusmaster.pokeworld.commands;

import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.function.Function;

public record CommandData(
		Function<GenericInteractionCreateEvent, ? extends PokeworldCommand> constructor,
		SlashCommandData slashCommandData,
		boolean devOnly
)
{

}
