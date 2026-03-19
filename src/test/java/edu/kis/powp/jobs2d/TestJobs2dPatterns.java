package edu.kis.powp.jobs2d;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.legacy.drawer.panel.DefaultDrawerFrame;
import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.CircleCommandFactory;
import edu.kis.powp.jobs2d.command.CommandAndCompositePatternTest;
import edu.kis.powp.jobs2d.command.RectangleCommandFactory;
import edu.kis.powp.jobs2d.command.TriangleCommandFactory;
import edu.kis.powp.jobs2d.drivers.adapter.DrawPanelAdapter;
import edu.kis.powp.jobs2d.drivers.adapter.LineDrawerAdapter;
import edu.kis.powp.jobs2d.events.SelectChangeVisibleOptionListener;
import edu.kis.powp.jobs2d.events.SelectTestFigure2OptionListener;
import edu.kis.powp.jobs2d.events.SelectTestFigureOptionListener;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;

public class TestJobs2dPatterns {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Setup test concerning preset figures in context.
	 * 
	 * @param application Application context.
	 */
	private static void setupPresetTests(Application application) {
		SelectTestFigureOptionListener selectTestFigureOptionListener = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager());

		application.addTest("Figure Joe 1", selectTestFigureOptionListener);

		SelectTestFigure2OptionListener selectTestFigure2OptionListener = new SelectTestFigure2OptionListener(
			DriverFeature.getDriverManager());

		application.addTest("Figure Joe 2", selectTestFigure2OptionListener);
	}

	/**
	 * Setup tests for Command and Composite Pattern (Task 5).
	 * Tests factory pattern implementations for creating shapes.
	 * 
	 * @param application Application context.
	 */
	private static void setupCommandPatternTests(Application application) {
		application.addTest("Command Pattern Tests", (ActionEvent e) -> 
			CommandAndCompositePatternTest.main(new String[]{}));
		
		application.addTest("Rectangle Factory", (ActionEvent e) -> {
			Job2dDriver driver = DriverFeature.getDriverManager().getCurrentDriver();
			RectangleCommandFactory.createRectangle(driver, 10, 10, 50, 30).execute();
		});
		
		application.addTest("Triangle Factory", (ActionEvent e) -> {
			Job2dDriver driver = DriverFeature.getDriverManager().getCurrentDriver();
			TriangleCommandFactory.createTriangle(driver, 10, 10, 40).execute();
		});
		
		application.addTest("Circle Factory", (ActionEvent e) -> {
			Job2dDriver driver = DriverFeature.getDriverManager().getCurrentDriver();
			CircleCommandFactory.createCircle(driver, 50, 50, 30).execute();
		});
	}

	/**
	 * Setup driver manager, and set default driver for application.
	 * 
	 * @param application Application context.
	 */
	private static void setupDrivers(Application application) {
		Job2dDriver loggerDriver = new LoggerDriver();
		DriverFeature.addDriver("Logger Driver", loggerDriver);
		DriverFeature.getDriverManager().setCurrentDriver(loggerDriver);

		Job2dDriver testDriver = new DrawPanelAdapter(DrawerFeature.getDrawerController());
		DriverFeature.addDriver("DrawPanelAdapter", testDriver);

		// Tryb linii specjalnej (Special Line)
		Job2dDriver specialLineDriver = new LineDrawerAdapter(DrawerFeature.getDrawerController(), edu.kis.legacy.drawer.shape.LineFactory.getSpecialLine());
		DriverFeature.addDriver("Special Line Simulator", specialLineDriver);

		// Tryb linii przerywanej (Dotted Line)
		Job2dDriver dottedLineDriver = new LineDrawerAdapter(DrawerFeature.getDrawerController(), edu.kis.legacy.drawer.shape.LineFactory.getDottedLine());
		DriverFeature.addDriver("Dotted Line Simulator", dottedLineDriver);

		DriverFeature.updateDriverInfo();
	}

	/**
	 * Auxiliary routines to enable using Buggy Simulator.
	 * 
	 * @param application Application context.
	 */
	private static void setupDefaultDrawerVisibilityManagement(Application application) {
		DefaultDrawerFrame defaultDrawerWindow = DefaultDrawerFrame.getDefaultDrawerFrame();
		application.addComponentMenuElementWithCheckBox(DrawPanelController.class, "Default Drawer Visibility",
				new SelectChangeVisibleOptionListener(defaultDrawerWindow), true);
		defaultDrawerWindow.setVisible(true);
	}

	/**
	 * Setup menu for adjusting logging settings.
	 * 
	 * @param application Application context.
	 */
	private static void setupLogger(Application application) {
		application.addComponentMenu(Logger.class, "Logger", 0);
		application.addComponentMenuElement(Logger.class, "Clear log",
				(ActionEvent e) -> application.flushLoggerOutput());
		application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
		application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
		application.addComponentMenuElement(Logger.class, "Warning level",
				(ActionEvent e) -> logger.setLevel(Level.WARNING));
		application.addComponentMenuElement(Logger.class, "Severe level",
				(ActionEvent e) -> logger.setLevel(Level.SEVERE));
		application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Application app = new Application("2d jobs Visio");
				DrawerFeature.setupDrawerPlugin(app);
				setupDefaultDrawerVisibilityManagement(app);

				DriverFeature.setupDriverPlugin(app);
				setupDrivers(app);
				setupPresetTests(app);
				setupCommandPatternTests(app);
				setupLogger(app);

				app.setVisibility(true);
			}
		});
	}

}
