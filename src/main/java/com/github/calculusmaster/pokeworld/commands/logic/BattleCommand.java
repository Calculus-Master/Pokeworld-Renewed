package com.github.calculusmaster.pokeworld.commands.logic;

import com.github.calculusmaster.pokeworld.battle.BattleManager;
import com.github.calculusmaster.pokeworld.battle.BattleRequest;
import com.github.calculusmaster.pokeworld.commands.PokeworldCommand;
import com.github.calculusmaster.pokeworld.db.PokeworldPlayer;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

		if(sub.equals("challenge"))
		{
			// Verify that the initiator isn't already in a battle
			if(BattleManager.isInBattle(player.getID())) return this.error(event, "You are already in another battle.");
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

			final String inBattleError = "The player %s is already in another battle.";

			OptionMapping player2 = Objects.requireNonNull(event.getOption("player2"));
			if(BattleManager.isInBattle(player2.getAsUser().getId())) return this.error(event, inBattleError.formatted(player2.getAsUser().getName()));
			else requested.add(player2.getAsUser().getId());

			OptionMapping player3 = event.getOption("player3");
			if(player3 != null && BattleManager.isInBattle(player3.getAsUser().getId())) return this.error(event, inBattleError.formatted(player3.getAsUser().getName()));
			else if(player3 != null) requested.add(player3.getAsUser().getId());

			OptionMapping player4 = event.getOption("player4");
			if(player4 != null && BattleManager.isInBattle(player4.getAsUser().getId())) return this.error(event, inBattleError.formatted(player4.getAsUser().getName()));
			else if(player4 != null) requested.add(player4.getAsUser().getId());

			// Create the battle request
			BattleManager.createRequest(player.getID(), requested);

			// Notify players
			event.reply("%s: You have been challenged to a battle by %s! Use `/battle accept` or `/battle deny` to respond.".formatted(requested.stream().map("<@%s>"::formatted).collect(Collectors.joining(", ")), this.user.getAsMention())).queue();
		}
		else return this.error(event);

		return true;
	}
}
