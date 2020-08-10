package utils;

import coreoperations.MultiBrowser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class TestSetup {

    private MultiBrowser multiBrowser;

    @BeforeEach
    public void setup() {
        multiBrowser = createMultiBrowser();
        initThreadLocals();
        multiBrowser.openNewBrowser(1);
    }

    @AfterEach
    public void tearDown(){
        multiBrowser.closeBrowsers();
        releaseThreadLocals();
    }

    private void initThreadLocals() {
        multiBrowser.initThreadLocalsForBrowser();
    }

    private void releaseThreadLocals() {
        multiBrowser.releaseThreadLocalsForBrowser();
    }

    private MultiBrowser createMultiBrowser() {
        MultiBrowser multiBrowser;
        multiBrowser = new MultiBrowser();
        if(System.getenv("browserType") == null)
            multiBrowser.setBrowserType("chrome");
        else
            multiBrowser.setBrowserType(System.getenv("browserType"));

        if(System.getenv("url") == null)
            multiBrowser.setUrl("https://optymyze.com/");
        else
            multiBrowser.setUrl(System.getenv("url"));

        if(System.getenv("gridServerUrl") == null)
            multiBrowser.setGridServerUrl("");
        else
            multiBrowser.setGridServerUrl(System.getenv("gridServerUrl"));

        return multiBrowser;
    }
}