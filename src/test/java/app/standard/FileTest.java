package app.standard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FileTest {
    @Test
    @DisplayName("최초의 file test")
    void t1(){
        Util.File.test();
    }

    @Test
    @DisplayName("파일 생성. 내용이 없는 빈 파일 생성")
    void t2(){
        String file = "test.txt";

        Util.File.createFile(file); // 파일 생성

        assertThat(Files.exists(Paths.get(file))).isTrue();
    }

    @Test
    @DisplayName("파일 내용 읽어오기")
    void t3() {

        // 파일을 Hello, World 내용으로 생성
        String file = "test.txt";
        String testContent = "Hello, World!";

        Util.File.write(file, testContent);
        String content = Util.File.readAsString(file);

        assertThat(content).isEqualTo("Hello, World!");
    }

    @Test
    @DisplayName("파일 내용 수정")
    void t4() {
        String file = "test.txt";
        String writeContent = "modify content";
        Util.File.write(file, writeContent);

        String readContent = Util.File.readAsString(file);

        assertThat(readContent).isEqualTo(writeContent);
    }

    @Test
    @DisplayName("파일 삭제")
    void t5() {
        // 파일 생성
        String file = "test.txt";

        Util.File.createFile(file);
        // 존재여부 확인
        assertThat(Files.exists(Paths.get(file))).isTrue();

        // 파일 삭제
        Util.File.delete(file);
        // 존재여부 확인
        assertThat(Files.exists(Paths.get(file))).isFalse();
    }

}
