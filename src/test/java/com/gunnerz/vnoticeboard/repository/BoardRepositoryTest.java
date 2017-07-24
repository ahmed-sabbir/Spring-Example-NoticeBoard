package com.gunnerz.vnoticeboard.repository;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gunnerz.vnoticeboard.entity.BoardEntity;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BoardRepositoryTest {
	
	@Autowired
	BoardRepository boardRepository;
	
	@Test
	public void findByNameTest() {
		
		BoardEntity board1 = new BoardEntity(1L, "tinytots", "testpass1", "Sample board text1");
		BoardEntity board2 = new BoardEntity(2L, "stjoseph", "testpass2", "Sample board text2");
		BoardEntity board3 = new BoardEntity(3L, "rifelspublic", "testpass3", "Sample board text3");
		BoardEntity board4 = new BoardEntity(4L, "govtboys", "testpass4", "Sample board text4");
		BoardEntity board5 = new BoardEntity(5L, "shaheen", "testpass5", "Sample board text5");
		

		boardRepository.save(board1);
		boardRepository.save(board2);
		boardRepository.save(board3);
		boardRepository.save(board4);
		boardRepository.save(board5);
		
		System.out.println(boardRepository.findByName("stjoseph"));

		
	}
}
