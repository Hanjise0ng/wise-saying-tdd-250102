package app.domain.wiseSaying;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WiseSaying {

    private int id;
    private String content;
    private String author;

    public WiseSaying(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public static WiseSaying fromMap(Map<String, Object> map) {
        int id = (int)map.get("id");
        String content = (String)map.get("content");
        String author = (String)map.get("author");

        return new WiseSaying(id, content, author);
    }

    public boolean isNew() {
        return this.id == 0;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", this.id);
        map.put("content", this.content);
        map.put("author", this.author);

        return map;
    }
}
