package com.gunnerz.vnoticeboard.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gunnerz.vnoticeboard.dto.RegistrationForm;
import com.gunnerz.vnoticeboard.entity.BoardEntity;
import com.gunnerz.vnoticeboard.repository.BoardRepository;

@Controller
public class BoardController {

	private static final Logger LOGGER =LoggerFactory
			.getLogger(BoardController.class);
	
	@Autowired
	private BoardRepository boardRepository;
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String registerBoard(@Valid RegistrationForm registrationFrom, BindingResult result,WebRequest request, RedirectAttributes redirectAttributes){
		
		String boardName=request.getParameter("boardName");
		String password=request.getParameter("password");
		
		System.out.println(boardName);
		System.out.println(password);
		
		if(result.hasErrors()){
			System.out.println(result.getAllErrors());
			redirectAttributes.addAttribute("error", "Password should be more than 3 and less 100 characters");
			return "redirect:/" + boardName;
		}
		
		BoardEntity board=new BoardEntity();
		board.setName(boardName);
		board.setPassword(password);
		boardRepository.save(board);
		
		return "redirect:/"+boardName;
	}
	
	@RequestMapping(value="/{boardName}",method=RequestMethod.GET)
	public String showBoard(@PathVariable("boardName")String boardName,Model model){
		
		LOGGER.debug("Rendering board page:"+boardName);
		
		BoardEntity board=boardRepository.findByName(boardName);
		
		if(board==null){
			LOGGER.debug("Board not found:"+boardName);
			model.addAttribute("boardName", boardName);
			return "newboard";
		}
		
		model.addAttribute("boardName", boardName);
		model.addAttribute("boardContent", board.getContent());
		return "board";
		
	}
	
	@RequestMapping(value="/{boardName}/edit",method=RequestMethod.GET)
	public String editBoard(@PathVariable("boardName") String boardName,Model model){
		
		LOGGER.debug("Rendering board edit page:"+boardName);
		
		BoardEntity board=boardRepository.findByName(boardName);
		
		if(board==null){
			LOGGER.debug("Board not found:"+boardName);
			return "home";
		}
		model.addAttribute("content", board.getContent());
		System.out.println("from edit");
		return "editboard";
		
		
	}
	
	@RequestMapping(value="/{boardName}/edit",method=RequestMethod.POST)
	public String doEditBoard(@PathVariable("boardName") String boardName,WebRequest request,RedirectAttributes redirectAttributes, Model model){
		BoardEntity board=boardRepository.findByName(boardName);
		
		if (board==null){
			LOGGER.debug("Board not found:"+boardName);
			return "home";
		}
		
		if (board.getPassword().equals(request.getParameter("password"))){
			board.setContent(request.getParameter("content"));
			boardRepository.save(board);
			return "redirect:/"+request.getParameter("boardName");
		}
		redirectAttributes.addAttribute("error", "Password miss match");
		
		return "redirect:/"+request.getParameter("boardName")+"/edit";
		
	}
	
	
}
