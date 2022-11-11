package myweb.secondboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class File {
    // 동호회 대표이미지
    // 내정보 회원페이지
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="file_id")
    private Long id;

    @Lob
    private byte[] saveImg;


    public static File createImg(byte[] photoImg){
        File file = new File();

        file.setSaveImg(photoImg);
        return file;

    }




    public void  updateImgPath(File originFile, byte[] files){

        originFile.setSaveImg(files);
    }



}
