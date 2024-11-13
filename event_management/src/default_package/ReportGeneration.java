package default_package;

import java.util.List;
interface FeedbackSubmission {
 void submitFeedback(Feedback feedback);
}

interface FeedbackAggregation {
 double aggregateFeedback(List<Feedback> feedbackList);
}
interface ReportGeneration {
 void generateReport(Event event);
}

