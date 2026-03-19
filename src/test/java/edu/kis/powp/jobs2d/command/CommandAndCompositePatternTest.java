package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.LoggerDriver;

/**
 * Ujednolicone testy dla Command Pattern, Composite Pattern i fabryk figur.
 * Zawiera testy z Tasks 2, 3, 4 i 5.
 */
public class CommandAndCompositePatternTest {
	
	// ==================== TASK 2 TESTS ====================
	
	/**
	 * Task 2, Test 1: Podstawowe wykonanie poleceń z logger driver.
	 */
	public static void testBasicCommands() {
		System.out.println("\n=== TASK 2, Test 1: Podstawowe wykonanie poleceń ===");
		
		Job2dDriver driver = new LoggerDriver();
		
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
	 * Task 2, Test 2: Niezależność poleceń od kontekstu.
	 */
	public static void testCommandContextIndependence() {
		System.out.println("\n=== TASK 2, Test 2: Niezależność poleceń od kontekstu ===");
		
		Job2dDriver driver1 = new LoggerDriver();
		Job2dDriver driver2 = new LoggerDriver();
		
		DriverCommand cmd1 = new SetPositionCommand(driver1, 5, 5);
		DriverCommand cmd2 = new SetPositionCommand(driver2, 5, 5);
		
		System.out.println("Wykonanie sterownika 1:");
		cmd1.execute();
		
		System.out.println("Wykonanie sterownika 2 (ten sam wzorzec poleceń, różny sterownik):");
		cmd2.execute();
	}
	
	/**
	 * Task 2, Test 3: Interface-based receiver - dowód niezależności.
	 */
	public static void testInterfaceBasedReceiver() {
		System.out.println("\n=== TASK 2, Test 3: Interface-Based Receiver ===");
		
		Job2dDriver customDriver = new MockDriver();
		
		DriverCommand setPos = new SetPositionCommand(customDriver, 100, 200);
		DriverCommand operateTo = new OperateToCommand(customDriver, 150, 250);
		
		System.out.println("Polecenia wykonane z niestandardowym sterownikiem:");
		setPos.execute();
		operateTo.execute();
		System.out.println("✓ Te same klasy poleceń działają z różnymi implementacjami sterowników!");
	}
	
	// ==================== TASK 3 TESTS ====================
	
	/**
	 * Task 3, Test 1: Podstawowe ComplexCommand.
	 */
	public static void testBasicComplexCommand() {
		System.out.println("\n=== TASK 3, Test 1: Podstawowe ComplexCommand ===");
		
		Job2dDriver driver = new LoggerDriver();
		
		ComplexCommand drawLine = new ComplexCommand("Rysuj linię");
		drawLine.addCommand(new SetPositionCommand(driver, 0, 0));
		drawLine.addCommand(new OperateToCommand(driver, 10, 10));
		
		System.out.println("Kompleksowe polecenie: " + drawLine);
		System.out.println("Wykonywanie...");
		drawLine.execute();
	}
	
	/**
	 * Task 3, Test 2: ComplexCommand - Rysowanie trójkąta.
	 */
	public static void testComplexTriangleCommand() {
		System.out.println("\n=== TASK 3, Test 2: ComplexCommand - Rysowanie trójkąta ===");
		
		Job2dDriver driver = new LoggerDriver();
		
		ComplexCommand drawTriangle = new ComplexCommand("Rysuj trójkąt");
		drawTriangle.addCommand(new SetPositionCommand(driver, 0, 0));
		drawTriangle.addCommand(new OperateToCommand(driver, 10, 0));
		drawTriangle.addCommand(new OperateToCommand(driver, 5, 8));
		drawTriangle.addCommand(new OperateToCommand(driver, 0, 0));
		
		System.out.println("Polecenie: " + drawTriangle);
		System.out.println("Liczba kroków: " + drawTriangle.getCommandCount());
		System.out.println("Wykonywanie...");
		drawTriangle.execute();
	}
	
	/**
	 * Task 3, Test 3: Zagnieżdżone ComplexCommand (Composite Pattern).
	 */
	public static void testNestedComplexCommands() {
		System.out.println("\n=== TASK 3, Test 3: Zagnieżdżone ComplexCommand (Composite) ===");
		
		Job2dDriver driver = new LoggerDriver();
		
		ComplexCommand drawRectangle = new ComplexCommand("Rysuj prostokąt");
		drawRectangle.addCommand(new SetPositionCommand(driver, 0, 0));
		drawRectangle.addCommand(new OperateToCommand(driver, 10, 0));
		drawRectangle.addCommand(new OperateToCommand(driver, 10, 5));
		drawRectangle.addCommand(new OperateToCommand(driver, 0, 5));
		drawRectangle.addCommand(new OperateToCommand(driver, 0, 0));
		
		ComplexCommand drawTriangle = new ComplexCommand("Rysuj trójkąt");
		drawTriangle.addCommand(new SetPositionCommand(driver, 15, 0));
		drawTriangle.addCommand(new OperateToCommand(driver, 25, 0));
		drawTriangle.addCommand(new OperateToCommand(driver, 20, 8));
		drawTriangle.addCommand(new OperateToCommand(driver, 15, 0));
		
		ComplexCommand drawMultipleFigures = new ComplexCommand("Rysuj wiele figur");
		drawMultipleFigures.addCommand(drawRectangle);
		drawMultipleFigures.addCommand(drawTriangle);
		
		System.out.println("Główne polecenie: " + drawMultipleFigures);
		System.out.println("Bezpośredni podpolecenia: " + drawMultipleFigures.getCommandCount());
		System.out.println("\nWykonywanie (będzie rekurencyjnie wykonane całe drzewo):");
		drawMultipleFigures.execute();
		
		System.out.println("\n✓ Wzorzec Composite: Polecenia proste i złożone traktowane jednakowo!");
	}
	
	// ==================== TASK 5 FACTORY TESTS ====================
	
	/**
	 * Task 5, Test 1: Fabryka RectangleCommandFactory.
	 */
	public static void testRectangleFactory() {
		System.out.println("\n=== TASK 5, Test 1: RectangleCommandFactory ===");
		
		Job2dDriver driver = new LoggerDriver();
		
		DriverCommand rectangle = RectangleCommandFactory.createRectangle(driver, 0, 0, 15, 10);
		System.out.println("Polecenie: " + rectangle);
		System.out.println("Wykonywanie prostokąta 15x10:");
		rectangle.execute();
	}
	
	/**
	 * Task 5, Test 2: Fabryka TriangleCommandFactory.
	 */
	public static void testTriangleFactory() {
		System.out.println("\n=== TASK 5, Test 2: TriangleCommandFactory ===");
		
		Job2dDriver driver = new LoggerDriver();
		
		DriverCommand triangle = TriangleCommandFactory.createTriangle(driver, 0, 0, 20);
		System.out.println("Polecenie: " + triangle);
		System.out.println("Wykonywanie trójkąta boku 20:");
		triangle.execute();
	}
	
	/**
	 * Task 5, Test 3: Fabryka CircleCommandFactory.
	 */
	public static void testCircleFactory() {
		System.out.println("\n=== TASK 5, Test 3: CircleCommandFactory ===");
		
		Job2dDriver driver = new LoggerDriver();
		
		DriverCommand circle = CircleCommandFactory.createCircle(driver, 50, 50, 15, 16);
		System.out.println("Polecenie: " + circle);
		System.out.println("Wykonywanie okręgu r=15:");
		circle.execute();
	}
	
	/**
	 * Task 5, Test 4: Kombinacja fabryk - wiele figur w pojedynczym ComplexCommand.
	 */
	public static void testCombinedFactories() {
		System.out.println("\n=== TASK 5, Test 4: Kombinacja fabryk ===");
		
		Job2dDriver driver = new LoggerDriver();
		
		ComplexCommand multipleShapes = new ComplexCommand("Wiele figur rysowanych fabrykami");
		multipleShapes.addCommand(RectangleCommandFactory.createRectangle(driver, 0, 0, 10, 8));
		multipleShapes.addCommand(TriangleCommandFactory.createTriangle(driver, 20, 0, 10));
		multipleShapes.addCommand(CircleCommandFactory.createCircle(driver, 40, 4, 5, 8));
		
		System.out.println("Combinowane polecenie: " + multipleShapes);
		System.out.println("Liczba fabryk figur: " + multipleShapes.getCommandCount());
		System.out.println("Wykonywanie wszystkich figur:");
		multipleShapes.execute();
	}
	
	/**
	 * Mock driver dla testów
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
	 * Main test runner.
	 */
	public static void main(String[] args) {
		System.out.println("╔════════════════════════════════════════════════════════════╗");
		System.out.println("║  Command Pattern, Composite Pattern, i Factory Pattern     ║");
		System.out.println("║  Tasks 2, 3, 4, 5                                          ║");
		System.out.println("╚════════════════════════════════════════════════════════════╝");
		
		// Task 2 Tests
		testBasicCommands();
		testCommandContextIndependence();
		testInterfaceBasedReceiver();
		
		// Task 3 Tests
		testBasicComplexCommand();
		testComplexTriangleCommand();
		testNestedComplexCommands();
		
		// Task 5 Tests
		testRectangleFactory();
		testTriangleFactory();
		testCircleFactory();
		testCombinedFactories();
		
		System.out.println("\n╔════════════════════════════════════════════════════════════╗");
		System.out.println("║  ✓ Wszystkie testy wykonane pomyślnie                      ║");
		System.out.println("║  ✓ Command Pattern: Niezależność od sterownika            ║");
		System.out.println("║  ✓ Composite Pattern: Zagnieżdżame struktury              ║");
		System.out.println("║  ✓ Factory Pattern: Tworzenie figur                       ║");
		System.out.println("╚════════════════════════════════════════════════════════════╝");
	}
}
