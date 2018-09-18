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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GridPageTestWithFillIns {

    public void checkingShowAllFillInPage(ChromeDriver webDriver, Report report, JavascriptExecutor js, JFXCheckBox showAllFillInPage, TextField inputGridPageURLWithFillIns, Text statusInfo, TextField inputSearch, Properties Homepage){
        final String infoMessage = "Checking GridPage with Fill Ins";
        ChangeCheckBox.adjustStyle(false,"progress",showAllFillInPage);
        Platform.runLater(() -> {
            statusInfo.setText(""+infoMessage+"...");
        });
        try {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(0));
            try {
                webDriver.navigate().to(inputGridPageURLWithFillIns.getText().trim());
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                try{
                    boolean isAvailable = webDriver.findElementByXPath(Homepage.getProperty("page.grid.fillInMore")) != null;
                    List<WebElement> FillInsMore = webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.fillInMore")));
                    List<WebElement> FillInsAll = webDriver.findElements(By.xpath(Homepage.getProperty("page.grid.fillInAll")));
                    ArrayList LinksMore = new ArrayList();
                    ArrayList LinksAll = new ArrayList();

                    for (WebElement FillIns : FillInsMore){
                        LinksMore.add(FillIns.getAttribute("href").trim().toLowerCase());
                    }
                    for (WebElement FillAll : FillInsAll){
                        LinksAll.add(FillAll.getAttribute("href").trim().toLowerCase());
                    }

                    for (int i = 0 ; i < FillInsAll.size(); i++){
                        ((JavascriptExecutor)webDriver).executeScript("window.open()");
                        tabs = new ArrayList<>(webDriver.getWindowHandles());
                        try{
                            webDriver.switchTo().window(tabs.get(1)); //switches to new tab
                            webDriver.get(LinksMore.get(i).toString());
                            final String urlLocationBefore = webDriver.getCurrentUrl();
                            ((JavascriptExecutor)webDriver).executeScript("window.open()");
                            tabs = new ArrayList<>(webDriver.getWindowHandles());
                            webDriver.switchTo().window(tabs.get(2)); //switches to new tab
                            webDriver.get(LinksAll.get(i).toString());

                            if ( urlLocationBefore.contains(webDriver.getCurrentUrl()) ){
                                report.writeToFile("Checking Fill Ins: ", "Tested URL \""+ urlLocationBefore +"\" successfully!");
                            }else {
                                report.writeToFile("Checking Fill Ins: ", "Tested URL \""+ urlLocationBefore +"\" NOT successfully!");
                            }

                            webDriver.switchTo().window(tabs.get(2)).close();
                            webDriver.switchTo().window(tabs.get(1)).close();
                            webDriver.switchTo().window(tabs.get(0));
                        }catch (Exception errorWhileLoop){
                            tabs = new ArrayList<>(webDriver.getWindowHandles());
                            webDriver.switchTo().window(tabs.get(0));
                            webDriver.navigate().to(inputGridPageURLWithFillIns.getText().trim());
                            errorWhileLoop.printStackTrace();
                            break;
                        }

                    }
                    ChangeCheckBox.adjustStyle(true,"complete",showAllFillInPage);
                    report.writeToFile(infoMessage, "Complete!");
                }catch (Exception gridPageIssue){
                    ChangeCheckBox.adjustStyle(true,"nope",showAllFillInPage);
                    boolean isSuccessful = ScreenshotViaWebDriver.printScreen(webDriver, "GridPageErrorPagingWindows.png");
                    if (isSuccessful){
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot successful!");
                    }else {
                        report.writeToFile("GridPage Error Screenshot: ", "Screenshot not successful!");
                    }
                    webDriver.navigate().to(inputSearch.getText().trim());
                    report.writeToFile(infoMessage, "Could find any FillIns-Boxes");
                    gridPageIssue.printStackTrace();
                }
            }catch (Exception noRequestedSiteFound){
                ChangeCheckBox.adjustStyle(true,"nope",showAllFillInPage);
                webDriver.navigate().to(inputSearch.getText().trim());
                report.writeToFile(infoMessage, "Couldn't navigate to requested Site!");
                noRequestedSiteFound.printStackTrace();
            }
        }catch (Exception noBrowserWorking){
            ChangeCheckBox.adjustStyle(true,"nope",showAllFillInPage);
            webDriver.navigate().to(inputSearch.getText().trim());
            report.writeToFile(infoMessage, "unable to check! Browser not responding");
            noBrowserWorking.printStackTrace();
        }

        report.writeToFile("=================================", "");

    }

}
