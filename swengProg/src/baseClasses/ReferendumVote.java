package baseClasses;

public class ReferendumVote extends Session{
	public ReferendumVote(int id, String name, boolean isActive){
		super(id, name, isActive);
	}
	
	public String toString(){
		String s = "Referendum \""+name+"\"";
		return s;
	}

	@Override
	public boolean isReferendum() {
		// TODO Auto-generated method stub
		return true;
	}
}
