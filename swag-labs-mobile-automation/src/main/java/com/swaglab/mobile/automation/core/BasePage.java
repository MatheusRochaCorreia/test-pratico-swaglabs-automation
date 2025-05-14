package com.swaglab.mobile.automation.core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BasePage {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);
    protected AppiumDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        LOGGER.info("Clicou no elemento: {}", element);
    }

    protected void sendKeys(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
        LOGGER.info("Digitou '{}' no elemento: {}", text, element);
    }
    
    protected void clearElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        LOGGER.info("Limpou o elemento: {}", element);
    }

    protected boolean isElementDisplayed(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            LOGGER.info("Elemento está visível: {}", element);
            return element.isDisplayed();
        } catch (Exception e) {
            LOGGER.error("Elemento não está visível: {}", element);
            return false;
        }
    }
    
    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            LOGGER.info("Elemento está presente: {}", locator);
            return true;
        } catch (Exception e) {
            LOGGER.error("Elemento não está presente: {}", locator);
            return false;
        }
    }
    
    protected String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        String text = element.getText();
        LOGGER.info("Texto obtido: '{}'", text);
        return text;
    }

    protected WebElement waitForElement(By locator) {
        LOGGER.info("Aguardando elemento: {}", locator);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    protected void waitForElementToDisappear(WebElement element) {
        LOGGER.info("Aguardando elemento desaparecer: {}", element);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public byte[] getScreenshot() {
        LOGGER.info("Capturando screenshot da tela");
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}