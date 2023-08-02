package org.zerock.b01.service;

import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

@SpringBootTest
@Log4j2
class BoardServiceImplTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {
        BoardDTO boardDTO = BoardDTO.builder()
                .title("Sample title..")
                .content("Sample Content")
                .writer("user01")
                .build();

        // 위에랑 아래랑 같다

        /*BoardDTO bb = new BoardDTO();
        bb.setTitle("Sample title..");
        bb.setContent("Sample Content");
        bb.setWriter("user00");*/

        Long bno = boardService.register(boardDTO);
        log.info("bno : " + bno);
    }

    @Test
    public void testReadOne(){
        BoardDTO boardDTO = boardService.readOne(101L);

        log.info("boardDTO : " + boardDTO);
    }

    @Test
    public void testModify(){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(99L)
                .title("수정 테스트")
                .content("수정 내용 테스트")
                .build();
                boardService.modify(boardDTO);
    }

    @Test
    public void testRemove() {
        Long bno = 101L;
        boardService.remove(bno);
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("user")
                .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        log.info("responseDTO =======> "+responseDTO);
    }
}