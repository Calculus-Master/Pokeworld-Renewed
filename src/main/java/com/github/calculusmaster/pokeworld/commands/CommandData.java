package com.github.calculusmaster.pokeworld.commands;

import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.function.Supplier;

public record CommandData(Supplier<? extends PokeworldCommand> constructor, SlashCommandData slashCommandData)
{

}
