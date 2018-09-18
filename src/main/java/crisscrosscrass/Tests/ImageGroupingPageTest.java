package crisscrosscrass.Tests;

import com.jfoenix.controls.JFXCheckBox;
import crisscrosscrass.Tasks.ChangeCheckBox;
import crisscrosscrass.Tasks.Report;
import crisscrosscrass.Tasks.ScreenshotViaWebDriver;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ImageGroupingPageTest {

    public void checkingImageGroupingClickOut(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox ImageGroupingClickOut, TextField inputLucenePage, Text statusInfo, TextField inputGridPageURL, Properties Homepage){
        final String infoMessage = "Checking Image Grouping Click Out";
        ChangeCheckBox.adjustStyle(false,"progress",ImageGroupingClickOut);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            boolean isSuccessful = false;
            try {
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0){
                        report.writeToFile("provided GridPageURL "+inputGridPageURL.getText(), " included Windows! Adjusted GridPage to make test happen!");
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                    }else {
                        System.out.println("No Window Element!");
                    }
                    wait.until(ExpectedConditions.elementToBeClickable((By.xpath(Homepage.getProperty("page.items.grouping")))));
                    List<WebElement> ImageGroupingElements = webDriver.findElementsByXPath(Homepage.getProperty("page.items.grouping"));
                    ImageGroupingElements.get(0).click();

                    try{
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("imagegrouping.page.toshop.main"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("imagegrouping.page.toshop.variant"))));
                        webDriver.findElementByXPath(Homepage.getProperty("imagegrouping.page.toshop.main")).click();
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,100)");
                        }
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,-100)");
                        }
                        tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(0));
                        List<WebElement> ProductsVariant = webDriver.findElementsByXPath(Homepage.getProperty("imagegrouping.page.toshop.variant"));
                        ProductsVariant.get(0).click();
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,100)");
                        }
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(100);
                            js.executeScript("window.scrollBy(0,-100)");
                        }
                        tabs = new ArrayList<>(webDriver.getWindowHandles());
                        webDriver.switchTo().window(tabs.get(2));
                        final String urlComparison = webDriver.getCurrentUrl();
                        webDriver.switchTo().window(tabs.get(1));
                        if (webDriver.getCurrentUrl().contains(urlComparison)){
                            report.writeToFile(infoMessage, "Successful!");
                            ChangeCheckBox.adjustStyle(true,"complete",ImageGroupingClickOut);
                        }else {
                            report.writeToFile(infoMessage, "Not successful!");
                            ChangeCheckBox.adjustStyle(true,"nope",ImageGroupingClickOut);
                        }
                        webDriver.switchTo().window(tabs.get(2)).close();
                        webDriver.switchTo().window(tabs.get(1)).close();
                        webDriver.switchTo().window(tabs.get(0));
                        webDriver.navigate().to(inputGridPageURL.getText().trim());

                    }catch (Exception noProduktVariants){
                        ChangeCheckBox.adjustStyle(true,"nope",ImageGroupingClickOut);
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "DetailPageImageGrouping.png");
                        if (isSuccessful){
                            report.writeToFile("Checking Image Grouping Click Out: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("Checking Image Grouping Click Out: ", "Screenshot not successful!");
                        }
                        webDriver.navigate().to(inputGridPageURL.getText().trim());
                        report.writeToFile(infoMessage, "Couldn't detect Product Variants in Image Grouping");
                        noProduktVariants.printStackTrace();
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",ImageGroupingClickOut);
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "DetailPageImageGrouping.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Image Grouping Click Out: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("Checking Image Grouping Click Out: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputGridPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect Image Grouping Icon for Detail Grouping Page");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",ImageGroupingClickOut);
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",ImageGroupingClickOut);
            webDriver.navigate().to(inputGridPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }

    public void checkingDetailPageOfOffer(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox DetailPageOfOffer, TextField inputLucenePage, Text statusInfo, TextField inputGridPageURL, Properties Homepage){
        final String infoMessage = "Checking Image Grouping Detail Page of Offer";
        ChangeCheckBox.adjustStyle(false,"progress",DetailPageOfOffer);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            boolean isSuccessful = false;
            try {
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    if(webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.windows"))).size() > 0){
                        report.writeToFile("provided GridPageURL "+inputGridPageURL.getText(), " included Windows! Adjusted GridPage to make test happen!");
                        webDriver.findElementByXPath(Homepage.getProperty("page.grid.windows.continue")).click();
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Homepage.getProperty("page.grid.windows.continue"))));
                    }else {
                        System.out.println("No Window Element!");
                    }
                    wait.until(ExpectedConditions.elementToBeClickable((By.xpath(Homepage.getProperty("page.items.grouping")))));
                    List<WebElement> ImageGroupingElements = webDriver.findElementsByXPath(Homepage.getProperty("page.items.grouping"));
                    ImageGroupingElements.get(0).click();

                    try{
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("imagegrouping.page.toshop.main"))));
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Homepage.getProperty("imagegrouping.page.toshop.variant"))));
                        List<WebElement> productVariantsInfoIcons = webDriver.findElementsByXPath(Homepage.getProperty("imagegrouping.page.infoicon.variant"));
                        productVariantsInfoIcons.get(0).click();
                        if (webDriver.getCurrentUrl().contains("detail")){
                            report.writeToFile(infoMessage, "Successful!");
                            ChangeCheckBox.adjustStyle(true,"complete",DetailPageOfOffer);
                        }else {
                            report.writeToFile(infoMessage, "Not successful! Unable to detect Pattern in URL");
                            ChangeCheckBox.adjustStyle(true,"nope",DetailPageOfOffer);
                        }
                        webDriver.navigate().to(inputGridPageURL.getText().trim());
                    }catch (Exception noProduktVariants){
                        ChangeCheckBox.adjustStyle(true,"nope",DetailPageOfOffer);
                        isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "DetailPageImageGroupingOffer.png");
                        if (isSuccessful){
                            report.writeToFile("Checking Image Grouping Detail Page of Offer: ", "Screenshot successful!");
                        }else {
                            report.writeToFile("Checking Image Grouping Detail Page of Offer: ", "Screenshot not successful!");
                        }
                        webDriver.navigate().to(inputGridPageURL.getText().trim());
                        report.writeToFile(infoMessage, "Couldn't detect Product Variants in Image Grouping");
                        noProduktVariants.printStackTrace();
                    }
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",DetailPageOfOffer);
                    isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "DetailPageImageGroupingOffer.png");
                    if (isSuccessful){
                        report.writeToFile("Checking Image Grouping Detail Page of Offer: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("Checking Image Grouping Detail Page of Offer: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputGridPageURL.getText().trim());
                    report.writeToFile(infoMessage, "Couldn't detect Image Grouping Icon for Detail Grouping Page");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",DetailPageOfOffer);
                webDriver.navigate().to(inputGridPageURL.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",DetailPageOfOffer);
            webDriver.navigate().to(inputGridPageURL.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }
        report.writeToFile("=================================", "");
    }
}
