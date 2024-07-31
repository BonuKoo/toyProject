package com.myboard.toy.domain.board.dto;

import com.myboard.toy.domain.file.board.UploadFileOfBoard;
import com.myboard.toy.domain.reply.Reply;
import com.myboard.toy.domain.reply.dto.ReplyDTO;
import com.myboard.toy.securityproject.domain.entity.Account;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class BoardDTO {
    private Long id;
    private String title;
    private String content;
    private Account account;
    private List<Reply> replies;
    private List<MultipartFile> imageFiles; // 파일 업로드 필드
    //service -> 컨트롤러
    private List<UploadFileOfBoard> formattedFiles;

    public BoardDTO(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    //게시글 생성용
    public BoardDTO (String title,String content){
        this.title=title;
        this.content=content;
    }

    //게시글 생성 V2 용
    public BoardDTO(String title,String content,
                    Account account, List<UploadFileOfBoard> formattedFiles){
        this.title=title;
        this.content=content;
        this.account=account;
        this.formattedFiles=formattedFiles;
    }

    //Service의 createBoardV2용
    public BoardDTO(Long id, String title, String content,
                    Account account,
                    List<Reply> replies,List<UploadFileOfBoard> formattedFiles) {
        this.id = id;
        this.title = title;
        this.account = account;
        this.content = content;
        this.replies = replies;
        this.formattedFiles = formattedFiles;
    }

    @QueryProjection
    public BoardDTO(Long id, String title, String content, List<Reply> replies) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.replies = replies;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
