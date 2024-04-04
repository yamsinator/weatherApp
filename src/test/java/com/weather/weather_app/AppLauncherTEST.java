package com.weather.weather_app;

import javax.swing.SwingUtilities;

public class AppLauncherTEST {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// Display weather app
				new WeatherAppGUI().setVisible(true);
			}
		});
	}
}
