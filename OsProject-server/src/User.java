import java.io.Serializable;

public class User implements Serializable {

	

	private String name;
	private String address;
	private String ppsNum;
	private String age;
	private String weight;
	private String height;

	public User() {
		name = new String();
		address = new String();
		ppsNum = new String();
		age = new String();
		weight = new String();
		height = new String();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public String getPpsNum() {
		return ppsNum;
	}

	public void setPpsNum(String ppsNum) {
		this.ppsNum = ppsNum;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

}
