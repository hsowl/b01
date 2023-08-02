package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2

//runwith같은거 안넣고 springboottest 하나 넣으면 됨
class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder().
                    title("title......" + i).
                    content("content...." + i).
                    writer("user" + (i % 10)).
                    build(); // application.properties에 spring.jpa.show-sql=true 이게 있어야 콘솔에서 db 들어가는게 보여줌.

            Board result = boardRepository.save(board);
            log.info("bno  : " + result.getBno());
        });
    }


    @Test
    public void testRead() {

        Optional<Board> id = boardRepository.findById(3L);

        Board board = id.orElseThrow();
        log.info(board);
    }

    @Test
    public void testDelete() {

        Optional<Board> id = boardRepository.findById(3L);
        Board board = id.orElseThrow();
        boardRepository.delete(board);

    }

//    @Test
//    public void testTitle() {
//        String title = "title......70";
//        Board byTitle = boardRepository.findByTitle(title);
//        log.info(byTitle);
//    }

    @Test
    public void testPaging() {

        PageRequest request = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> page = boardRepository.findAll(request);
        log.info(page.getTotalPages());
        log.info(page.getSize());
        log.info(page.getTotalElements());
        log.info(page.getNumber());

        page.getContent().forEach(board->log.info(board));
    }

    @Test
    public void testWriter(){
        boardRepository.findBoardByWriter("user1").forEach(
                board->log.info("board : " + board)
        );
    }


    @Test
    public void testWriterAndContent(){
        boardRepository.findBoardByWriterAndContent("user1","content....51").forEach(
                board->log.info("board : " + board)
        );
    }

    @Test
    public void testBetween(){
        boardRepository.findByBnoBetween(10L,15L) // 10~15번 사이 조회하겠다~
                .forEach(board->log.info(board));
    }

    @Test
    public void testLike(){
        boardRepository.findByWriterLike("%5%") // 5가 포함된거 다 조회~
                .forEach(board->log.info("board : " + board));
    }

    @Test
    public void testContaining(){
        boardRepository.findByWriterContaining("5") // 5가 포함된거 다 조회~
                .forEach(board->log.info("board : " + board));
    }

    @Test
    public void testfindByBnoLessThanOrderByContentDesc(){
        boardRepository.findByBnoLessThanOrderByContentDesc(100L) // 100 이하 모두 나열
                .forEach(board->log.info("board : " + board));
    }

    @Test
    public void testQuery1(){
        boardRepository.findByWriterDetail("2") // 타이틀 2가 들어간건 모두 서치,
                .forEach(board->log.info("board:"+ board));
    }
    @Test
    public void testQuery2(){
        boardRepository.findByWriterTitleDetail("2","1") // 유저에 2가들어가고 타이틀에 1이들어가는거 조회
                .forEach(board->log.info("board:"+ board));
    }

    @Test
    public void testKeywordPage() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("bno").descending());

        Page<Board> page = boardRepository.findKeyword("2", pageable);

        log.info("page : " + page.getTotalPages());
        log.info("page : " + page.getTotalElements());
        log.info("page : " + page.getSize());
        log.info("page : " + page.getNumber());

        page.getContent().forEach(list -> log.info(list));
    }

    @Test
    public void testOne(){
        Board board = boardRepository.searchBno(100L);
        log.info("=====>" + board);
    }

    @Test
    public void testByTitle(){
        boardRepository.findByTitle2("2").forEach(board->log.info("Title : "+ board));
    }

    @Test
    public void testQuery3(){
        boardRepository.findByWriterTitleDetail2("2","2").
                forEach(board -> log.info(Arrays.toString(board)));
    }

    @Test
    public void testSearch1() {
//        Pageable pageable = PageRequest.of(0, 5, Sort.by("bno").descending());
//        Page<Board> search1 = boardRepository.search1(pageable);

        Page<Board> search1 = boardRepository.search1(PageRequest.of(0,5,Sort.by("bno").descending()));

        log.info("getTotalPages : " + search1.getTotalPages());
        log.info("get : " + search1.getTotalElements());
        search1.getContent().forEach(list->log.info(list));
    }
}