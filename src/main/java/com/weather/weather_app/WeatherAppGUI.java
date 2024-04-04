package com.weather.weather_app;

import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class WeatherAppGUI extends JFrame {

	private JTextField searchBar;
	private JLabel weatherConditionImage;
	private JLabel temperatureText;
	private JLabel weatherCondDesc;
	private JLabel humidityText;
	private JLabel windspeedText;
	private JLabel loadingLabel;
	private JSONObject weatherData;

	public WeatherAppGUI() {
		// Set up the GUI and add a title.
		super("Weather App");

		// Configure GUI to end the program's process once it has been closed.
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Set the size of our GUI (in pixels).
		setSize(450, 650);

		// Load the GUI at the center of the screen.
		setLocationRelativeTo(null);

		// Make the layout manager null to manually position the components within the
		// GUI.
		setLayout(null);

		// Prevent any resizing of the GUI.
		setResizable(false);

		addGuiComponents();
	}

	private void addGuiComponents() {
		// Create the search field.
		searchBar = new JTextField();
		searchBar.setBounds(15, 15, 351, 45);
		searchBar.setFont(new Font("Helvetica", Font.PLAIN, 24));
		add(searchBar);

		// Add the loading label.
		loadingLabel = new JLabel("Loading...");
		loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loadingLabel.setBounds(0, 70, 450, 36);
		loadingLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
		loadingLabel.setForeground(Color.BLACK);
		loadingLabel.setVisible(false);
		add(loadingLabel);

		// Add the weather image.
		weatherConditionImage = new JLabel(loadImage("cloudy.png"));
		weatherConditionImage.setBounds(0, 125, 450, 217);
		add(weatherConditionImage);

		// Add the temperature text.
		temperatureText = new JLabel("10 F");
		temperatureText.setBounds(0, 350, 450, 54);
		temperatureText.setFont(new Font("Helvetica", Font.BOLD, 48));

		// Center the text.
		temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
		add(temperatureText);

		// Add the weather condition description.
		weatherCondDesc = new JLabel("Cloudy");
		weatherCondDesc.setBounds(0, 405, 450, 36);
		weatherCondDesc.setFont(new Font("Helvetica", Font.PLAIN, 32));
		weatherCondDesc.setHorizontalAlignment(SwingConstants.CENTER);
		add(weatherCondDesc);

		// Add the humidity image.
		JLabel humidityImage = new JLabel(loadImage("humidity.png"));
		humidityImage.setBounds(15, 500, 74, 66);
		add(humidityImage);

		// Add the humidity text.
		humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
		humidityText.setBounds(90, 500, 85, 55);
		humidityText.setFont(new Font("Helvetica", Font.PLAIN, 16));
		add(humidityText);

		// Add the windspeed image.
		JLabel windspeedImage = new JLabel(loadImage("windspeed.png"));
		windspeedImage.setBounds(220, 500, 74, 66);
		add(windspeedImage);

		// Add the windspeed text.
		windspeedText = new JLabel("<html><b>Windspeed</b> 15mph</html>");
		windspeedText.setBounds(310, 500, 85, 55);
		windspeedText.setFont(new Font("Helvetica", Font.PLAIN, 15));
		add(windspeedText);

		// Add the search button.
		final JButton searchButton = new JButton(loadImage("search.png"));

		// Change the cursor to a hand cursor when hovering over this button.
		searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchButton.setBounds(375, 13, 47, 45);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Show the loading indicator.
				loadingLabel.setText("Loading...");
				loadingLabel.setVisible(true);

				// Get location from user.
				String userInput = searchBar.getText();

				// Validate input - remove whitespace to ensure non-empty text.
				if (userInput.replaceAll("\\s", "").length() <= 0) {
					// Hide loading indicator if search is empty.
					loadingLabel.setVisible(false);
					return;
				}

				// Retrieve weather data (Simulated delay for 2 seconds).
				Timer timer = new Timer(2000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						weatherData = WeatherApp.getWeatherData(userInput);

						// Update GUI.

						// Update weather image.
						String weatherCondition = (String) weatherData.get("weather_condition");
						switch (weatherCondition) {
						case "Clear":
							weatherConditionImage.setIcon(loadImage("clear.png"));
							break;
						case "Cloudy":
							weatherConditionImage.setIcon(loadImage("cloudy.png"));
							break;
						case "Rain":
							weatherConditionImage.setIcon(loadImage("rain.png"));
							break;
						case "Snow":
							weatherConditionImage.setIcon(loadImage("snow.png"));
							break;
						}

						// Update temperature text.
						double temperature = (double) weatherData.get("temperature");
						temperatureText.setText(temperature + " F");

						// Update weather condition text.
						weatherCondDesc.setText(weatherCondition);

						// Update humidity text.
						long humidity = (long) weatherData.get("humidity");
						humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

						// Update windspeed text.
						double windspeed = (double) weatherData.get("windspeed");
						windspeedText.setText("<html><b>Windspeed</b> " + windspeed + "mph</html>");

						// Hide loading indicator and show Label for location search
						loadingLabel.setText("Weather for " + userInput);
						loadingLabel.setVisible(true);
					}
				});
				timer.setRepeats(false);
				timer.start();
			}
		});

		searchBar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					searchButton.doClick(); // Simulates click on search button
				}
			}
		});
		add(searchButton);
	}

	// Used to load images in our GUI components.
	private ImageIcon loadImage(String fileName) {
	    try {
	        // Read the image file from the root directory of the JAR.
	        BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/" + fileName));
	        // Return an ImageIcon so that our component can render it.
	        return new ImageIcon(image);
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Could not find resource: " + fileName);
	        return null;
	    }
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			WeatherAppGUI gui = new WeatherAppGUI();
			gui.setVisible(true);
		});
	}
}
