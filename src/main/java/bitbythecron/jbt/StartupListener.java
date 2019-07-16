package bitbythecron.jbt;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

/**
 * Runs at app startup, after DI.
 */
@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        log.info("Starting up...");

        // Prepare JxBrowser
        Engine engine = Engine.newInstance(
                EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                        .licenseKey("1BNDHFSC1FSF94A5BCH9IR3IE9PGPM9JQUWK1PGVU156IS7B5VQJ1ILYI1SVDSMACNPEWL")
                        .build());
        Browser browser = engine.newBrowser();
//        browser.navigation().loadUrl("https://www.nike.com/");
        browser.navigation().loadUrl("http://localhost:9200/");

        JFrame.setDefaultLookAndFeelDecorated(true);

        SwingUtilities.invokeLater(() -> {
            BrowserView view = BrowserView.newInstance(browser);

            JFrame mainWindow = new JFrame("JxBrowser Troubleshooting App");

            mainWindow.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    engine.close();
                }
            });

            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
            mainWindow.setResizable(true);

            mainWindow.add(view);

            mainWindow.pack();

            mainWindow.setVisible(true);

        });

        log.info("App has started");

    }
}
