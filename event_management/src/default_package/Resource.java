package default_package;

public abstract class Resource {
    protected int resourceId;
    protected boolean isAvailable;

    public Resource(int resourceId) {
        this.resourceId = resourceId;
        this.isAvailable = true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailability(boolean availability) {
        isAvailable = availability;
    }
}
