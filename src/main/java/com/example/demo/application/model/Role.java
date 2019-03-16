package com.example.demo.application.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Data
public class Role {

	public enum ROLE_TYPE {
		ADMIN, REVIEWER, USER, EDITOR
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "role")
	private String role;
//	private User user;


	public Role() {
	}

	public Role(String role) {
		this.role = role;
	}


//	@ManyToOne(cascade= CascadeType.MERGE)
//	@JoinTable(name="users_roles",
//			joinColumns = {@JoinColumn(name="users_id", referencedColumnName="id")},
//			inverseJoinColumns = {@JoinColumn(name="roles_id", referencedColumnName="id")}
//	)
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
