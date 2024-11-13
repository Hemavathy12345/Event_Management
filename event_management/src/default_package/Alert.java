package default_package;
public class Alert {
    public static void sendAlert(String message) {
        System.out.println("ALERT: " + message);
    }
    public static void sendEventReminder(Event event) {
        System.out.println("Reminder: The event '" + event.getName() + "' is happening on " + event.getDate() + " at " + event.getLocation());
    }
}

