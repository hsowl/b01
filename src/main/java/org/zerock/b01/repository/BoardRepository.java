package org.zerock.b01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.b01.domain.Board;
import org.zerock.b01.repository.search.BoardSearch;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> , BoardSearch { //<table,기본키타입>
    List<Board> findBoardByWriter(String writer);

    List<Board> findBoardByWriterAndContent(String writer, String content); // 두개 같이 조회

    List<Board> findByBnoBetween(Long start, Long end); // start부터 end까지 조회

    List<Board> findByWriterLike(String name);

    List<Board> findByWriterContaining(String name);

    List<Board> findByBnoLessThanOrderByContentDesc(Long bno);

    @Query("select b from Board b where b.title like %:user% order by b.bno desc")
    List<Board> findByWriterDetail(@Param("user") String user);

    // select board의 bno, title, writer를 보여달라
    @Query("select board from Board board where board.writer like %:user% and board.title like %:title%")
    List<Board> findByWriterTitleDetail(@Param("user") String u, @Param("title") String t);

    @Query("select b from Board b where b.title like concat('%', :keyword,'%')")
    Page<Board> findKeyword(String keyword, Pageable pageable);

    @Query(value= "select * from board where = :bno", nativeQuery = true)
    Board searchBno(@Param("bno") Long bno);

    @Query("select b from Board b where b.title like %?1%") //첫번째 파라미터값
    List<Board> findByTitle(@Param("title") String title);

    @Query("select b from Board b where b.title like %:title%")
    List<Board> findByTitle2(@Param("title") String title);

    @Query("select b.bno,b.title,b.writer from Board b where b.writer like %:writer% and b.title like %:title%")
    List<Object[]> findByWriterTitleDetail2(@Param("writer") String writer,@Param("title")String title);
}