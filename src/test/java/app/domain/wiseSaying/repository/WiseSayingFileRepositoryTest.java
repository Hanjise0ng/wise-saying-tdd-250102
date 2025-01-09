package app.domain.wiseSaying.repository;

import app.domain.wiseSaying.WiseSaying;
import app.standard.Util;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingFileRepositoryTest {

    WiseSayingFileRepository wiseSayingRepository = new WiseSayingFileRepository();

    @BeforeEach
    void beforeEach() {
        Util.File.deleteForce("db/test");
    }

    @AfterEach
    void afterEach() {
        Util.File.deleteForce("db/test");
    }

    @Test
    @DisplayName("명언 저장")
    void t1() {

        WiseSaying wiseSaying = new WiseSaying(1, "aaa", "bbb");

        wiseSayingRepository.save(wiseSaying);

        String filePath = "db/test/wiseSaying/%d.json".formatted(wiseSaying.getId());

        boolean rst = Files.exists(Path.of(filePath));
        assertThat(rst).isTrue();

        Map<String, Object> map = Util.Json.readAsMap(filePath);
        WiseSaying savedWiseSaying = WiseSaying.fromMap(map);

        assertThat(wiseSaying).isEqualTo(savedWiseSaying);
    }

    @Test
    @DisplayName("명언 삭제")
    void t2() {

        WiseSaying wiseSaying = new WiseSaying(1, "aaa", "bbb");

        wiseSayingRepository.save(wiseSaying);

        String filePath = "db/test/wiseSaying/%d.json".formatted(wiseSaying.getId());

        boolean delRst= wiseSayingRepository.deleteById(wiseSaying.getId());
        boolean rst = Files.exists(Path.of(filePath));

        assertThat(rst).isFalse();
        assertThat(delRst).isTrue();
    }

    @Test
    @DisplayName("id로 명언 가져오기")
    void t3() {
        WiseSaying wiseSaying = new WiseSaying(1, "aaa", "bbb");
        wiseSayingRepository.save(wiseSaying);

        assertThat(Files.exists(Path.of("db/test/wiseSaying/%d.json".formatted(wiseSaying.getId())))).isTrue();

        Optional<WiseSaying> opWiseSaying = wiseSayingRepository.findById(wiseSaying.getId());
        WiseSaying foundWiseSaying = opWiseSaying.orElse(null);

        assertThat(foundWiseSaying).isNotNull();
        assertThat(foundWiseSaying).isEqualTo(wiseSaying);
    }

    @Test
    @DisplayName("모든 명언 가져오기")
    void t4() {
        WiseSaying wiseSaying1 = new WiseSaying(1, "aaa", "bbb");
        WiseSaying wiseSaying2 = new WiseSaying(2, "ccc", "ddd");
        WiseSaying wiseSaying3 = new WiseSaying(3, "eee", "fff");

        wiseSayingRepository.save(wiseSaying1);
        wiseSayingRepository.save(wiseSaying2);
        wiseSayingRepository.save(wiseSaying3);

        List<WiseSaying> wiseSayings = wiseSayingRepository.findAll();

        assertThat(wiseSayings).hasSize(3);
        assertThat(wiseSayings).contains(wiseSaying1, wiseSaying2, wiseSaying3);

    }

    @Test
    @DisplayName("lastId 가져오기")
    void t5() {
        WiseSaying wiseSaying1 = new WiseSaying( "aaa1", "bbb");
        wiseSayingRepository.save(wiseSaying1);

        WiseSaying wiseSaying2 = new WiseSaying( "aaa1", "bbb");
        wiseSayingRepository.save(wiseSaying2);

        int lastId = wiseSayingRepository.getLastId();

        assertThat(lastId).isEqualTo(wiseSaying2.getId());
    }
}