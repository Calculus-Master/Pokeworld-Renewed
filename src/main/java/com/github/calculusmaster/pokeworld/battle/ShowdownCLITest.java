package com.github.calculusmaster.pokeworld.battle;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ShowdownCLITest
{
	public static void main(String[] args) throws IOException
	{
		Dotenv env = Dotenv.load();

		ProcessBuilder builder = new ProcessBuilder(env.get("SHOWDOWN_SIM_DIR") + "pokemon-showdown", "simulate-battle").redirectErrorStream(true).inheritIO();

		Process p = builder.start();

		Scanner scanner = new Scanner(System.in);
		Scanner scannerIn = new Scanner(p.getInputStream());

		while(p.isAlive())
		{
			while (scanner.hasNext())
			{
				String i = scanner.nextLine() + "\n";
				p.outputWriter().write(i);

				System.out.println("---Sent:----");
				System.out.println(i);
				System.out.println("------------");

				try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream())))
				{
					reader.lines().forEach(System.out::println);
				}
			}
		}
	}
}
