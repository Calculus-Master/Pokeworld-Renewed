package com.github.calculusmaster.pokeworld;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Pokeworld
{
	public static Dotenv ENV;
	public static JDA BOT;

	public static void main(String[] args)
	{
		ENV = Dotenv.load();

		JDABuilder builder = JDABuilder.createDefault(ENV.get("BOT_TOKEN"));

		System.out.println("Loaded!");
	}
}
