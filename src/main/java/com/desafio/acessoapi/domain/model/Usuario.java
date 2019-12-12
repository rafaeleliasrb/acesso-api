package com.desafio.acessoapi.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
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
	
	@Cascade(value = {CascadeType.ALL})
	@OneToMany(mappedBy = "usuario")
	private List<Phone> phones;

	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime created;
	
	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime modified;
	
	@Column(nullable = false)
	private LocalDateTime lastLogin;
	
	private String token;
	
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "usuario_permissao", 
//		joinColumns = @JoinColumn(name = "usuario_id"), 
//		inverseJoinColumns = @JoinColumn(name = "permissao_id"))
//	private List<Permissao> permissoes;
}
