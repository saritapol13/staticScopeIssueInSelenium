package product1;

import coreoperations.MultiBrowser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.TestSetup;
import website.HomePage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class RemoteBrowserTests extends TestSetup {

    @Test
    @DisplayName("The about tab verification")
    public void about_tab_is_on_home_page() {
        HomePage homePage = new HomePage();
        try {
            MultiBrowser.getActiveBrowser().get("https://optymyze.com/about/");
            homePage.waitAfterHomePageIsDisplayed();
            WebElement href = MultiBrowser.getActiveBrowser().findElement(By.xpath("//a[@href='/about/']"));
            assertTrue((href.isDisplayed()));
        } catch (Exception e) {
           fail("The test failed due to: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("The developers tab verification")
    public void developers_tab_is_on_home_page() {
        HomePage homePage = new HomePage();
        try {
            MultiBrowser.getActiveBrowser().get("https://optymyze.com/developers/");
            homePage.waitAfterHomePageIsDisplayed();
            WebElement href = MultiBrowser.getActiveBrowser().findElement(By.xpath("//a[@href='/developers/']"));
            assertTrue((href.isDisplayed()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
