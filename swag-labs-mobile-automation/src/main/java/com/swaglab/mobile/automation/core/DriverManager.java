package com.swaglab.mobile.automation.core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(DriverManager.class);
    private static AppiumDriver driver;
    
    private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";

    private DriverManager() {
        // Construtor privado para evitar instanciação
    }

    public static AppiumDriver getDriver() {
        if (driver == null) {
            startDriver();
        }
        return driver;
    }

    public static void startDriver() {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            
            // Informações do dispositivo
            caps.setCapability("deviceName", "26a8c447");
            caps.setCapability("platformName", "Android");
            caps.setCapability("automationName", "UiAutomator2");

            // Informações do aplicativo
            caps.setCapability("appPackage", "com.swaglabsmobileapp");
            caps.setCapability("appActivity", "com.swaglabsmobileapp.MainActivity");

            // Outras configurações
            caps.setCapability("newCommandTimeout", 60);
            caps.setCapability("autoGrantPermissions", true);

            // Inicializa o driver com a nova URL
            URL appiumServerURL = new URL(APPIUM_SERVER_URL);
            driver = new AndroidDriver(appiumServerURL, caps);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

            LOGGER.info("Driver inicializado com sucesso!");
        } catch (MalformedURLException e) {
            LOGGER.error("Erro ao inicializar o driver: {}", e.getMessage());
            throw new RuntimeException("Erro ao inicializar o Appium Driver", e);
        }
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            LOGGER.info("Driver finalizado com sucesso!");
        }
    }
}
