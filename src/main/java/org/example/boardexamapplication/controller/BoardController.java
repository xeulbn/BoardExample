package org.example.boardexamapplication.controller;

import lombok.RequiredArgsConstructor;
import org.example.boardexamapplication.domain.Board;
import org.example.boardexamapplication.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping()
    public String boards(Model model, @RequestParam(defaultValue = "1")int page,
                         @RequestParam(defaultValue = "10")int size) {
        Pageable pageable= PageRequest.of(page-1,size);

        Page<Board> boards= boardService.findAllPosts(pageable);
        model.addAttribute("boards", boards);
        model.addAttribute("currentPage", page);
        return "boards/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("board", new Board());
        return "boards/form";
    }

    @PostMapping("/add")
    public String addBoard(@ModelAttribute Board board,
                           RedirectAttributes redirectAttributes) {
        boardService.saveBoard(board);
        redirectAttributes.addFlashAttribute("message", "New board added");
        return "redirect:/boards";
    }

    @GetMapping("/view")
    public String detailBoard(@RequestParam Long id, Model model) {
        Board board = boardService.findBoardById(id);
        model.addAttribute("board", board);
        return "boards/detail";
    }

    @GetMapping("/deleteform")
    public String deleteForm(@RequestParam Long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("board", boardService.findBoardById(id));
        return "boards/deleteform";
    }

    @PostMapping("/delete")
    public String deleteBoard(@RequestParam Long id, @RequestParam String password,
                              RedirectAttributes redirectAttributes) {
        Board board = boardService.findBoardById(id);
        if(board.getPassword().equals(password)) {
            boardService.delete(id);
            redirectAttributes.addFlashAttribute("message","성공적으로 삭제되었습니다!");
            return "redirect:/boards";
        }
        redirectAttributes.addFlashAttribute("error","삭제가 실패하였습니다");
        return "redirect:/boards";

    }

    @GetMapping("/updateform")
    public String updateForm(@RequestParam Long id, Model model) {
        model.addAttribute("board", boardService.findBoardById(id));
        return "boards/updateform";
    }

    @PostMapping("/update")
    public String editBoard(@ModelAttribute Board board
    , RedirectAttributes redirectAttributes){
        boardService.saveBoard(board);
        redirectAttributes.addFlashAttribute("message","게시글이 성공적으로 수정되었습니다.");
        return "redirect:/boards";
    }

}
