package utils;

import org.junit.Assert;

import java.io.InputStream;
import java.net.URL;

public class Resources {

    public static final String EDGE_DRIVER_SERVER_EXE = getResource("MicrosoftWebDriver.exe");
    public static final String CHROME_DRIVER_SERVER_EXE = getResource("chromedriver.exe");
    public static final String CHROME_DRIVER_SERVER = getResource("chromedriver");
    public static final String CHROMIUM_EDGE_DRIVER_SERVER_EXE = getResource("msedgedriver.exe");

    public static String getResource(String resourcePath) {
        URL resource = Resources.class.getClassLoader().getResource(resourcePath);
        Assert.assertNotNull(String.format("Resource '%s' was not found.", resourcePath), resource);
        return Resources.class.getClassLoader().getResource(resourcePath).getPath();
    }

    public static InputStream getResourceAsStream(String resourcePath) {
        return Resources.class.getClassLoader().getResourceAsStream(resourcePath);
    }
}
