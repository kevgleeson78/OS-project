import java.io.Serializable;
//Fitness record class to create a new instance of the user fitness records that are entered to the system.
//also used when deleting a record
public class FitnessRecord implements Serializable {

	private String mode;
	private String duration;

	public FitnessRecord() {
		mode = new String();
		duration = new String();
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
}
