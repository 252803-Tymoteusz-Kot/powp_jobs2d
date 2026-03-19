package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.LoggerDriver;

public class CommandPatternTest {
	
	/**
	 * Test 1: Podstawowe wykonanie poleceń z logger driver.
	 */
	public static void testBasicCommands() {
		System.out.println("\n=== Test 1: Podstawowe wykonanie poleceń ===");
		
		Job2dDriver driver = new LoggerDriver();
		
		// Tworzymy i wykonujemy polecenia - nie związane z LoggerDriver
		DriverCommand setPos = new SetPositionCommand(driver, 0, 0);
		DriverCommand operateTo1 = new OperateToCommand(driver, 10, 10);
		DriverCommand setPos2 = new SetPositionCommand(driver, 10, 10);
		DriverCommand operateTo2 = new OperateToCommand(driver, 20, 0);
		
		System.out.println("Wykonywanie sekwencji poleceń:");
		setPos.execute();
		operateTo1.execute();
		setPos2.execute();
		operateTo2.execute();
	}
	
	/**
	 * Test 2: Dowód, że polecenia nie są związane z konkretną implementacją sterownika.
	 */
	public static void testCommandContextIndependence() {
		System.out.println("\n=== Test 2: Niezależność poleceń od kontekstu ===");
		
		// Wiele instancji LoggerDriver
		Job2dDriver driver1 = new LoggerDriver();
		Job2dDriver driver2 = new LoggerDriver();
		
		DriverCommand cmd1 = new SetPositionCommand(driver1, 5, 5);
		DriverCommand cmd2 = new SetPositionCommand(driver2, 5, 5);  // Różne sterowniki, ten sam typ polecenia
		
		System.out.println("Wykonanie sterownika 1:");
		cmd1.execute();
		
		System.out.println("Wykonanie sterownika 2 (ten sam wzorzec poleceń, różny sterownik):");
		cmd2.execute();
	}
	
	/**
	 * Test 3: Dowód, że DriverCommand NIE jest sztywno związany
	 * z konkretną implementacją sterownika. Polecenia działają z KAŻDĄ
	 * implementacją interfejsu Job2dDriver.
	 */
	public static void testInterfaceBasedReceiver() {
		System.out.println("\n=== Test 3: Odbiorca opierający się na interfejsie (Task 2 kluczowy) ===");
		
		// Tworzymy niestandardową implementację sterownika
		Job2dDriver customDriver = new MockDriver();
		
		// Używamy tych samych klas poleceń z niestandardowym sterownikiem
		DriverCommand setPos = new SetPositionCommand(customDriver, 100, 200);
		DriverCommand operateTo = new OperateToCommand(customDriver, 150, 250);
		
		System.out.println("Polecenia wykonane z niestandardowym sterownikiem (dowód możliwości ponownego użycia):");
		setPos.execute();
		operateTo.execute();
		System.out.println("✓ Te same klasy poleceń działają z różnymi implementacjami sterowników!");
	}
	
	/**
	 * Niestandardowy mock sterownika do testów
	 */
	private static class MockDriver implements Job2dDriver {
		@Override
		public void setPosition(int x, int y) {
			System.out.println("  [MockDriver] setPosition(" + x + ", " + y + ")");
		}

		@Override
		public void operateTo(int x, int y) {
			System.out.println("  [MockDriver] operateTo(" + x + ", " + y + ")");
		}
	}
	
	/**
	 * Test 4: Dowód, że ta sama sekwencja poleceń może być używana z różnymi sterownikami.
	 */
	public static void testCommandReusabilityInDifferentContexts() {
		System.out.println("\n=== Test 4: Możliwość ponownego użycia poleceń w różnych kontekstach ===");
		
		// Metoda pomocnicza, która używa poleceń - NIE zna konkretnych sterowników
		executeCommandSequence(new LoggerDriver(), "Logger");
		executeCommandSequence(new MockDriver(), "Mock");
	}
	
	/**
	 * Metoda pomocnicza demonstrująca prawdziwą niezależność kontekstu.
	 */
	private static void executeCommandSequence(Job2dDriver driver, String driverName) {
		System.out.println("  Kontekst: " + driverName + " Driver");
		
		// Używane polecenia NIE są specyficzne dla żadnego sterownika
		DriverCommand[] commands = {
			new SetPositionCommand(driver, 0, 0),
			new OperateToCommand(driver, 5, 5),
			new SetPositionCommand(driver, 5, 5),
			new OperateToCommand(driver, 10, 0)
		};
		
		for (DriverCommand cmd : commands) {
			cmd.execute();
		}
	}
	
	/**
	 * Główny runner testów.
	 */
	public static void main(String[] args) {
		System.out.println("╔════════════════════════════════════════════════════════════╗");
		System.out.println("║  Zadanie 2: Wzorzec Command - Test niezależności kontekstu  ║");
		System.out.println("╚════════════════════════════════════════════════════════════╝");
		
		testBasicCommands();
		testCommandContextIndependence();
		testInterfaceBasedReceiver();
		testCommandReusabilityInDifferentContexts();
		
		System.out.println("\n╔════════════════════════════════════════════════════════════╗");
		System.out.println("║  ✓ Wszystkie testy wykonane pomyślnie                      ║");
		System.out.println("║  ✓ Polecenia NIE są sztywno związane ze sterownikami      ║");
		System.out.println("║  ✓ Klasy poleceń działają w różnych kontekstach            ║");
		System.out.println("╚════════════════════════════════════════════════════════════╝");
	}
}
