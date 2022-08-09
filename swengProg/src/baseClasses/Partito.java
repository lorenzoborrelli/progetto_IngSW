package baseClasses;

public class Partito {
	private int id;
	private String name;
	
	public Partito(int id, String nome) {
		this.id = id;
		this.name = nome;
	}

	public boolean equals(Object o) {
		if(o instanceof Partito)
			if(this.id == ((Partito)o).getId() && this.name == ((Partito)o).getName())
				return true;
		return false;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		String s = name;
		return s;
	}
	
}
