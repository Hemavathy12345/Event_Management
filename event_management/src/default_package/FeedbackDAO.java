package default_package;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {

    public void addFeedback(Feedback feedback) {
        String sql = "INSERT INTO Feedback (feedback_id, rating, comments, participant_id, session_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, feedback.getFeedbackId());
            stmt.setInt(2, feedback.getRating());
            stmt.setString(3, feedback.getComments());
            stmt.setInt(4, feedback.getParticipantId());
            stmt.setInt(5, feedback.getSessionId());

            stmt.executeUpdate();
            System.out.println("Feedback added to the database.");

        } catch (SQLException e) {
            System.out.println("Error adding feedback: " + e.getMessage());
        }
    }

    public List<Feedback> getFeedbackBySession(int sessionId) {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT * FROM Feedback WHERE session_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sessionId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int feedbackId = rs.getInt("feedback_id");
                int rating = rs.getInt("rating");
                String comments = rs.getString("comments");
                int participantId = rs.getInt("participant_id");

                feedbackList.add(new Feedback(feedbackId, rating, comments, participantId, sessionId));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving feedback: " + e.getMessage());
        }

        return feedbackList;
    }

    public double calculateAverageRating(int sessionId) {
        String sql = "SELECT AVG(rating) AS average_rating FROM Feedback WHERE session_id = ?";
        double averageRating = 0.0;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sessionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                averageRating = rs.getDouble("average_rating");
            }

        } catch (SQLException e) {
            System.out.println("Error calculating average rating: " + e.getMessage());
        }

        return averageRating;
    }
}
