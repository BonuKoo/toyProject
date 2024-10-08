package com.myboard.toy.infra.file.service.impl;

import com.myboard.toy.board.board.repository.BoardRepository;
import com.myboard.toy.infra.file.service.FileService;
import com.myboard.toy.infra.file.service.FileStore;
import com.myboard.toy.board.domain.entity.Board;
import com.myboard.toy.infra.file.domain.board.UploadFileOfBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

        private final BoardRepository boardRepository;
        private final FileStore fileStore;

    @Override
    public ResponseEntity<Resource> downloadAttach(Long id) throws MalformedURLException {

        //파일 갖고 있냐 없냐 검증
        UploadFileOfBoard uploadFile = boardRepository.findById(id)
                .map(Board::getFiles)
                .flatMap(files -> files.stream().findFirst())
                .orElse(null);

        if (uploadFile == null){
            return ResponseEntity.notFound().build();
        }

        String storeFileName = uploadFile.getStoreFileName();
        String uploadFileName = uploadFile.getUploadFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));
        log.info("uploadFileName={}",uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
