package default_package;

public class Speaker {
    private int speakerId;
    private String name;
    private String expertise;

    public Speaker(String name) {
        this.setName(name);
        this.setExpertise(""); 
    }

	public int getSpeakerId() {
		return speakerId;
	}

	public void setSpeakerId(int speakerId) {
		this.speakerId = speakerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}
}

