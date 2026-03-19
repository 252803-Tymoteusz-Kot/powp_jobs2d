package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

/**
 * Fabryka poleceń rysujących prostokąt (Rectangle Command Factory).
 */
public class RectangleCommandFactory {
	/**
	 * Zwraca ComplexCommand rysujący prostokąt.
	 * 
	 * @param driver Sterownik do rysowania
	 * @param x Pozycja X (lewy górny róg)
	 * @param y Pozycja Y (lewy górny róg)
	 * @param width Szerokość prostokąta
	 * @param height Wysokość prostokąta
	 * @return ComplexCommand reprezentujący rysowanie prostokąta
	 */
	public static ComplexCommand createRectangle(Job2dDriver driver, int x, int y, int width, int height) {
		ComplexCommand rectangle = new ComplexCommand("Prostokąt " + width + "x" + height);
		
		// Pozycja startowa (lewy górny róg)
		rectangle.addCommand(new SetPositionCommand(driver, x, y));
		
		// Linia górna (prawo)
		rectangle.addCommand(new OperateToCommand(driver, x + width, y));
		
		// Linia prawa (dół)
		rectangle.addCommand(new OperateToCommand(driver, x + width, y + height));
		
		// Linia dolna (lewo)
		rectangle.addCommand(new OperateToCommand(driver, x, y + height));
		
		// Linia lewa (góra) - zamknięcie
		rectangle.addCommand(new OperateToCommand(driver, x, y));
		
		return rectangle;
	}
}
