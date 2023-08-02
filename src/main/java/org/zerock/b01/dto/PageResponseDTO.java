package org.zerock.b01.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> {

    private int page;
    private int size;
    private int total;

    private int start;
    private int end;

    private boolean prev;
    private boolean next;

    private List<E> dtoList;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total){
        if(total <= 0){
            return;
        }

        this.page = pageRequestDTO.getPage(); // 1
        this.size = pageRequestDTO.getSize(); // 10

        this.total = total;   // 105
        this.dtoList = dtoList; // 게시글 리스트

        this.end = (int)(Math.ceil(this.page/10.0))*10;

        this.start = this.end - 9;

        int last = (int)(Math.ceil(total/(double)size)); //제일 마지막 페이지 11

        this.end = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end*this.size; // 105 > 10*10


    }
}
