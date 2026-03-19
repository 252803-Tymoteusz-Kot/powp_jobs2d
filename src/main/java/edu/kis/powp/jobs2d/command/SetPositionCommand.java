package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

/**
 * Command for setting the position of the device.
 * Encapsulates the setPosition operation.
 */
public class SetPositionCommand implements DriverCommand {
	private final Job2dDriver driver;
	private final int x;
	private final int y;

	/**
	 * Creates a SetPositionCommand.
	 *
	 * @param driver The driver on which to execute the command.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public SetPositionCommand(Job2dDriver driver, int x, int y) {
		this.driver = driver;
		this.x = x;
		this.y = y;
	}

	@Override
	public void execute() {
		driver.setPosition(x, y);
	}

	@Override
	public String toString() {
		return "SetPositionCommand(" + x + ", " + y + ")";
	}
}
