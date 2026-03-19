package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

/**
 * Fabryka poleceń rysujących okrąg (Circle Command Factory).
 */
public class CircleCommandFactory {
	
	/**
	 * Zwraca ComplexCommand rysujący okrąg.
	 * 
	 * @param driver Sterownik do rysowania
	 * @param centerX Pozycja X (środek okręgu)
	 * @param centerY Pozycja Y (środek okręgu)
	 * @param radius Promień okręgu
	 * @param segments Liczba segmentów (więcej = dokładniejszy okrąg, minimum 4)
	 * @return ComplexCommand reprezentujący rysowanie okręgu
	 */
	public static ComplexCommand createCircle(Job2dDriver driver, int centerX, int centerY, int radius, int segments) {
		ComplexCommand circle = new ComplexCommand("Okrąg r=" + radius);
		
		if (segments < 4) {
			segments = 4;
		}
		
		// Pozycja startowa (punkt na okręgu)
		double angle = 0;
		int startX = centerX + (int)(radius * Math.cos(angle));
		int startY = centerY + (int)(radius * Math.sin(angle));
		circle.addCommand(new SetPositionCommand(driver, startX, startY));
		
		// Rysuj segmenty okręgu
		double angleStep = 2 * Math.PI / segments;
		for (int i = 1; i <= segments; i++) {
			angle = i * angleStep;
			int nextX = centerX + (int)(radius * Math.cos(angle));
			int nextY = centerY + (int)(radius * Math.sin(angle));
			circle.addCommand(new OperateToCommand(driver, nextX, nextY));
		}
		
		return circle;
	}
	
	/**
	 * Wersja uproszczona - domyślnie 32 segmenty dla gładkiego okręgu.
	 */
	public static ComplexCommand createCircle(Job2dDriver driver, int centerX, int centerY, int radius) {
		return createCircle(driver, centerX, centerY, radius, 32);
	}
}
