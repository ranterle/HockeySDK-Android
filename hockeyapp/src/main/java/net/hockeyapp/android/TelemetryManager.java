package net.hockeyapp.android;

import android.app.Application;
import android.content.Context;

import com.microsoft.applicationinsights.library.ApplicationInsights;
import com.microsoft.applicationinsights.library.TelemetryClient;


import net.hockeyapp.android.utils.Util;

import java.util.Map;

public class TelemetryManager {

    /**
     * Registers new Feedback manager. Providing {@code appContext} as well as {@code application}
     * will enable auto collection of sessions and page views.
     *
     * @param appContext            the application context of the app
     * @param application           the application app used for the app
     * @param appIdentifier         the HockeyApp app identifier of your app
     */
    public static void register(Context appContext, Application application, String appIdentifier){
        register(appContext, application, appIdentifier, null);
    }

    /**
     * Registers new Feedback manager. Providing {@code appContext} as well as {@code application}
     * will enable auto collection of sessions and page views.
     *
     * @param appContext            the application context of the app
     * @param application           the application app used for the app
     * @param appIdentifier         the HockeyApp app identifier of your app
     * @param serverURL             the server URL for sending telemetry data.
     */
    public static void register(Context appContext, Application application, String appIdentifier, String serverURL){

        // TODO: The approach of exposing configurations is completely different to the one we use in HockeyApp. I don't think that exposing multiple register() methods will do the trick. We could expose wrapped versions of our Config and User objects.
        String instrumentationKey = Util.convertAppIdentifierToIkey(appIdentifier);
        ApplicationInsights.setup(appContext, application, instrumentationKey);

        if(serverURL != null){
            ApplicationInsights.getConfig().setEndpointUrl(serverURL);
        }
        ApplicationInsights.setExceptionTrackingDisabled(true);
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
     * {@code properties} defaults to {@code null}.
     * {@code measurements} defaults to {@code null}.
     *
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
     * @param pageName     the name of the page.
     * @param properties   custom properties associated with the event
     */
    public static void trackPageView(
            String pageName,
            Map<String, String> properties,
            Map<String, Double> measurements) {
        TelemetryClient.getInstance().trackPageView(pageName, properties, measurements);
    }

    /**
     * Sends information about a new Session to Application Insights.
     */
    public static void trackNewSession() {
        TelemetryClient.getInstance().trackNewSession();
    }
}
