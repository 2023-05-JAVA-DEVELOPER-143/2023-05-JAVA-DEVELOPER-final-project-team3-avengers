package com.danaga.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danaga.dto.BoardDto;
import com.danaga.dto.LikeConfigDto;
import com.danaga.entity.Board;
import com.danaga.entity.LikeConfig;
import com.danaga.service.BoardService;
import com.danaga.service.LikeConfigService;
import com.danaga.service.MemberServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
		List<BoardDto> top10List = bService.popularPost();
		for (BoardDto boardDto : boardList) {
			if(boardDto.getContent().length()>15) {
				String contentTemp=boardDto.getContent().substring(0,15)+"...";
				boardDto.setContent(contentTemp);
			}
		}
		for (BoardDto boardDto : top10List) {
			if(boardDto.getTitle().length()>10) {
				String contentTemp=boardDto.getTitle().substring(0,10)+"...";
				boardDto.setContent(contentTemp);
			}
		}
		model.addAttribute("boardList",boardList);
		model.addAttribute("top10",top10List);
		return "board/board";
	}
	@GetMapping("/create/{boardGroupId}")
	public String createForm(@PathVariable Long boardGroupId ,Model model) {
		Long boardGroupId1=boardGroupId;
		model.addAttribute("boardGroupId",boardGroupId1);
		model.addAttribute("board",new BoardDto());
		return "board/create";
	}
	
	@PostMapping("/create/{boardGroupId}")
	public String createBoard(@PathVariable("boardGroupId")Long boardGruopId, @ModelAttribute("board") BoardDto dto,Model model) {
		log.info("dto : {} ",dto);
		//List<LikeConfigDto> configs = lcService.create(dto.getId(),dto.getMemberId());
		BoardDto saved = bService.createBoard(dto);
		log.info("saved: {}",saved);
		model.addAttribute("saved",saved);
		return "redirect:/board/list/"+saved.getBoardGroupId();
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
			lcService.deleteConfigs(target);
			bService.delete(target);
			rttr.addFlashAttribute("msg","삭제가 완료 되어따~!"); 
		}
		//3. 결과페이지로 리다이렉트한다.
		return "redirect:/board/list/"+target.getBoardGroupId();
	}
	
}
