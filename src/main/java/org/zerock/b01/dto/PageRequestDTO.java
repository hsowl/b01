package org.zerock.b01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    public void PageRequestDTO(int page, int size, String type, String keyword){
        this.page = page;
        this.size = size;
        this.type = type;
        this.keyword = keyword;

    }

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    private String type;
    private String keyword;

    public String[] getTypes() {    // tcw -> t c w -----> 문자열을 개별 문자로 분리해서 String 배열로 저장
        if(type == null || type.isEmpty()){
            return null;
        }
        return type.split("");
    }

    public Pageable getPageable(String ...props){    //가변인자  ---> 메개변수 갯수 상관 없   pageRe.getPageable(String str, String str2, String str3)
        return PageRequest.of(this.page -1, this.size, Sort.by(props).descending());
    }



    private String link;

    public String getLink(){  // 검색조건과 페이징 조건을 문자열로 구성

        if(link == null){
            StringBuilder builder = new StringBuilder();
            builder.append("page="+this.page); // page=0
            builder.append("&size="+this.size); //page=0&size=10

            if(type != null && type.length() > 0){
                builder.append("&type="+type);
            }

            if(keyword != null){
                try{
                    builder.append("&keyword="+ URLEncoder.encode(keyword, "UTF-8"));
                }catch (UnsupportedEncodingException e){
                }
            }
            link = builder.toString();


        }
        return link;
    }


}
