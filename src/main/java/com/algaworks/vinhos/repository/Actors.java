package com.algaworks.vinhos.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.algaworks.vinhos.model.Actor;


public interface Actors extends JpaRepository<Actor, Short> {
	
	
	Page<Actor> findByFirstNameContainingIgnoreCaseOrderByFirstName(String firstName, Pageable pageable);
	
	//Page<Actor> findByFirstNameContainingIgnoreCaseOrderByFirstName(new PageRequest(0, 10));		 
	
	
	List<Actor> findByFirstNameContainingIgnoreCaseOrderByFirstName(String firstName);
	
	//Page<Actor> findAll( Pageable pageRequest);
	
	List<Actor> findAllByFirstName(String firstName, Pageable pageable);
	


}