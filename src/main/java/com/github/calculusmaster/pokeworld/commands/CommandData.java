package com.github.calculusmaster.pokeworld.commands;

import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.function.Function;

public record CommandData(
		Function<GenericCommandInteractionEvent, ? extends PokeworldCommand> constructor,
		SlashCommandData slashCommandData
)
{

}
