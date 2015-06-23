package net.hockeyapp.android;

import android.app.Application;
import android.content.Context;

import com.microsoft.applicationinsights.contracts.User;
import com.microsoft.applicationinsights.library.ApplicationInsights;
import com.microsoft.applicationinsights.library.TelemetryClient;

import net.hockeyapp.android.objects.TelemetryManagerConfig;
import net.hockeyapp.android.utils.Util;

import java.util.EnumSet;
import java.util.Map;

public class TelemetryManager {

    /*
     *  Enum which represents the different auto collection features
     */
    public static enum AutoMode {
        SESSIONS,
        PAGE_VIEWS
    }

    /**
     * Registers new Feedback manager. Providing {@code appContext} as well as {@code application}
     * will enable auto collection of sessions and page views.
     *
     * @param appContext    the application context of the app
     * @param application   the application app used for the app
     * @param appIdentifier the HockeyApp app identifier of your app
     * @param modes         an enum set that contains the enabled auto collection features
     */
    public static void registerAndExecute(Context appContext, Application application, String appIdentifier, EnumSet<AutoMode> modes) {
        register(appContext, application, appIdentifier, modes);
        execute();
    }

    /**
     * Registers new Telemetry manager. Providing {@code appContext} as well as {@code application}
     * will enable auto collection of sessions and page views but won't start the telemetry feature.
     * Use this method if you want to customize TelemetryManager to initialize it.
     * Do your custom settings before calling {@link TelemetryManager#execute()}.
     * If you don't need to customize this feature, also
     *
     * @param appContext    the application context of the app
     * @param application   the application app used for the app
     * @param appIdentifier the HockeyApp app identifier of your app
     * @param modes         an enum set that contains the enabled auto collection features
     * @see TelemetryManager#registerAndExecute(Context, Application, String, java.util.EnumSet)
     */
    public static void register(Context appContext, Application application, String appIdentifier, EnumSet<AutoMode> modes) {
        String instrumentationKey = Util.convertAppIdentifierToIkey(appIdentifier);
        ApplicationInsights.setup(appContext, application, instrumentationKey);
        ApplicationInsights.setExceptionTrackingDisabled(true);
        setAutoCollectionModes(modes);
    }

    /**
     * Will start TelemetryManager after it has been set up using
     *
     * @see TelemetryManager#register(Context, Application, String, java.util.EnumSet)
     * Is called by
     * @see TelemetryManager#registerAndExecute(Context, Application, String, java.util.EnumSet)
     */
    public static void execute() {
        ApplicationInsights.start();
    }

    /*
     * Configures the auto collection features based on a given enum set.
     *
     * @param modes an enum set containing enabled features
     */
    public static void setAutoCollectionModes(EnumSet<AutoMode> modes){

        if(modes == null){
            modes = EnumSet.noneOf(AutoMode.class);
        }

        if(modes.contains(AutoMode.SESSIONS)){
           ApplicationInsights.enableAutoSessionManagement();
       }else{
           ApplicationInsights.disableAutoSessionManagement();
       }

       if(modes.contains(AutoMode.PAGE_VIEWS)){
           ApplicationInsights.enableAutoPageViewTracking();
       }else{
            ApplicationInsights.disableAutoPageViewTracking();
       }
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

    /**
     * Sends out pending telemetry data items. Normally they are send out in a bundle after a
     * certain time range or because a certain number of items has been reached.
     * @see net.hockeyapp.android.objects.TelemetryManagerConfig
     */
    public void sendPendingData() {
        ApplicationInsights.sendPendingData();
    }

    //TODO: upgrade to 1.0-beta.6 when available to use this method
    public static void setUserConfig(User user) {
        //ApplicationInsights.setCustomUserContext(user);
        ApplicationInsights.setUserId(user.getId());
    }

    public static void setTelemetryManagerConfig(TelemetryManagerConfig config) {
        ApplicationInsights.INSTANCE.setConfig(config);
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
