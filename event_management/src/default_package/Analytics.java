package default_package;
import java.util.List;
abstract class Analytics implements FeedbackAggregation {
    @Override
    public double aggregateFeedback(List<Feedback> feedbackList) {
        if (feedbackList.isEmpty()) {
            return 0.0;
        }
        double total = 0;
        for (Feedback feedback : feedbackList) {
            total += feedback.getRating();
        }
        return total / feedbackList.size();
    }

	public static double calculateAverageRating(List<Feedback> feedbackList) {
        if (feedbackList == null || feedbackList.isEmpty()) {
            return 0.0;
        }

        int totalRating = 0;
        for (Feedback feedback : feedbackList) {
            totalRating += feedback.getRating();
        }
        return (double) totalRating / feedbackList.size();
    }
}
