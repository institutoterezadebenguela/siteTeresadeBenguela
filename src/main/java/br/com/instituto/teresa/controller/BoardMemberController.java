package br.com.instituto.teresa.controller;

import br.com.instituto.teresa.dto.BoardMemberRequestDTO;
import br.com.instituto.teresa.dto.BoardMemberResponseDTO;
import br.com.instituto.teresa.service.BoardMemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardMemberController {

    private final BoardMemberService boardMemberService;

    public BoardMemberController(BoardMemberService boardMemberService) {
        this.boardMemberService = boardMemberService;
    }

    @GetMapping
    public ResponseEntity<List<BoardMemberResponseDTO>> getAllBoardMembers() {
        return ResponseEntity.ok(boardMemberService.getAllBoardMembers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardMemberResponseDTO> updateBoardMember(
            @PathVariable @NonNull Long id,
            @RequestBody @Valid BoardMemberRequestDTO dto) {
        return ResponseEntity.ok(boardMemberService.updateBoardMember(id, dto));
    }
}
