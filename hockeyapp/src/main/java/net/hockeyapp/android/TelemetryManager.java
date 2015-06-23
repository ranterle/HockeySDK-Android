package net.hockeyapp.android;

import android.app.Application;
import android.content.Context;

import com.microsoft.applicationinsights.contracts.User;
import com.microsoft.applicationinsights.library.ApplicationInsights;
import com.microsoft.applicationinsights.library.TelemetryClient;

import net.hockeyapp.android.objects.TelemetryManagerConfig;
import net.hockeyapp.android.utils.Util;

import java.util.Map;

public class TelemetryManager {

    /**
     * Registers new Feedback manager. Providing {@code appContext} as well as {@code application}
     * will enable auto collection of sessions and page views.
     *
     * @param appContext    the application context of the app
     * @param application   the application app used for the app
     * @param appIdentifier the HockeyApp app identifier of your app
     */
    public static void registerAndStart(Context appContext, Application application, String appIdentifier) {
        register(appContext, application, appIdentifier, null);
        ApplicationInsights.start();
    }

    /**
     * Registers new Telemetry manager. Providing {@code appContext} as well as {@code application}
     * will enable auto collection of sessions and page views and start to collect & send telemetry.
     * If you want to customize your usage of telemetry, use {@link }, do you custom settings for
     * telemetry, e.g. {@link TelemetryManager#disableAutoSessions()} and call {@link TelemetryManager#start()}
     * to start collecting telemetry data.
     *
     * @param appContext    the application context of the app
     * @param application   the application app used for the app
     * @param appIdentifier the HockeyApp app identifier of your app
     * @param serverURL     the server URL for sending telemetry data.
     */
    public static void registerAndStart(Context appContext, Application application, String appIdentifier, String serverURL) {
        register(appContext, application, appIdentifier, serverURL); //TODO move server URL to own setter?
        ApplicationInsights.start();
    }

    /**
     * Registers new Telemetry manager. Providing {@code appContext} as well as {@code application}
     * will enable auto collection of sessions and page views but won't start the telemetry feature.
     * Use this method if you want to customize TelemetryManager to initialize it.
     * Do your custom settings before calling {@link TelemetryManager#start()}.
     * If you don't need to customize this feature, also
     *
     * @param appContext    the application context of the app
     * @param application   the application app used for the app
     * @param appIdentifier the HockeyApp app identifier of your app
     * @param serverURL     the server URL for sending telemetry data.
     * @see TelemetryManager#registerAndStart(Context, Application, String, String)
     */
    public static void register(Context appContext, Application application, String appIdentifier, String serverURL) {
        String instrumentationKey = Util.convertAppIdentifierToIkey(appIdentifier);
        ApplicationInsights.setup(appContext, application, instrumentationKey);

        if (serverURL != null) {
            ApplicationInsights.getConfig().setEndpointUrl(serverURL);
        }
        ApplicationInsights.setExceptionTrackingDisabled(true);
    }

    /**
     * Will start TelemetryManager after it has been set up using
     *
     * @see TelemetryManager#register(Context, Application, String, String)
     * Is called by
     * @see TelemetryManager#registerAndStart(Context, Application, String)
     * and
     * @see TelemetryManager#registerAndStart(Context, Application, String, String)
     */
    public static void start() {
        ApplicationInsights.start();
    }

    /**
     * {@code properties} defaults to {@code null}.
     * {@code measurements} defaults to {@code null}.
     *
     * @see TelemetryManager#trackEvent(String, java.util.Map, java.util.Map)
     */
    public static void trackEvent(String eventName) {
        TelemetryClient.getInstance().trackEvent(eventName);
    }

    /**
     * {@code measurements} defaults to {@code null}.
     *
     * @see TelemetryManager#trackEvent(String, java.util.Map, java.util.Map)
     */
    public static void trackEvent(String eventName, Map<String, String> properties) {
        TelemetryClient.getInstance().trackEvent(eventName, properties);
    }

    /**
     * Sends information about an event to Application Insights.
     *
     * @param eventName    the name of the event
     * @param properties   custom properties associated with the event
     * @param measurements custom measurements associated with the event
     */
    public static void trackEvent(
          String eventName,
          Map<String, String> properties,
          Map<String, Double> measurements) {
        TelemetryClient.getInstance().trackEvent(eventName, properties, measurements);
    }

    /**
     * {@code measurements} defaults to {@code null}.
     *
     * @see TelemetryManager#trackTrace(String, Map)
     */
    public static void trackTrace(String message) {
        TelemetryClient.getInstance().trackTrace(message);
    }

    /**
     * Sends tracing information to Application Insights.
     *
     * @param message    the message associated with this trace.
     * @param properties custom properties associated with the event
     */
    public static void trackTrace(String message, Map<String, String> properties) {
        TelemetryClient.getInstance().trackTrace(message, properties);
    }

    /**
     * Sends information about an aggregated metric to Application Insights. Note: all data sent via
     * this method will be aggregated. To enqueue non-aggregated data use
     * {@link TelemetryManager#trackEvent(String, Map, Map)} with measurements.
     *
     * @param name  the name of the metric
     * @param value the value of the metric
     */
    public static void trackMetric(String name, double value) {
        TelemetryClient.getInstance().trackMetric(name, value);
    }

    /**
     * @see TelemetryManager#trackHandledException(Throwable, Map)
     */
    public static void trackHandledException(Throwable handledException) {
        TelemetryClient.getInstance().trackHandledException(handledException);
    }

    /**
     * Sends information about an handledException to Application Insights.
     *
     * @param handledException the handledException to track.
     * @param properties       custom properties associated with the event
     */
    public static void trackHandledException(Throwable handledException, Map<String, String> properties) {
        TelemetryClient.getInstance().trackHandledException(handledException, properties);
    }

    /**
     * {@code properties} defaults to {@code null}.
     * {@code measurements} defaults to {@code null}.
     *
     * @see TelemetryManager#trackPageView(String, Map, Map)
     */
    public static void trackPageView(String pageName) {
        TelemetryClient.getInstance().trackPageView(pageName);
    }

    /**
     * {@code measurements} defaults to {@code null}.
     *
     * @see TelemetryManager#trackPageView(String, Map, Map)
     */
    public static void trackPageView(String pageName, Map<String, String> properties) {
        TelemetryClient.getInstance().trackPageView(pageName, properties);
    }

    /**
     * Sends information about a page view to Application Insights.
     *
     * @param pageName   the name of the page.
     * @param properties custom properties associated with the event
     */
    public static void trackPageView(
          String pageName,
          Map<String, String> properties,
          Map<String, Double> measurements) {
        TelemetryClient.getInstance().trackPageView(pageName, properties, measurements);
    }

    /**
     * Count the current session as a new session
     */
    public static void trackNewSession() {
        TelemetryClient.getInstance().trackNewSession();
    }

    public void sendPendingData() {
        ApplicationInsights.sendPendingData();
    }

    //TODO: upgrade to 1.0-beta.6 when available to use this method
    public static void setUserConfig(User user) {
        //ApplicationInsights.setCustomUserContext(user);
        ApplicationInsights.setUserId(user.getId());
    }

    //TODO not very nice to have a class called ApplicationInsightsConfig.
    //I replaced this with a (not necessary) subclass to not expose anything that reads "ApplicationInsights"
    public static void setTelemetryConfig(TelemetryManagerConfig config) {
        ApplicationInsights.INSTANCE.setConfig(config);
    }

    /**
     * Enables all auto-collection features
     */
    public static void enableAutoCollection() {
        ApplicationInsights.enableAutoCollection();
    }

    /**
     * Disables all auto-collection features
     */
    public static void disableAutoCollection() {
        ApplicationInsights.disableAutoCollection();
    }

    /**
     * Enables auto session management
     */
    public static void enableAutoSessions() {
        ApplicationInsights.enableAutoSessionManagement();
    }

    /**
     * Disables auto session management
     */
    public static void disableAutoSessions() {
        ApplicationInsights.disableAutoSessionManagement();
    }

    /**
     * Enables auto pageviews
     */
    public static void enableAutoPageViews() {
        ApplicationInsights.enableAutoPageViewTracking();
    }

    /**
     * Disables auto pageviews
     */
    public static void disableAutoPageViews() {
        ApplicationInsights.disableAutoPageViewTracking();
    }

    /**
     * Enable / disable auto collection of telemetry data at startup.
     *
     * @param disabled if set to true, the auto collection feature will be disabled at app start
     *                 To enable/disable auto collection features at runtime, use
     *                 {@link TelemetryManager#disableAutoCollection()} or the more specific
     *                 {@link TelemetryManager#disableAutoSessions()} ()},
     *                 {@link TelemetryManager#disableAutoPageViews()} ()}
     */
    public static void setAutoCollectionDisabledAtStartup(Boolean disabled) {
        ApplicationInsights.setAutoCollectionDisabledAtStartup(disabled);
    }

    /**
     * Force Application Insights to create a new session with a custom sessionID.
     *
     * @param sessionId a custom session ID that we want to use to create a new session
     */
    public static void renewSession(String sessionId) {
        ApplicationInsights.renewSession(sessionId);
    }


}
