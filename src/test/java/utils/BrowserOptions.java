package utils;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BrowserOptions {

    public static final String DOWNLOADS_PATH_LINUX = "/mnt/twistInputFiles/BrowserDownloads/";

    /*public EdgeOptions getEdgeChromiumOptions() {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.setCapability("ensureCleanSession", true);
        edgeOptions.setCapability("useChromium", true);
        edgeOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

        edgeOptions.setExperimentalOption("useAutomationExtension", false);
        edgeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

        // Enable flash for all sites for edge 80
        edgeOptions.addArguments("--disable-features=EnableEphemeralFlashPermission");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.plugins", 1);
        prefs.put("profile.content_settings.plugin_whitelist.adobe-flash-player", 1);
        prefs.put("profile.content_settings.exceptions.plugins.*,*.per_resource.adobe-flash-player", 1);
        prefs.put("PluginsAllowedForUrls", "*");
        prefs.put("exit_type", "None");
        prefs.put("exited_cleanly", true);
        edgeOptions.setExperimentalOption("prefs", prefs);
        edgeOptions.setExperimentalOption("prefs", chromeProfilePreferences());
        edgeOptions.addArguments("--start-maximized");
        edgeOptions.addArguments("disable-extensions", "disable-infobars", "disable-breakpad");
        if (System.getenv("remoteServerUrl") != null) {
            edgeOptions.addArguments("--start-fullscreen");
        }
        edgeOptions.addArguments("--disable-web-security");
        edgeOptions.addArguments("--ignore-certificate-errors");
        edgeOptions.addArguments("--disable-popup-blocking");

        return edgeOptions;
    }*/

    private Map<String, Object> chromeProfilePreferences() {
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("profile.default_content_setting_values.automatic_downloads", 1);
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.directory_upgrade", true);

        prefs.put(
                "download.default_directory", DOWNLOADS_PATH_LINUX);


        prefs.put("plugins.plugins_list", Arrays.asList(chromePlugins()));
        prefs.put("credentials_enable_service", false);

        prefs.put("profile.default_content_setting_values.plugins", 1);
        prefs.put("profile.content_settings.plugin_whitelist.adobe-flash-player", 1);
        prefs.put("profile.content_settings.exceptions.plugins.*,*.per_resource.adobe-flash-player", 1);
        prefs.put("protocol_handler.excluded_schemes.msteams", false);

        return prefs;
    }

    private HashMap<String, Object> chromePlugins() {
        HashMap<String, Object> plugin = new HashMap<String, Object>();
        plugin.put("enabled", true);
        plugin.put("name", "Chrome PDF Viewer");

        return plugin;
    }

    public ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
      //  chromeOptions.setExperimentalOption("useAutomationExtension", false);
       // chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

        chromeOptions.setExperimentalOption("prefs", chromeProfilePreferences());
        chromeOptions.addArguments("--start-maximized");
        if (System.getenv("remoteServerUrl") != null) {
            chromeOptions.addArguments("--start-fullscreen");
        }
        chromeOptions.addArguments("--disable-web-security");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--disable-popup-blocking");

        return chromeOptions;
    }

}
