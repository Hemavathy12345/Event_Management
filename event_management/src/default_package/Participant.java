package default_package;

public class Participant {
    private int participantId;
    private String name;
    private String email;

    public Participant(int participantId, String name, String email) {
        this.participantId = participantId;
        this.setName(name);
        this.setEmail(email);
    }

    public int getParticipantId() {
        return participantId;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getParticipantName() {
		return name;
	}

	public String getParticipantEmail() {
		return email;
	}
}

