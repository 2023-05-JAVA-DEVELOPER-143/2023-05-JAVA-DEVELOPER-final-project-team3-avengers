package com.danaga.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danaga.dto.BoardDto;
import com.danaga.entity.Board;
import com.danaga.entity.LikeConfig;
import com.danaga.service.BoardService;
import com.danaga.service.LikeConfigService;
import com.danaga.service.MemberServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

	@Autowired
	private BoardService bService;
	@Autowired
	private LikeConfigService lcService; 
	
	@GetMapping("/list/{boardGroupId}")
	public String list(@PathVariable Long boardGroupId,Model model) {
		List<BoardDto>boardList=bService.boards(boardGroupId);
		log.info("boardList : {} "+boardList.toString());
		model.addAttribute("boardList",boardList);
		return "board/list";
	}
	@GetMapping("/create/{boardGroupId}")
	public String createForm(@PathVariable Long boardGroupId ,Model model) {
		Long boardGroupId1=boardGroupId;
		model.addAttribute("boardGroupId",boardGroupId1);
		return "board/create_form";
	}
	
	@PostMapping("/create")
	public String createBoard(@PathVariable BoardDto dto,Model model) {
		log.info("dto : {} ",dto);
		List<LikeConfig> configs = lcService.create(dto);
		BoardDto saved = bService.createBoard(dto,configs);
		log.info("saved: {}",saved);
		model.addAttribute("saved",saved);
		return "redirect:/board/show"+saved.getId();
	}
	
	@GetMapping("/{id}/show")
	public String show (@PathVariable Long id,Model model) {
		BoardDto board = bService.boardDetail(id);
		model.addAttribute("board",board);
		return "board/show";
	}
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable Long id,Model model) {
		BoardDto board=bService.boardDetail(id);
		
		model.addAttribute("board",board);
		
		return "board/edit";
	}
	@PostMapping("/{id}/edit")
	public String updated(@PathVariable Long id,BoardDto dto,Model model,RedirectAttributes rttr) {
		BoardDto board= bService.boardDetail(id);
		board = bService.update(dto);
		model.addAttribute("board",board);
		rttr.addFlashAttribute("upd","수정이 완료 되어따!~");
		return "redirect:/board/"+board.getId()+"/show";
	}
	
	@GetMapping("/{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes rttr) {
		//1. 삭제 대상을 가져온다~
		BoardDto target =bService.boardDetail(id);
		
		//2. 대상을 삭제한다~
		if(target!=null) {
			bService.delete(target);
			rttr.addFlashAttribute("msg","삭제가 완료 되어따~!"); 
		}
		//3. 결과페이지로 리다이렉트한다.
		return "redirect:/board/list/"+target.getBoardGroupId();
	}
	
}
