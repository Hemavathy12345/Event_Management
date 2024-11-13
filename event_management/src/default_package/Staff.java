package default_package;

public class Staff extends Resource {
    private String name;
    private String skillset;

    public Staff(int resourceId, String name, String skillset) {
        super(resourceId);
        this.name = name;
        this.skillset = skillset;
    }

    public String getName() {
        return name;
    }

	public String getSkillset() {
		return skillset;
	}

	public void setSkillset(String skillset) {
		this.skillset = skillset;
	}
}
