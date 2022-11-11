package myweb.secondboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.boards.BoardUploadFile;
import myweb.secondboard.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ImageController {

  private final ImageService imageService;
  private final ResourceLoader resourceLoader;

  @PostMapping("/image")
  public ResponseEntity<?> imageUpload(@RequestParam("file") MultipartFile file) {
    try {
      BoardUploadFile uploadFile = imageService.store(file);
      return ResponseEntity.ok().body("/image/" + uploadFile.getId());
    } catch(Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/image/{fileId}")
  public ResponseEntity<?> serveFile(@PathVariable Long fileId){
    try {
      BoardUploadFile uploadFile = imageService.load(fileId);
      Resource resource = resourceLoader.getResource("file:" + uploadFile.getFilePath());
      return ResponseEntity.ok().body(resource);
    } catch(Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }

  }

}
