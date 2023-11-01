package com.danaga.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danaga.dto.BoardDto;
import com.danaga.dto.CommentDto;
import com.danaga.service.BoardService;
import com.danaga.service.CommentsService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardController {

	@Autowired
	private BoardService bService;
	@Autowired
	private CommentsService cService;
	
	
	@GetMapping("/board{boardGroupId}")
	public String list(@PathVariable Long boardGroupId,Model model) {
		List<BoardDto>boardList=bService.boards(boardGroupId);
		List<BoardDto> top10List = bService.popularPost();
		String name = bService.getBoardGroupName(boardGroupId);
		model.addAttribute("boardGroupId",boardGroupId);
		model.addAttribute("boardGroupName",name);
		model.addAttribute("boardList",boardList);
		model.addAttribute("top10",top10List);
		return "board/board_list";
	}
	@GetMapping("/board_create{boardGroupId}")
	public String createForm(@PathVariable Long boardGroupId ,Model model) {
		model.addAttribute("boardGroupId",boardGroupId);
		model.addAttribute("board",new BoardDto());
		return "board/create_board";
	}
	
	@PostMapping("/board_create{boardGroupId}")
	public String createBoard(@PathVariable("boardGroupId")Long boardGruopId, @ModelAttribute("board") BoardDto dto,Model model) {
		log.info("dto : {} ",dto);
		
		BoardDto saved = bService.createBoard(dto);
		log.info("saved: {}",saved);
		
		model.addAttribute("saved",saved);
		model.addAttribute("msg","새로운 글이 생성 되었습니다.");
		return "redirect:/show"+saved.getId();
	}
	
	@GetMapping("/show{id}")
	public String show (@PathVariable Long id,Model model) {
		BoardDto board = bService.boardDetail(id);
		List<CommentDto> comments= cService.comments(id);
		model.addAttribute("comments",comments);
		model.addAttribute("board",board);
		return "board/board_detail";
	}
	@GetMapping("/board_edit{id}")
	public String edit(@PathVariable Long id,Model model) {
		BoardDto board=bService.boardDetail(id);
		
		model.addAttribute("board",board);
		
		return "board/edit";
	}
	@PostMapping("/board_edit{id}")
	public String updated(@PathVariable Long id,BoardDto dto,Model model,RedirectAttributes rttr) {
		BoardDto board= bService.boardDetail(id);
		board = bService.update(dto);
		model.addAttribute("board",board);
		rttr.addFlashAttribute("upd","수정이 완료 되었습니다.");
		return "redirect:/board/"+board.getId()+"/show";
	}
	
	@GetMapping("/board_delete{id}")
	public String delete(@PathVariable Long id, RedirectAttributes rttr) {
		//1. 삭제 대상을 가져온다~
		BoardDto target =bService.boardDetail(id);
		
		//2. 대상을 삭제한다~
		if(target!=null) {
			bService.delete(target);
			rttr.addFlashAttribute("msg","삭제가 완료 되었습니다."); 
		}
		//3. 결과페이지로 리다이렉트한다.
		return "redirect:/board/list/"+target.getBoardGroupId();
	}
		
}
