package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    @Transactional
    //base64 인코딩하여 byte [] 형식을 리턴해줌
    public byte[] ImgSave(MultipartFile file) throws IOException {

        byte[] photoEncode = new byte[0];

        if (file != null) {
            Base64.Encoder encoder = Base64.getEncoder();
            photoEncode = encoder.encode(file.getBytes());
        }
        return photoEncode;
    }


}
