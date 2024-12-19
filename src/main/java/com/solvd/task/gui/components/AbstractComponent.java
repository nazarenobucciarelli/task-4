package com.solvd.task.gui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public abstract class AbstractComponent {

    protected WebElement root;

    protected WebDriverWait wait;

    protected  WebDriver driver;

    protected static final Logger logger = LoggerFactory.getLogger(AbstractComponent.class);

    public AbstractComponent(WebElement root, WebDriver driver) {
        this.root = root;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.driver = driver;
        PageFactory.initElements(root, this);
    }

    public WebElement getRoot() {
        return root;
    }

}
