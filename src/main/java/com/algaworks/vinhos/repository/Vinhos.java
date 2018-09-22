package com.algaworks.vinhos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.algaworks.vinhos.model.Vinho;

public interface Vinhos extends JpaRepository<Vinho, Long> {
	
	
	List<Vinho> findByNomeContainingIgnoreCaseOrderByNome(String nome);
	 

}
