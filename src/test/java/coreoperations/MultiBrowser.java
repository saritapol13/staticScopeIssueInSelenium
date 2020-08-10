package coreoperations;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.WebDriver;
import java.util.LinkedHashMap;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import static org.junit.jupiter.api.Assertions.fail;

public class MultiBrowser {

    CustomWebDriver customWebDriver = new CustomWebDriver();
    public Logger log = getLogger(this.getClass().getName());
    private static ThreadLocal<Integer> threadLocalActiveBrowserNo = new ThreadLocal<Integer>();
    private static ThreadLocal<LinkedHashMap<Integer, String>> threadLocalBrowsersTypes = new ThreadLocal<LinkedHashMap<Integer, String>>();
    private static ThreadLocal<LinkedHashMap<Integer, WebDriver>> threadLocalBrowsers = new ThreadLocal<LinkedHashMap<Integer, WebDriver>>();

    public static String browserType;
    private static String url;
    private static String gridServerUrl;

    public static String getUrl() {
        return url;
    }

    public static String getGridServerUrl() {
        return gridServerUrl;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setGridServerUrl(String gridServerUrl) {
        this.gridServerUrl = gridServerUrl;
    }

    public String getBrowserType() {
        return browserType;
    }

    public void initThreadLocalsForBrowser() {
        LinkedHashMap<Integer, WebDriver> browsers = new LinkedHashMap<Integer, WebDriver>();
        threadLocalBrowsers.set(browsers);
        LinkedHashMap<Integer, String> browserTypes = new LinkedHashMap<Integer, String>();
        threadLocalBrowsersTypes.set(browserTypes);
        threadLocalActiveBrowserNo.set(1);
    }

    public void releaseThreadLocalsForBrowser() {
        threadLocalBrowsers.remove();
        threadLocalBrowsersTypes.remove();
        threadLocalActiveBrowserNo.remove();
    }

    public static void setTLBrowsers(LinkedHashMap<Integer, WebDriver> arrBrowsers) {
        threadLocalBrowsers.set(arrBrowsers);
    }

    public static LinkedHashMap<Integer, WebDriver> getTLBrowsers() {
        return threadLocalBrowsers.get();
    }

    public static void setTLBrowsersTypes(LinkedHashMap<Integer, String> arrBrowsersTypes) {
        threadLocalBrowsersTypes.set(arrBrowsersTypes);
    }

    public static LinkedHashMap<Integer, String> getTLBrowsersTypes() {
        return threadLocalBrowsersTypes.get();
    }

    public static void setTLActiveBrowserNumber(Integer activeBrowserNo) {
        threadLocalActiveBrowserNo.set(activeBrowserNo);
    }

    public static Integer getTLActiveBrowserNumber() {
        return threadLocalActiveBrowserNo.get();
    }

    public static WebDriver getActiveBrowser(){
        return getTLBrowsers().get(getTLActiveBrowserNumber());
    }

    public void openNewBrowser(int intBrowserNo) {
        try {
            WebDriver browser = null;
            browser = customWebDriver.openNewBrowser(getBrowserType());
            putTLBrowsers(intBrowserNo, browser);
            putTLBrowsersTypes(intBrowserNo, browserType);
            setTLActiveBrowserNumber(intBrowserNo);

        } catch (Exception e) {
            log.info("Error while attempting to open browser:\n" + ExceptionUtils.getMessage(e));
        }
    }

    public void closeBrowsers() {
        int browserID = 1;
        for (WebDriver browser : getTLBrowsers().values()) {
            try {
                browser.quit();
                log.info(String.format("The browser %s for test has been closed.", browser.toString()));
            } catch (Exception e) {
                fail("Exception raised while attempting to close browser:\n" + ExceptionUtils.getMessage(e));
            }
        }
    }

    public static void putTLBrowsers(Integer key, WebDriver value) {
        LinkedHashMap<Integer, WebDriver> arrBrowsers = getTLBrowsers();
        arrBrowsers.put(key, value);
        setTLBrowsers(arrBrowsers);
    }

    public static void putTLBrowsersTypes(Integer key, String value) {
        LinkedHashMap<Integer, String> arrBrowsersTypes = getTLBrowsersTypes();
        arrBrowsersTypes.put(key, value);
        setTLBrowsersTypes(arrBrowsersTypes);
    }
}
