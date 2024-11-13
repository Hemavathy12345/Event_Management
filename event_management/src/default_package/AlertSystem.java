package default_package;

public class AlertSystem {
    private static final int CRITICAL_RATING_THRESHOLD = 2; 
    public void checkForCriticalFeedback(Feedback feedback) {
        if (isCritical(feedback)) {
            sendAlert(feedback);
        }
    }
    private boolean isCritical(Feedback feedback) {
        return feedback.getRating() <= CRITICAL_RATING_THRESHOLD 
                || feedback.getComments().toLowerCase().contains("urgent");
    }
    private void sendAlert(Feedback feedback) {
        System.out.println("ALERT: Critical feedback received!");
        System.out.println("Participant ID: " + feedback.getParticipantId());
        System.out.println("Rating: " + feedback.getRating());
        System.out.println("Comments: " + feedback.getComments());
        System.out.println("Please review this feedback promptly.");
    }
}
