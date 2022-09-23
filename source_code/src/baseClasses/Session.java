package baseClasses;

public abstract class Session {
	private int id;
	protected String name;
	protected boolean isActive = false;
	protected boolean alreadyVoted = false;
	protected boolean quorum = false;

	



	public Session(int id, String name, boolean isActive) {
		this.id = id;
		this.name = name;
		this.isActive = isActive;
	}
	
	
	
	public boolean isQuorum() {
		return quorum;
	}



	public void setQuorum(boolean quorum) {
		this.quorum = quorum;
	}



	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean getIsActive() {
		return isActive;
	}
	
	
	public boolean getAlreadyVoted() {
		return alreadyVoted;
	}

	public void setAlreadyVoted(boolean alreadyVoted) {
		this.alreadyVoted = alreadyVoted;
	}
	
	public abstract boolean isReferendum();
	
	
	public abstract String toString();
	
}
