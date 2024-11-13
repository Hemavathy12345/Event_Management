package default_package;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Main {
    private static final int THREAD_POOL_SIZE = 5; 
    private static Connection connection;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, Event> events = new HashMap<>();
        Report report = new Report();
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        AlertSystem alertSystem = new AlertSystem();  

        // Initialize database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/event_management1", "root", "Mahindra");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Create a thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        int choice;
        do {
            System.out.println("\nEvent Management System");
            System.out.println("1. Create Event");
            System.out.println("2. Add Session to Event");
            System.out.println("3. Add Participant");
            System.out.println("4. Submit Feedback");
            System.out.println("5. View Feedback for a Session");
            System.out.println("6. Calculate Average Rating for a Session");
            System.out.println("7. Send Event Reminder");
            System.out.println("8. Generate and Export Event Report");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1:
                    System.out.println("Enter Event ID:");
                    int eventId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter Event Name:");
                    String eventName = scanner.nextLine();
                    System.out.println("Enter Event Date (yyyy-mm-dd):");
                    String eventDate = scanner.nextLine();
                    System.out.println("Enter Event Location:");
                    String eventLocation = scanner.nextLine();
                    Event event = new Event(eventId, eventName, eventDate, eventLocation);
                    events.put(eventId, event);
                    addEventToDatabase(event);
                    System.out.println("Event created successfully.");
                    break;
                case 2:
                    System.out.println("Enter Event ID:");
                    eventId = scanner.nextInt();
                    scanner.nextLine();
                    event = events.get(eventId);
                    if (event != null) {
                        System.out.println("Enter Session ID:");
                        int sessionId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter Session Title:");
                        String sessionTitle = scanner.nextLine();
                        System.out.println("Enter Session Start Time (HH:mm:ss):");
                        String startTime = scanner.nextLine();
                        System.out.println("Enter Session End Time (HH:mm:ss):");
                        String endTime = scanner.nextLine();
                        System.out.println("Enter Speaker Name:");
                        String speakerName = scanner.nextLine();
                        Speaker speaker = addSpeakerToDatabase(speakerName);
                        Session session = new Session(sessionId, sessionTitle, startTime, endTime, speaker);
                        event.addSession(session);
                        addSessionToDatabase(session);
                        System.out.println("Session added to event successfully.");
                    } else {
                        System.out.println("Event not found.");
                    }
                    break;
                case 3:
                    System.out.println("Enter Event ID:");
                    eventId = scanner.nextInt();
                    scanner.nextLine();
                    event = events.get(eventId);
                    if (event != null) {
                        System.out.println("Enter Participant ID:");
                        int participantId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter Participant Name:");
                        String participantName = scanner.nextLine();
                        System.out.println("Enter Participant Email:");
                        String participantEmail = scanner.nextLine();
                        Participant participant = new Participant(participantId, participantName, participantEmail);
                        event.addParticipant(participant);
                        addParticipantToDatabase(participant);
                        System.out.println("Participant added successfully.");
                    } else {
                        System.out.println("Event not found.");
                    }
                    break;
                case 4:
                    System.out.println("Enter Session ID:");
                    int sessionIdForFeedback = scanner.nextInt();
                    System.out.println("Enter Feedback ID:");
                    int feedbackId = scanner.nextInt();
                    System.out.println("Enter Rating (1-5):");
                    int rating = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter Comments:");
                    String comments = scanner.nextLine();
                    System.out.println("Enter Participant ID:");
                    int participantId = scanner.nextInt();
                    Feedback feedback = new Feedback(feedbackId, rating, comments, participantId, sessionIdForFeedback);
                    Runnable feedbackTask = () -> {
                        feedbackDAO.addFeedback(feedback);
                        alertSystem.checkForCriticalFeedback(feedback); 
                        logFeedbackSubmission(feedback);
                        System.out.println("Feedback submitted successfully.");
                    };
                    executorService.submit(feedbackTask);
                    break;

                case 5:
                    System.out.println("Enter Session ID to view feedback:");
                    sessionIdForFeedback = scanner.nextInt();
                    List<Feedback> feedbackList = feedbackDAO.getFeedbackBySession(sessionIdForFeedback);
                    System.out.println("Feedback for Session ID " + sessionIdForFeedback + ":");
                    for (Feedback fb : feedbackList) {
                        System.out.println("Participant ID: " + fb.getParticipantId());
                        System.out.println("Rating: " + fb.getRating());
                        System.out.println("Comments: " + fb.getComments());
                        System.out.println("-----");
                    }
                    break;
                case 6:
                    System.out.println("Enter Session ID:");
                    sessionIdForFeedback = scanner.nextInt();
                    double avgRating = feedbackDAO.calculateAverageRating(sessionIdForFeedback);
                    System.out.println("Average Rating for Session ID " + sessionIdForFeedback + ": " + avgRating);
                    break;
                case 7:
                    System.out.println("Enter Event ID:");
                    eventId = scanner.nextInt();
                    event = events.get(eventId);
                    if (event != null) {
                        Alert.sendEventReminder(event);
                    } else {
                        System.out.println("Event not found.");
                    }
                    break;
                case 8:
                    System.out.println("Enter Event ID to generate report:");
                    eventId = scanner.nextInt();
                    scanner.nextLine();
                    event = events.get(eventId);
                    if (event != null) {
                        report.generateReport(event);  
                        report.exportFeedbackAnalytics(event); 
                    } else {
                        System.out.println("Event not found.");
                    }
                    break;

                case 0:
                    // Exit
                    System.out.println("Exiting the system.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
        executorService.shutdown();
        scanner.close();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void addEventToDatabase(Event event) {
        String query = "INSERT INTO Event (event_id, event_name, event_date, event_location) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, event.getEventId());
            statement.setString(2, event.getEventName());
            statement.setDate(3, Date.valueOf(event.getEventDate()));
            statement.setString(4, event.getEventLocation());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static Speaker addSpeakerToDatabase(String speakerName) {
        Speaker speaker = new Speaker(speakerName); 
        String query = "INSERT INTO Speaker (speaker_name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, speaker.getName());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    speaker.setSpeakerId(generatedKeys.getInt(1));  
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return speaker;

    }

   
    private static void addSessionToDatabase(Session session) {
        String startTime = session.getStartTime();
        if (!startTime.contains(":")) {
            startTime += ":00";  
        }
        String endTime = session.getEndTime();
        if (!endTime.contains(":")) {
            endTime += ":00";  
        }
        try {
            String query = "INSERT INTO Session (session_id, session_title, start_time, end_time, speaker) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, session.getSessionId());
            statement.setString(2, session.getTitle());
            statement.setTime(3, Time.valueOf(startTime));
            statement.setTime(4, Time.valueOf(endTime));
            statement.setString(5, session.getSpeaker());
            statement.executeUpdate();
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    private static void addParticipantToDatabase(Participant participant) {
        String query = "INSERT INTO Participant (participant_id, participant_name, participant_email) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, participant.getParticipantId());
            statement.setString(2, participant.getParticipantName());
            statement.setString(3, participant.getParticipantEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void logFeedbackSubmission(Feedback feedback) {
        String query = "INSERT INTO FeedbackLog (feedback_id, participant_id, session_id, action) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, feedback.getFeedbackId());
            statement.setInt(2, feedback.getParticipantId());
            statement.setInt(3, feedback.getSessionId());
            statement.setString(4, "Submitted");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
