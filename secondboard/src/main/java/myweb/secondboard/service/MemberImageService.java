package myweb.secondboard.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import myweb.secondboard.domain.MemberUploadFile;
import myweb.secondboard.repository.MemberUploadFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class MemberImageService {

  @Autowired
  MemberUploadFileRepository memberUploadFileRepository;

  private final Path rootLocation; // d:/image/

  public MemberImageService(String uploadPath) {
    this.rootLocation = Paths.get(uploadPath);
    System.out.println(rootLocation.toString());
  }

  public MemberUploadFile updateStore(MultipartFile file) throws Exception {

    try {
      if(file.isEmpty()) {
        throw new Exception("Failed to store empty file " + file.getOriginalFilename());
      }

      String saveFileName = fileSave(rootLocation.toString(), file);
      MemberUploadFile saveFile = new MemberUploadFile();
      saveFile.setFileName(file.getOriginalFilename());
      saveFile.setSaveFileName(saveFileName);
      saveFile.setContentType(file.getContentType());
      saveFile.setSize(file.getResource().contentLength());
      saveFile.setRegisterDate(LocalDateTime.now());
      saveFile.setFilePath(rootLocation.toString().replace(File.separatorChar, '/') +'/' + saveFileName);
      memberUploadFileRepository.save(saveFile);
      return saveFile;

    } catch(IOException e) {
      throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
    }


  }

  public MemberUploadFile load(Long fileId) {
    return memberUploadFileRepository.findById(fileId).get();
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

  public Optional<MemberUploadFile> findById(Long imageId) {
//    if (uploadFileRepository.findById(imageId).isPresent()) {
//      return uploadFileRepository.findById(imageId);
//    }
//    return Optional.empty();
    if (memberUploadFileRepository.findById(imageId).isPresent()) {
      return Optional.of(memberUploadFileRepository.findById(imageId).get());
    }
    return Optional.empty();
  }

  public void deleteById(Long imageId) {
    memberUploadFileRepository.deleteById(imageId);
  }

  public MemberUploadFile newStore(MultipartFile file) throws Exception {

    try {
      if(file.isEmpty()) {
        throw new Exception("Failed to store empty file " + file.getOriginalFilename());
      }

      String saveFileName = newFileSave(rootLocation.toString(), file);
      MemberUploadFile saveFile = new MemberUploadFile();
      saveFile.setFileName(file.getOriginalFilename());
      saveFile.setSaveFileName(saveFileName);
      saveFile.setContentType(file.getContentType());
      saveFile.setSize(file.getResource().contentLength());
      saveFile.setRegisterDate(LocalDateTime.now());
      saveFile.setFilePath(rootLocation.toString().replace(File.separatorChar, '/') +'/' + saveFileName);
      memberUploadFileRepository.save(saveFile);
      return saveFile;

    } catch(IOException e) {
      throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
    }


  }

  public String newFileSave(String rootLocation, MultipartFile file) throws IOException {
    File uploadDir = new File(rootLocation);

    if (!uploadDir.exists()) {
      uploadDir.mkdirs();
    }

    // saveFileName 생성
    String saveFileName = file.getOriginalFilename();
    File saveFile = new File(rootLocation, saveFileName);
    FileCopyUtils.copy(file.getBytes(), saveFile);

    return saveFileName;
  }
}
