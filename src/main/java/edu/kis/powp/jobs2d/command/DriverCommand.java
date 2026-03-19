package edu.kis.powp.jobs2d.command;

/**
 * Command interface representing a command that can be executed on a device.
 * Implements the Command design pattern.
 */
public interface DriverCommand {
	/**
	 * Execute the command.
	 */
	void execute();
}
