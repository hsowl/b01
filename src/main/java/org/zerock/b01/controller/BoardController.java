package org.zerock.b01.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.BoardListReplyCountDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.service.BoardService;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
//        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        PageResponseDTO<BoardListReplyCountDTO> responseDTO = boardService.listWithReplyCount(pageRequestDTO);
        log.info("responseDTO : {}", responseDTO);
        model.addAttribute("responseDTO", responseDTO);
    }

    @GetMapping("/register")
    public void registerGet() {

    }

    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes rttr) {
        log.info("board Post register....");
        if(bindingResult.hasErrors()){
            log.info("삐빅삐빅 삐빅 에러발생 에러 발생");
            rttr.addFlashAttribute("errors",bindingResult.getAllErrors());
            return "redirect:/board/register";
        }

        log.info("boardDTO : {}", boardDTO);
        Long bno = boardService.register(boardDTO);
        rttr.addFlashAttribute("result",bno);
        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pagerequestDTO, Model model){
        log.info("bno ========================> {}",bno);

        BoardDTO boardDTO = boardService.readOne(bno);

        model.addAttribute("dto",boardDTO);

//        model.addAttribute("pageRequestDTO",pagerequestDTO);
    }

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO, @Valid BoardDTO boardDTO,
                         BindingResult bindingResult, RedirectAttributes rttr) {

        if (bindingResult.hasErrors()) {
            log.info("errors");
            String link = pageRequestDTO.getLink();
            rttr.addFlashAttribute("errors", bindingResult.getAllErrors());
            rttr.addAttribute("bno", boardDTO.getBno());
            return "redirect:/board/modify?" + link;
        }
        boardService.modify(boardDTO);
        rttr.addFlashAttribute("result", "modified");
        rttr.addAttribute("bno", boardDTO.getBno());
        return "redirect:/board/list";
    }
    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes rttr){
        boardService.remove(bno);
        rttr.addFlashAttribute("result", "removed");
        return "redirect:/board/list";
    }






}
