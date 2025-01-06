package app.standard;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Util {

    public static class File {
        public static void test() {
            System.out.println("파일 유틸 테스트");
        }

        public static void createFile(String pathValue) {
            Path filepath = Paths.get(pathValue);
            
//            if (Files.exists(filepath)) {
//                return;
//            }
            try {
                Files.createFile(filepath);
            } catch (Exception e) {
                System.out.println("파일 생성 중 실패");
                e.printStackTrace();
            }

        }
    }

}
