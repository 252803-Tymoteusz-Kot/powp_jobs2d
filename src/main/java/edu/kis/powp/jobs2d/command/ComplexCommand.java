package edu.kis.powp.jobs2d.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Złożone polecenie (Complex Command) implementujące wzorzec Composite.
 */
public class ComplexCommand implements DriverCommand {
	
	private final List<DriverCommand> commands;
	private final String description;
	
	/**
	 * Konstruktor kompleksowego polecenia.
	 * 
	 * @param description Opis kompleksowego polecenia (np. "Rysuj prostokąt")
	 */
	public ComplexCommand(String description) {
		this.description = description;
		this.commands = new ArrayList<>();
	}
	
	/**
	 * Dodaj polecenie do sekwencji.
	 * 
	 * @param command Polecenie do dodania (może być proste lub złożone)
	 */
	public void addCommand(DriverCommand command) {
		commands.add(command);
	}
	
	/**
	 * Dodaj wiele poleceń na raz.
	 * 
	 * @param commandsToAdd Tablica poleceń do dodania
	 */
	public void addCommands(DriverCommand... commandsToAdd) {
		for (DriverCommand cmd : commandsToAdd) {
			commands.add(cmd);
		}
	}
	
	/**
	 * Usuń polecenie z sekwencji.
	 * 
	 * @param command Polecenie do usunięcia
	 * @return true jeśli polecenie zostało usunięte, false jeśli go nie było
	 */
	public boolean removeCommand(DriverCommand command) {
		return commands.remove(command);
	}
	
	/**
	 * Wyczyść wszystkie polecenia.
	 */
	public void clear() {
		commands.clear();
	}
	
	/**
	 * Zwróć liczbę poleceń w tej sekwencji.
	 * 
	 * @return Liczba poleceń
	 */
	public int getCommandCount() {
		return commands.size();
	}
	
	/**
	 * Sprawdź czy sekwencja jest pusta.
	 * 
	 * @return true jeśli brak poleceń
	 */
	public boolean isEmpty() {
		return commands.isEmpty();
	}
	
	/**
	 * Wykonaj wszystkie polecenia w sekwencji.
	 * 
	 * Polecenia wykonywane są po kolei w kolejności dodania.
	 * Jeśli jedno z poleceń jest ComplexCommand, jego polecenia
	 * również zostaną wykonane (rekurencyjnie).
	 */
	@Override
	public void execute() {
		for (DriverCommand command : commands) {
			command.execute();
		}
	}
	
	/**
	 * Zwróć opis tego kompleksowego polecenia.
	 * 
	 * @return Opis oraz liczba poleceń w sekwencji
	 */
	@Override
	public String toString() {
		return description + " [" + commands.size() + " poleceń]";
	}
}
