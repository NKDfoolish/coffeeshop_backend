package com.coffeeshop.mycoffee.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

@Component
public class BrowserLauncher implements CommandLineRunner {

    private static final Logger logger = Logger.getLogger(BrowserLauncher.class.getName());

    @Value("${base_url}")
    String url;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Attempting to open browser with URL: " + url);
        openBrowser(url);
    }

    private void openBrowser(String url) {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();
        try {
            if (os.contains("win")) {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                runtime.exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux")) {
                String[] browsers = {"xdg-open", "google-chrome", "firefox"};
                boolean success = false;
                for (String browser : browsers) {
                    if (runtime.exec(new String[]{"which", browser}).waitFor() == 0) {
                        runtime.exec(new String[]{browser, url});
                        success = true;
                        break;
                    }
                }
                if (!success) {
                    logger.warning("No suitable browser found.");
                }
            } else {
                logger.warning("Unsupported operating system.");
            }
        } catch (IOException | InterruptedException e) {
            logger.severe("Failed to open browser: " + e.getMessage());
        }
    }
}
