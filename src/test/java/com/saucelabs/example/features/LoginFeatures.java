package com.saucelabs.example.features;

import com.saucelabs.example.pages.InventoryPage;
import com.saucelabs.example.pages.LoginPage;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class LoginFeatures
{
    private final static String userName = System.getenv("SAUCE_USERNAME");
    private final static String accessKey = System.getenv("SAUCE_ACCESS_KEY");

    // Run on a real Android device
    private final static String platformName = "Android";
    private final static String platformVersion = "10";
    private final static String deviceName = "Google.*";

    // Run on a real iOS device
//    private final static String platformName = "iOS";
//    private final static String platformVersion = "12.1.4";
//    private final static String deviceName = "iPhone.*";

    // Run on an Android Emulator
//    private final static String platformName = "Android";
//    private final static String platformVersion = "11";
//    private final static String deviceName = "Android GoogleAPI Emulator";

    // Run on an iOS Simulator
//    private final static String platformName = "iOS";
//    private final static String platformVersion = "13.4";
//    private final static String deviceName = "iPhone XS Simulator";

    @Test
    public void verifyValidUsersCanSignIn()
    throws MalformedURLException
    {
        URL url = new URL("https://ondemand.us-west-1.saucelabs.com/wd/hub");

        // Set the W3C standard capabilities...
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("platformName", platformName);
        caps.setCapability("platformVersion", platformVersion);
        caps.setCapability("browserName", "chrome");
        caps.setCapability("name", "Verify Valid Users Can Sign In");

        // Set the Sauce-specific capabilities...
        MutableCapabilities sauceOpts = new MutableCapabilities();
        sauceOpts.setCapability("userName", userName);
        sauceOpts.setCapability("accessKey", accessKey);
        sauceOpts.setCapability("appiumVersion", "1.18.1");
        sauceOpts.setCapability("deviceName", deviceName);

        // Merge them together.
        caps.setCapability("sauce:options", sauceOpts);

        AppiumDriver driver = new AppiumDriver(url, caps);

        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);

        loginPage.navigateTo(LoginPage.PAGE_URL);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");

        loginPage.clickLogin();
        inventoryPage.waitForPageLoad();

        ((JavascriptExecutor) driver).executeScript("sauce:job-result=passed");
        driver.quit();
    }
}
