package myweb.secondboard.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import myweb.secondboard.domain.boards.BoardUploadFile;
import myweb.secondboard.repository.UploadFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ImageService {

  @Autowired UploadFileRepository uploadFileRepository;

  private final Path rootLocation; // d:/image/

  public ImageService(String uploadPath) {
    this.rootLocation = Paths.get(uploadPath);
    System.out.println(rootLocation.toString());
  }

  public BoardUploadFile store(MultipartFile file) throws Exception {

    try {
      if(file.isEmpty()) {
        throw new Exception("Failed to store empty file " + file.getOriginalFilename());
      }

      String saveFileName = fileSave(rootLocation.toString(), file);
      BoardUploadFile saveFile = new BoardUploadFile();
      saveFile.setFileName(file.getOriginalFilename());
      saveFile.setSaveFileName(saveFileName);
      saveFile.setContentType(file.getContentType());
      saveFile.setSize(file.getResource().contentLength());
      saveFile.setRegisterDate(LocalDateTime.now());
      saveFile.setFilePath(rootLocation.toString().replace(File.separatorChar, '/') +'/' + saveFileName);
      uploadFileRepository.save(saveFile);
      return saveFile;

    } catch(IOException e) {
      throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
    }


  }

  public BoardUploadFile load(Long fileId) {
    return uploadFileRepository.findById(fileId).get();
  }

  public String fileSave(String rootLocation, MultipartFile file) throws IOException {
    File uploadDir = new File(rootLocation);

    if (!uploadDir.exists()) {
      uploadDir.mkdirs();
    }

    // saveFileName 생성
    UUID uuid = UUID.randomUUID();
    String saveFileName = uuid.toString() + file.getOriginalFilename();
    File saveFile = new File(rootLocation, saveFileName);
    FileCopyUtils.copy(file.getBytes(), saveFile);

    return saveFileName;
  }

  public Optional<BoardUploadFile> findById(Long imageId) {
//    if (uploadFileRepository.findById(imageId).isPresent()) {
//      return uploadFileRepository.findById(imageId);
//    }
//    return Optional.empty();
    if (uploadFileRepository.findById(imageId).isPresent()) {
      return Optional.of(uploadFileRepository.findById(imageId).get());
    }
    return Optional.empty();
  }

  public void deleteById(Long imageId) {
    uploadFileRepository.deleteById(imageId);
  }
}
