package com.deepak.beans;

import java.util.List;

//import java.awt.List;

public class Subjects {
	private List<String> subjects;

	public List<String> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}

	@Override
	public String toString() {
		return subjects.toString();
	}
}
