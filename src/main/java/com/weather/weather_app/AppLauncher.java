package com.weather.weather_app;

import javax.swing.SwingUtilities;

public class AppLauncher {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// Display weather app
				new WeatherAppGUI().setVisible(true);
				
//				System.out.println(WeatherApp.getLocationData("Tokyo"));
				
//				System.out.println(WeatherApp.getCurrentTime());
			}
		});
	}
}
