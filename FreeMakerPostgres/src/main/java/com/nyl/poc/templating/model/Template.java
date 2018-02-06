package com.nyl.poc.templating.model;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Table(name = "emailtemplates")

public class Template implements Serializable {
	private static final long serialVersionUID = -3009157732242241606L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	/*public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	} */
	public String getTemplate_content() {
		return template_content;
	}
	public void setTemplate_content(String template_content) {
		this.template_content = template_content;
	}
	
	//@Column(name = "template_name")
	//private String template_name = null;
	
	@Column(name = "template_content")
	private String template_content = null;


}
