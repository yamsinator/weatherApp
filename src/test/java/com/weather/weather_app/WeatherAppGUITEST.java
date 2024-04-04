package com.weather.weather_app;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class WeatherAppGUITEST extends JFrame {
    
	public WeatherAppGUITEST() {
		//Sets up GUI and title
		super("Weather App");
		
		// Configure the GUI to close when it has been closed
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Set size of GUI when opened
		setSize(450, 650);
		
		// Load GUI in the center of the screen
		setLocationRelativeTo(null);
		
		// Layout manager
		setLayout(null);
		
		// Resize GUI
		setResizable(true);
		
		addGuiComponents();
	}
	
	private void addGuiComponents() {
		
		// Search Field
		JTextField searchBar = new JTextField();
		
		// Set location and size of the search field
		searchBar.setBounds(15, 15, 351, 45);
		
		// Change font style and size
		searchBar.setFont(new Font("Helvetica", Font.PLAIN, 25));
		
		// Search Button
		JButton searchButton = new JButton(loadImage("src/main/resources/appImages/search.png"));
		
		// Change cursor
		searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchButton.setBounds(375, 13, 47, 45);
		add(searchButton);
	}
	
	// Create loadImage function
	private ImageIcon loadImage(String resourcePath) {
		try {
			// Read image file
			BufferedImage image = ImageIO.read(new File(resourcePath));
			
			return new ImageIcon(image);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print("Could not find resource");
		return null;
	}
	
}
