package default_package;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Report {

    // Method to generate a report and display it on the console
    public void generateReport(Event event) {
        System.out.println("Event Report:");
        System.out.println("Event ID: " + event.getEventId());
        System.out.println("Event Name: " + event.getName());
        System.out.println("Event Date: " + event.getDate());
        System.out.println("Event Location: " + event.getLocation());

        System.out.println("\nSessions:");
        for (Session session : event.getSessions()) {
            System.out.println("Session Title: " + session.getTitle());
            System.out.println("Start Time: " + session.getStartTime());
            System.out.println("End Time: " + session.getEndTime());
            System.out.println("Average Rating: " + Analytics.calculateAverageRating(session.getFeedbackList()));
            System.out.println("-----");
        }

        System.out.println("\nParticipants:");
        for (Participant participant : event.getParticipants()) {
            System.out.println("Participant ID: " + participant.getParticipantId());
            System.out.println("Name: " + participant.getName());
            System.out.println("Email: " + participant.getEmail());
            System.out.println("-----");
        }
    }

    // Method to export feedback analytics to a file
    public void exportFeedbackAnalytics(Event event) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Feedback_Analytics_Report.txt"))) {
            writer.println("Feedback Analytics Report for Event: " + event.getName());
            writer.println("Event ID: " + event.getEventId());
            writer.println("Event Date: " + event.getDate());
            writer.println("Event Location: " + event.getLocation());
            writer.println();

            writer.println("Sessions:");
            for (Session session : event.getSessions()) {
                writer.println("Session Title: " + session.getTitle());
                writer.println("Average Rating: " + Analytics.calculateAverageRating(session.getFeedbackList()));
                writer.println("Feedback Details:");
                for (Feedback feedback : session.getFeedbackList()) {
                    writer.println("Participant ID: " + feedback.getParticipantId());
                    writer.println("Rating: " + feedback.getRating());
                    writer.println("Comments: " + feedback.getComments());
                    writer.println("-----");
                }
                writer.println();
            }
            System.out.println("Feedback analytics report exported successfully to Feedback_Analytics_Report.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while exporting the feedback analytics report: " + e.getMessage());
        }
    }
}
