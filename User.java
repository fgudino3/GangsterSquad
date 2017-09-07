package model;

public class User {
	String name;
	int cin;
	String email;
	String username;
	String password;
	String passConfirm;
	
	public User(String username, String email, int cin, String password) {
		this.username = username;
		this.email = email;
		this.cin = cin;
		this.password = password;
	}
	
	public User(String name, int cin, String email, String username, String password, String passConfirm) {
		this.name = name;
		this.cin = cin;
		this.email = email;
		this.username = username;
		this.password = password;
		this.passConfirm = passConfirm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCin() {
		return cin;
	}

	public void setCin(int cin) {
		this.cin = cin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassConfirm() {
		return passConfirm;
	}

	public void setPassConfirm(String passConfirm) {
		this.passConfirm = passConfirm;
	}
	
	
 }
