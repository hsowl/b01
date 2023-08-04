package org.zerock.b01.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.b01.dto.ReplyDTO;

import javax.validation.Valid;
import java.util.Map;

@RestController     // = @ResponseBody 안써도 됨 @RequestBody 아님
@RequestMapping("/replies")
@Slf4j
public class ReplyController {

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> register
            (@Valid @RequestBody ReplyDTO replyDTO, BindingResult bindingResult) throws BindException{    //@Valid == 유효성 체크 "해죠"

        log.info("replyDTO =========> {}", replyDTO);

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = Map.of("rno", 1L);

        return ResponseEntity.ok(resultMap);
    }
}
