package domain.wiseSaying;

import java.util.ArrayList;
import java.util.List;

public class WiseSayingService {

    private final List<WiseSaying> wiseSayingList;
    private int lastId;

    public WiseSayingService() {
        this.wiseSayingList = new ArrayList<>();
        this.lastId = 0;
    }

    public WiseSaying write(String content, String author) {
        int id = ++lastId;
        WiseSaying wiseSaying = new WiseSaying(id, content, author);
        wiseSayingList.add(wiseSaying);

        return wiseSaying;
    }

    public List<WiseSaying> getAllItems() {
        return wiseSayingList;
    }
}
