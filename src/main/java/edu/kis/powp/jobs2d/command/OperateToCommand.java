package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

/**
 * Command for operating to a specific position (drawing a line to a point).
 * Encapsulates the operateTo operation.
 */
public class OperateToCommand implements DriverCommand {
	private final Job2dDriver driver;
	private final int x;
	private final int y;

	/**
	 * Creates an OperateToCommand.
	 *
	 * @param driver The driver on which to execute the command.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public OperateToCommand(Job2dDriver driver, int x, int y) {
		this.driver = driver;
		this.x = x;
		this.y = y;
	}

	@Override
	public void execute() {
		driver.operateTo(x, y);
	}

	@Override
	public String toString() {
		return "OperateToCommand(" + x + ", " + y + ")";
	}
}
