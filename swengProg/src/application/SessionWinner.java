package application;

import baseClasses.Candidato;

public class SessionWinner {
	private Candidato c;
	private boolean refYes;
	private boolean quorumNonRaggiunto;
	private int numVoti;
	

	private boolean isRef;
	
	public SessionWinner(boolean isRef, boolean refYes, Candidato c, boolean quorumNonRaggiunto, int numVoti) {
		this.isRef = isRef;
		this.refYes = refYes;
		this.c = c;
		this.quorumNonRaggiunto = quorumNonRaggiunto;
		this.numVoti = numVoti;
	}
	
	
	public Candidato getCandidato() {
		return c;
	}

	public boolean getResult() {
		return refYes;
	}

	public boolean isRef() {
		return isRef;
	}
	
	public boolean getQuorumNonRaggiunto() {
		return quorumNonRaggiunto;
	}
	
	public int getNumVoti() {
		return numVoti;
	}
	
	
}
