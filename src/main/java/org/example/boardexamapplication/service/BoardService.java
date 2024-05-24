package org.example.boardexamapplication.service;

import lombok.RequiredArgsConstructor;
import org.example.boardexamapplication.domain.Board;
import org.example.boardexamapplication.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<Board> findAllPosts(Pageable pageable) {
        Pageable sortedByDescId= PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id"));
        return boardRepository.findAll(sortedByDescId);
    }

    @Transactional(readOnly = true)
    public Board findBoardById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public Board saveBoard(Board board) {
        if (board.getCreatedAt() == null) {
            board.setCreatedAt(LocalDateTime.now());
        }
        return boardRepository.save(board);
    }
}
