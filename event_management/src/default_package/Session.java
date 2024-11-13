package default_package;

import java.util.ArrayList;
import java.util.List;

class Session implements FeedbackSubmission {
    private int sessionId;
    private String title;
    private String startTime;
    private String endTime;
    private String speaker;  // Add this field to store the event ID
    private List<Feedback> feedbackList = new ArrayList<>();
	private Speaker speaker2;

    // Updated constructor to accept eventId as a parameter
    public Session(int sessionId, String title, String startTime, String endTime, Speaker speaker2) {
        this.sessionId = sessionId;
        this.title = title;
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setSpeaker(speaker2);
      // Initialize eventId
    }

    private void setSpeaker(Speaker speaker2) {
    	this.setSpeaker2(speaker2);
		
	}

	public int getSessionId() {
        return sessionId;
    }

    public String getTitle() {
        return title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

  

    // Implementing submitFeedback from FeedbackSubmission interface
    @Override
    public void submitFeedback(Feedback feedback) {
        feedbackList.add(feedback);
        System.out.println("Feedback submitted for session: " + title);
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

	public Speaker getSpeaker2() {
		return speaker2;
	}

	public void setSpeaker2(Speaker speaker2) {
		this.speaker2 = speaker2;
	}
}
