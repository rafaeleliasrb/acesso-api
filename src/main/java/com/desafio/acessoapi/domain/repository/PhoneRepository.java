package com.desafio.acessoapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafio.acessoapi.domain.model.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

}
