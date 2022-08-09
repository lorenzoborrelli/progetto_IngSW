package baseClasses;

public class CategoricalPrefVote extends Session {

	public CategoricalPrefVote(int id, String name, boolean isActive){
		super(id, name, isActive);
	}

	@Override
	public String toString(){
		String s = "Votazione categorica pref \""+name+"\"";
		return s;
	}

	@Override
	public boolean isReferendum() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
