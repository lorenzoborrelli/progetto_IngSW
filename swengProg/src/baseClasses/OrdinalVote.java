package baseClasses;

import java.util.Vector;

public class OrdinalVote extends Session{
	private Vector<Partito> partiti;
	private Vector<Candidato> candidati;
	
	public OrdinalVote(int id, String name, boolean isActive){
		super(id, name, isActive);
		partiti = new Vector<Partito>();
	}
	
	public String toString(){
		String s = "Votazione ordinale \""+name+"\"";
		return s;
	}

	@Override
	public boolean isReferendum() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
