package com.danaga.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danaga.entity.Board;
import com.danaga.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService service;
	
	@GetMapping("list/{boardGroupId}")
	public String list(@PathVariable Long boardGroupId,Model model) {
		List<Board>boardList=service.findWhichBoards(boardGroupId);
		model.addAttribute("boardList",boardList);
		return "list/"+boardGroupId;
	}
}
