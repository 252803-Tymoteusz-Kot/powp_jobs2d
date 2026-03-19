package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

/**
 * Fabryka poleceń rysujących trójkąt (Triangle Command Factory).
 */
public class TriangleCommandFactory {
	
	/**
	 * Zwraca ComplexCommand rysujący trójkąt równoboczny.
	 * 
	 * @param driver Sterownik do rysowania
	 * @param x Pozycja X (lewy dolny róg)
	 * @param y Pozycja Y (lewy dolny róg)
	 * @param side Długość boku trójkąta
	 * @return ComplexCommand reprezentujący rysowanie trójkąta
	 */
	public static ComplexCommand createTriangle(Job2dDriver driver, int x, int y, int side) {
		ComplexCommand triangle = new ComplexCommand("Trójkąt równoboczny " + side);
		
		// Pozycja startowa (lewy dolny róg)
		triangle.addCommand(new SetPositionCommand(driver, x, y));
		
		// Pierwszy bok (prawo)
		triangle.addCommand(new OperateToCommand(driver, x + side, y));
		
		// Drugi bok (góra do środka)
		int topX = x + side / 2;
		int topY = y + (int)(side * Math.sqrt(3) / 2);
		triangle.addCommand(new OperateToCommand(driver, topX, topY));
		
		// Trzeci bok (powrót do startu)
		triangle.addCommand(new OperateToCommand(driver, x, y));
		
		return triangle;
	}
}
