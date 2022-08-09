package baseClasses;

public class Candidato {
	private int id;
	private String name;
	private String surname;
	private Partito p;
	
	public Candidato(int id, String name, String surname, Partito p) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.p = p;
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

	public Partito getPartito() {
		return p;
	}
	
	
	public String toString() {
		String s = name + " " + surname + " (" + p.getName() + ")";
		return s;
	}
}
