package coreoperations;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.BrowserOptions;
import utils.Resources;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class CustomWebDriver {

    private static final String BROWSER_CREATION_LOCK = "BrowserCreationLock";
    private static final String gridServerUrl  = System.getenv("gridServerUrl")!=null ? System.getenv("gridServerUrl") : null;
    public Logger log = Logger.getLogger(this.getClass().getName());

    public WebDriver openNewBrowser(String browserName) throws MalformedURLException {
        return browserFor(browserName);
    }

    public WebDriver browserFor(String browserName) throws MalformedURLException {
        BrowserOptions browserOptions = new BrowserOptions();
        WebDriver webDriver = null;
        if (gridServerUrl == null || gridServerUrl.isEmpty()) {
            try {
                setEdgeDriverProperty(browserName);
                setChromeDriverProperty();
                webDriver = getBrowserByBrowserType(browserName, browserOptions);
            }catch(WebDriverException e){
                log.info("Couldn't start new browser session due to:\n" + ExceptionUtils.getRootCauseMessage(e));
            }
            return webDriver;
        } else {
            synchronized (BROWSER_CREATION_LOCK) {
                log.info("Remote server URL: " + gridServerUrl);
                if (browserName.equalsIgnoreCase("chrome")) {
                    return new RemoteWebDriver(new URL(gridServerUrl), browserOptions.getChromeOptions());
                }/* else if (browserName.equalsIgnoreCase("chromiumEdge")) {
                    return new RemoteWebDriver(new URL(gridServerUrl), browserOptions.getEdgeChromiumOptions());
                }*/
            }
        }
        throw new RuntimeException("Browser name not valid \"" + browserName + "\"");
    }

    private WebDriver getBrowserByBrowserType(String browserName, BrowserOptions browserOptions) {
        WebDriver browser;

        switch (browserName.toLowerCase()) {
            case "chrome":
                browser = new ChromeDriver(browserOptions.getChromeOptions());
                break;
            /*case "chromiumedge":
                browser = new EdgeDriver(browserOptions.getEdgeChromiumOptions());
                break;*/
            default:
                throw new RuntimeException("Browser name not valid \"" + browserName + "\"");
        }
        return browser;
    }

    private void setChromeDriverProperty(){
        if (System.getProperty("os.name").startsWith("Windows"))
            System.setProperty("webdriver.chrome.driver", new File(Resources.CHROME_DRIVER_SERVER_EXE).getAbsolutePath());
        else
            System.setProperty("webdriver.chrome.driver", new File(Resources.CHROME_DRIVER_SERVER).getAbsolutePath());
        System.setProperty("webdriver.chrome.logfile", "chromedriver.log");
        System.setProperty("webdriver.chrome.verboseLogging", "true");
    }

    private void setEdgeDriverProperty(String browserType){
        if(browserType.equalsIgnoreCase("chromiumEdge"))
            System.setProperty("webdriver.edge.driver", new File(Resources.CHROMIUM_EDGE_DRIVER_SERVER_EXE).getAbsolutePath());
        else
            System.setProperty("webdriver.edge.driver", new File(Resources.EDGE_DRIVER_SERVER_EXE).getAbsolutePath());
    }
}
