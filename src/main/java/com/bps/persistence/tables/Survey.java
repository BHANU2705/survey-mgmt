package com.bps.persistence.tables;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Survey implements IBaseEntity {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;
	
	@Column
	private String name;

	@Embedded
	private LifeCycle lifeCycle;
	
	@OneToMany(mappedBy = "survey",
			cascade = CascadeType.ALL, orphanRemoval = true)
	List<Question> questions = new ArrayList<Question>();

	public void addQuestion(Question question) {
		if (questions == null) {
			questions = new ArrayList<Question>();
		}
		questions.add(question);
		question.setSurvey(this);
	}
	
	public void removeQuestion(Question question) {
		questions.remove(question);
		question.setSurvey(null);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	public void setLifeCycle(LifeCycle lifeCycle) {
		this.lifeCycle = lifeCycle;
	}

	public List<Question> getQuestions() {
		return questions;
	}
	
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}
