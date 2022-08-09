package baseClasses;

public class CategoricalVote extends Session{
	public CategoricalVote(int id, String name, boolean isActive){
		super(id, name, isActive);
	}
	
	public String toString(){
		String s = "Votazione categorica \""+name+"\"";
		return s;
		
	}

	@Override
	public boolean isReferendum() {
		// TODO Auto-generated method stub
		return false;
	}
}
