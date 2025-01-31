package app.standard;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;

public class Util {

    public static class File {
        public static void test() {
            System.out.println("파일 유틸 테스트");
        }

        public static void createFile(String pathValue) {
            write(pathValue, "");
        }

        public static String readAsString(String file) {
            Path filepath = Paths.get(file);

            try {
                return Files.readString(filepath);
            } catch (IOException e) {
                System.out.println("파일 읽기 실패");
                e.printStackTrace();
            }

            return "";
        }

        public static void write(String file, int content) {
            write(file, String.valueOf(content));
        }

        public static void write(String file, String content) {
            Path filepath = Paths.get(file);

            if (filepath.getParent() != null) {
                createDir(filepath.getParent().toString());
            }

            try {
                Files.writeString(filepath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                System.out.println("파일 쓰기 실패");
                e.printStackTrace();
            }
        }

        public static boolean delete(String file) {
            Path filepath = Paths.get(file);

            if (!Files.exists(filepath)) return false;

            try {
                Files.delete(filepath);
                return true;
            } catch (IOException e) {
                System.out.println("파일 삭제 실패");
                e.printStackTrace();
                return false;
            }
        }

        public static void createDir(String dirPath) {
            try {
                Files.createDirectories(Paths.get(dirPath));
            } catch (IOException e) {
                System.out.println("디렉토리 생성 실패");
                e.printStackTrace();
            }
        }

        public static void deleteForce(String path) {

            Path folderPath = Paths.get(path);

            if (!Files.exists(folderPath)) return;

            try {
                // 디렉토리 및 내용 삭제
                Files.walkFileTree(folderPath, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        // 파일 삭제
                        Files.delete(file);
                        System.out.println("파일 삭제됨: " + file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        // 디렉토리 삭제 (내부 파일 모두 삭제 후 호출됨)
                        Files.delete(dir);
                        System.out.println("디렉토리 삭제됨: " + dir);
                        return FileVisitResult.CONTINUE;
                    }
                });

                System.out.println("폴더와 그 안의 내용이 성공적으로 삭제되었습니다.");
            } catch (IOException e) {
                System.err.println("폴더 삭제 중 오류 발생: " + e.getMessage());
            }
        }

        public static List<Path> getPaths(String dirPathStr) {

            Path dirPath = Paths.get(dirPathStr);

            try {
                return Files.walk(dirPath)
                        .filter(Files::isRegularFile)
                        .toList();

            } catch (Exception e) {
                System.out.println("파일 목록 가져오기 실패");
                e.printStackTrace();
            }
            return List.of();
        }

        public static boolean exists(String filePath) {
            return Files.exists(Paths.get(filePath));
        }
    }

    public static class Json {
        public static String mapToJson(Map<String, Object> map) {

            StringBuilder jsonBuilder = new StringBuilder();

            jsonBuilder.append("{\n");

            String str = map.keySet().stream()
                    .map(k -> map.get(k) instanceof String
                            ? "    \"%s\" : \"%s\"".formatted(k, map.get(k))
                            : "    \"%s\" : %s".formatted(k, map.get(k))
                    ).collect(Collectors.joining(",\n"));

            jsonBuilder.append(str);
            jsonBuilder.append("\n}");

            return jsonBuilder.toString();
        }

        public static void writeAsMap(String filePath, Map<String, Object> wiseSayingMap) {
            String jsonStr = mapToJson(wiseSayingMap);
            File.write(filePath, jsonStr);
        }

        public static Map<String, Object> readAsMap(String filePath) {
            String jsonStr = File.readAsString(filePath);

            if(jsonStr.isEmpty()) return new LinkedHashMap<>();

            return jsonToMap(jsonStr);
        }

        public static Map<String, Object> jsonToMap(String jsonStr) {

            Map<String, Object> resultMap = new LinkedHashMap<>();

            jsonStr = jsonStr.replaceAll("\\{", "")
                    .replaceAll("}", "")
                    .replaceAll("\n", "")
                    .replaceAll(" : ", ":");

            Arrays.stream(jsonStr.split(",")) // ["id" : 1, "content" : "aaa", "author" : "bbb"]
                    .map(p -> p.trim().split(":")) //  p => [""id" : 1"]
                    .forEach(p -> { // p => ["id", 1]
                        String key = p[0].replaceAll("\"", "");
                        String value = p[1];

                        // 문자열
                        if(value.startsWith("\"")) {
                            resultMap.put(key, value.replaceAll("\"", ""));
                        }
                        // 실수
                        else if(value.contains(".")) {
                            resultMap.put(key, Double.parseDouble(value));
                        }
                        // 논리
                        else if(value.equals("true") || value.equals("false")) {
                            resultMap.put(key, Boolean.parseBoolean(value));
                        }
                        // 숫자
                        else {
                            resultMap.put(key, Integer.parseInt(value));
                        }
                    });

            return resultMap;
        }
    }
}
