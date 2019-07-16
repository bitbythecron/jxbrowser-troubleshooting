# jxbrowser-troubleshooting

## Building & Running
### Build
Run:

```
./gradlew clean build
```

This puts a self-contained, executable JAR under the `build/libs` directory.

### Running locally
Once built, then run:

```
java -Dspring.config=. -Djava.awt.headless=false -jar build/libs/jxbrowser-troubleshooting.jar
```

And the app will run and launch a Swing window pointed to the URL specified in the `StartupListener` class.

## Current Issues
### 1. Exception at startup
Although the app (and JxBrowser) starts up _seemingly_ fine, in the logs an exception is actually thrown at startup:

```
Exception in thread "AWT-EventQueue-0" java.lang.IllegalArgumentException
	at com.teamdev.jxbrowser.internal.util.Preconditions.checkArgument(Preconditions.java:32)
	at com.teamdev.jxbrowser.browser.internal.RenderWidget.boundsInScreen(RenderWidget.java:117)
	at com.teamdev.jxbrowser.view.swing.internal.OffScreenRenderWidget.updateBoundsInScreen(OffScreenRenderWidget.java:313)
	at com.teamdev.jxbrowser.view.swing.internal.OffScreenRenderWidget.access$2100(OffScreenRenderWidget.java:71)
	at com.teamdev.jxbrowser.view.swing.internal.OffScreenRenderWidget$WindowMovedListener.componentMoved(OffScreenRenderWidget.java:625)
	at java.awt.AWTEventMulticaster.componentMoved(AWTEventMulticaster.java:170)
	at java.awt.AWTEventMulticaster.componentMoved(AWTEventMulticaster.java:169)
	at java.awt.Component.processComponentEvent(Component.java:6362)
	at java.awt.Component.processEvent(Component.java:6313)
	at java.awt.Container.processEvent(Container.java:2237)
	at java.awt.Window.processEvent(Window.java:2025)
	
	<rest of stacktrace omitted for brevity>
```

### 2. Cannot load localhost URL
When the URL specified in the `StartupListener` class is a non-`localhost` address, the URL is fetched and loaded without issue. You
can see this in action by running the app (see directions above) with the URL set to, say, `https://www.nike.com/`. When you do this,
when the app starts up, a JxBrowser window with the Nike homepage loads beautifully!

**However**, if you first run a local web server off any port (8080, whatever), and point JxBrowser to load that `localhost` URL, you
will get a JxBrowser window displaying 404 "Not Found" error message, along with a `Reload` button. If you then click Reload, the
`localhost` URL loads perfectly fine the second time around. But this is a non-starter for me: I need it to load the `localhost` URL
the very first time!

#### 3. Exact steps to reproduce this one
1. Change the URL in `StartupListener` to point to this homepage like so:

```
browser.navigation().loadUrl("http://localhost:9200/");
```

2. Build and run the app. You will see JxBrowser load something that looks like this:

![not found](https://raw.githubusercontent.com/bitbythecron/jxbrowser-troubleshooting/master/screenshots/1.png)

This is the problem! I should be seeing the Spring login page!

3. Now click the `Reload` button and you will see:

![login](https://raw.githubusercontent.com/bitbythecron/jxbrowser-troubleshooting/master/screenshots/2.png)

### JxBrowser hangs on close
If you start the app and try to close the JxBrowser window on a mac via `Command+Q`, the process will hang and the pid needs
to be killed on the terminal. Conversely, if the pid is killed directly from the terminal (Control + C) it terminates without
hanging.