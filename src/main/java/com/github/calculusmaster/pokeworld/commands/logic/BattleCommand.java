package com.github.calculusmaster.pokeworld.commands.logic;

import com.github.calculusmaster.pokeworld.battle.BattleManager;
import com.github.calculusmaster.pokeworld.battle.BattleRequest;
import com.github.calculusmaster.pokeworld.commands.PokeworldCommand;
import com.github.calculusmaster.pokeworld.db.PokeworldPlayer;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BattleCommand extends PokeworldCommand
{
	public BattleCommand(GenericInteractionCreateEvent event)
	{
		super(event);
	}

	@Override
	public boolean execute(SlashCommandInteractionEvent event)
	{
		String sub = Objects.requireNonNull(event.getSubcommandName());
		PokeworldPlayer player = this.getPlayer();

		switch(sub)
		{
			case "challenge" ->
			{
				// Verify that the initiator isn't already in a battle
				if(BattleManager.isInBattle(player.getID()))
					return this.error(event, "You are already in another battle.");
				else if(BattleManager.hasRequest(player.getID()))
				{
					BattleRequest request = BattleManager.getRequest(player.getID());

					return request.isInitiator(player.getID())
							? this.error(event, "You have already initiated a battle request. Please wait for the other players to accept, or cancel it using `/battle cancel`.")
							: this.error(event, "You already have a pending battle request. Use `/battle accept` or `/battle deny` to respond to it.");
				}

				// Verify that challengers aren't already in a battle, and aggregate them
				// Player 2 is required, the others are optional
				List<String> requested = new ArrayList<>();

				final Function<OptionMapping, Boolean> checkPlayer = playerOption -> {
					if(playerOption == null) return true; // Optional so return true
					else
					{
						User targetUser = playerOption.getAsUser();
						if(BattleManager.isInBattle(targetUser.getId()))
							return this.error(event, "The player %s is already in another battle.".formatted(targetUser.getName()));
						else if(!PokeworldPlayer.exists(targetUser.getId()))
							return this.error(event, "The player %s has not started their adventure yet. Ask them to use `/start` to begin!".formatted(targetUser.getName()));
						else requested.add(targetUser.getId());
					}

					return true;
				};

				if(!checkPlayer.apply(Objects.requireNonNull(event.getOption("player2")))) return false;
				if(!checkPlayer.apply(event.getOption("player3"))) return false;
				if(!checkPlayer.apply(event.getOption("player4"))) return false;

				// Create the battle request
				BattleManager.createRequest(player.getID(), requested);

				// Notify players
				event.reply("%s: You have been challenged to a battle by %s! Use `/battle accept` or `/battle deny` to respond.".formatted(requested.stream().map("<@%s>"::formatted).collect(Collectors.joining(", ")), this.user.getAsMention())).queue();
			}
			case "accept" ->
			{
				if(BattleManager.isInBattle(player.getID()))
					return this.error(event, "You are already in another battle.");
				else if(!BattleManager.hasRequest(player.getID()))
					return this.error(event, "You do not have any pending battle requests.");

				BattleRequest request = BattleManager.getRequest(player.getID());

				if(request.isInitiator(player.getID()))
					return this.error(event, "You cannot accept your own battle request.");
				else if(request.hasAccepted(player.getID()))
					return this.error(event, "You have already accepted this battle request.");

				request.setAccepted(player.getID());

				event.reply("You have accepted the battle request! Waiting for the other players to accept...").queue();
			}
			case "deny" ->
			{
			}
			default ->
			{
				return this.error(event);
			}
		}

		return true;
	}
}
