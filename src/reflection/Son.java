package reflection;

public class Son extends Father{

	public String sonName;
	protected Integer sonAge;
	private String sonSchool;
	public int getSonAge() {
		return sonAge;
	}
	public void setSonAge(int sonAge) {
		this.sonAge = sonAge;
	}
	public String getSonSchool() {
		return sonSchool;
	}
	public void setSonSchool(String sonSchool) {
		this.sonSchool = sonSchool;
	}
}
