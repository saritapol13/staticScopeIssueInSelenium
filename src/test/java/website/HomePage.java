package website;

import com.google.common.base.Function;
import coreoperations.MultiBrowser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomePage {

    private static final Logger log = LogManager.getLogger();

    public void waitAfterHomePageIsDisplayed() {
            FluentWait<WebDriver> wait = new FluentWait<>(MultiBrowser.getActiveBrowser());
            wait.withTimeout(Duration.ofSeconds(30))
                    .ignoring(NullPointerException.class)
                    .ignoring(InvalidSelectorException.class)
                    .ignoring(JavascriptException.class)
                    .until(new Function<WebDriver, Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driver) {
                            List<WebElement> elements = validateHomePageContent();
                            return elements.size() > 0;
                        }
                    });
            MultiBrowser.getActiveBrowser().manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
    }

    public List<WebElement> validateHomePageContent(){
        return MultiBrowser.getActiveBrowser().findElements(By.xpath("//div[@id='be-content']"));
    }
}
