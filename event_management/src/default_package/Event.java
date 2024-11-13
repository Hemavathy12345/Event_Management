package default_package;
import java.util.ArrayList;
import java.util.List;
public class Event {
    private int eventId;
    private String name;
    private String date;
    private String location;
    private List<Session> sessions;
    private List<Participant> participants;
    public Event(int eventId, String name, String date, String location) {
        this.setEventId(eventId);
        this.setName(name);
        this.setDate(date);
        this.setLocation(location);
        this.sessions = new ArrayList<>();
        this.participants = new ArrayList<>();
    }
    public void addSession(Session session) {
        sessions.add(session);
    }
    public void addParticipant(Participant participant) {
        participants.add(participant);
    }
    public List<Session> getSessions() {
        return sessions;
    }
    public List<Participant> getParticipants() {
        return participants;
    }
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getEventName() {
	    return name;
	}
	public String getEventDate() {
		return date;
	}
	public String getEventLocation() {
		return location;
	}
}
