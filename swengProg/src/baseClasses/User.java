package baseClasses;

public class User {
	private int id;
	private String name;
	private String surname;
	private String email;
	private int userType;
	
	public User(int id, String name, String surname, String email, int userType){
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.userType = userType;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public int getUserType() {
		return userType;
	}
}
