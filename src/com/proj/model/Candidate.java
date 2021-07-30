package com.proj.model;

public class Candidate {
	// - - > DECLARATIONS
	private String code, name, position, party;
	
	public Candidate(String code, String name, String position, String party) {
		this.code = code;
		this.name = name;
		this.position = position;
		this.party = party;
	}

	// - - > GETTER 
	public String getName() {
		return name;
	}

	public String getPosition() {
		return position;
	}

	public String getParty() {
		return party;
	}
	
	public String getCode() {
		return code;
	}
}
