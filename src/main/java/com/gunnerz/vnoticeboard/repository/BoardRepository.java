package com.gunnerz.vnoticeboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gunnerz.vnoticeboard.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity,Long>{
	
	public BoardEntity findByName(String name);

}
