package default_package;

public class Feedback {
    private int feedbackId;
    private int rating;
    private String comments;
    private int participantId;
    private int sessionId;
    public Feedback(int feedbackId, int rating, String comments, int participantId, int sessionId) {
        this.feedbackId = feedbackId;
        this.rating = rating;
        this.comments = comments;
        this.participantId = participantId;
        this.sessionId = sessionId;
    }
    public int getFeedbackId() {
        return feedbackId;
    }
    public int getRating() {
        return rating;
    }

    public String getComments() {
        return comments;
    }

    public int getParticipantId() {
        return participantId;
    }

    public int getSessionId() {
        return sessionId;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
