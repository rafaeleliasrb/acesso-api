package com.desafio.acessoapi.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name = "usuario_id")
	private List<Phone> phones = new ArrayList<>();

	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime created;
	
	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime modified;
	
	@Column(nullable = false)
	private LocalDateTime lastLogin;
	
	private String token;
}
