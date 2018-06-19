package org.okky.like.domain.repository;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.okky.like.TestMother;
import org.okky.like.domain.model.Emotion;
import org.okky.like.domain.model.EmotionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static java.math.BigInteger.valueOf;
import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.okky.like.domain.model.EmotionType.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@FieldDefaults(level = PRIVATE)
public class EmotionRepositoryTest extends TestMother {
    @Autowired
    EmotionRepository repository;

    @Test
    public void existsByTargetIdAndMemberId_있는_경우_true() {
        repository.save(fixture("t1", "m1", LIKE));
        boolean exists = repository.existsByTargetIdAndMemberId("t1", "m1");

        assertTrue("존재하므로 true를 반환해야 한다.", exists);
    }

    @Test
    public void existsByTargetIdAndMemberId_없는_경우_false() {
        repository.save(fixture("t1", "m1", LIKE));
        boolean exists = repository.existsByTargetIdAndMemberId("t1", "m9");

        assertFalse("존재하므로 true를 반환해야 한다.", exists);
    }

    @Test
    public void findByTargetIdAndMemberId_targetId와_memberId가_같으면_항상_같음() {
        Emotion emotion = fixture("t1", "m1", LIKE);
        repository.save(emotion);
        Emotion retrieved = repository.findByTargetIdAndMemberId("t1", "m1").get();

        assertEquals("둘이 같아야 한다.", emotion, retrieved);
    }

    @Test
    public void findByTargetIdAndMemberId_targetId는_같지만_memberId가_다르면_다름() {
        Emotion emotion = fixture("t1", "m1", LIKE);
        repository.save(emotion);
        Optional<Emotion> result = repository.findByTargetIdAndMemberId("t1", "m9");

        assertFalse("존재하지 않는다.", result.isPresent());
    }

    @Test
    public void deleteByTargetIdAndMemberId_삭제_확인() {
        repository.save(fixture("t1", "m1", LIKE));

        assertTrue("저장 후 존재해야 한다.", repository.existsByTargetIdAndMemberId("t1", "m1"));

        repository.deleteByTargetIdAndMemberId("t1", "m1");

        assertFalse("삭제 후에는 없어야 한다.", repository.existsByTargetIdAndMemberId("t1", "m1"));
    }

    @Test
    public void findEmotionType_정확히_찾는지_확인() {
        repository.save(fixture("t1", "m1", ANGRY));
        String emotionType = repository.findEmotionType("t1", "m1");

        assertThat("ANGRY를 정확히 가져와야 한다.", emotionType, is("ANGRY"));
    }

    @Test
    public void findEmotionType_targetId가_다르면_null_반환() {
        repository.save(fixture("t1", "m1", ANGRY));
        String emotionType = repository.findEmotionType("t5", "m1");

        assertNull("해당하지 않는 targetId이므로 null이어야 한다.", emotionType);
    }

    @Test
    public void findEmotionType_memberId가_다르면_null_반환() {
        repository.save(fixture("t1", "m1", ANGRY));
        String emotionType = repository.findEmotionType("t1", "m5");

        assertNull("해당하지 않는 memberId이므로 null이어야 한다.", emotionType);
    }

    @Test
    public void queryEmotionStatByTargetId_targetId로_정확히_찾아오는_것을_확인() {
        repository.save(fixture("t1", "m1", LIKE));
        repository.save(fixture("t1", "m2", LIKE));
        repository.save(fixture("t1", "m3", LIKE));
        repository.save(fixture("t1", "m4", FUN));
        repository.save(fixture("t1", "m5", ANGRY));
        repository.save(fixture("t2", "m1", SAD));
        Object[] tuple = (Object[]) repository.queryEmotionStatByTargetId("t1");

        assertThat("targetId는 t1이다.", tuple[0], is("t1"));
        assertThat("전체 공감표현 개수는 5개이다.", tuple[1], is(valueOf(5)));
        assertThat("좋아요 개수는 3개이다.", tuple[2], is(valueOf(3)));
        assertThat("재밌어요 개수는 1개이다.", tuple[3], is(valueOf(1)));
        assertThat("고마워 개수는 0개이다.", tuple[4], is(valueOf(0)));
        assertThat("슬퍼 개수는 0개이다.", tuple[5], is(valueOf(0)));
        assertThat("화나 개수는 1개이다.", tuple[6], is(valueOf(1)));
    }

    @Test
    public void queryEmotionStatByTargetId_targetId가_없다면_빈_통계를_가져와야_함() {
        repository.save(fixture("t1", "m1", LIKE));
        Object[] tuple = (Object[]) repository.queryEmotionStatByTargetId("t9");

        assertThat("targetId는 t9이다.", tuple[0], is("t9"));
        assertThat("없는 target이므로 전체 공감표현 개수는 0개이다.", tuple[1], is(valueOf(0)));
        assertThat("없는 target이므로 좋아요 개수는 0개이다.", tuple[2], is(valueOf(0)));
        assertThat("없는 target이므로 재밌어요 개수는 0개이다.", tuple[3], is(valueOf(0)));
        assertThat("없는 target이므로 고마워 개수는 0개이다.", tuple[4], is(valueOf(0)));
        assertThat("없는 target이므로 슬퍼 개수는 0개이다.", tuple[5], is(valueOf(0)));
        assertThat("없는 target이므로 화나 개수는 0개이다.", tuple[6], is(valueOf(0)));
    }

    @Test
    public void queryMyEmotionStat_memberId로_정확히_찾아오는_것을_확인() {
        repository.save(fixture("t1", "m1", LIKE));
        repository.save(fixture("t1", "m2", LIKE));
        repository.save(fixture("t2", "m1", LIKE));
        repository.save(fixture("t3", "m1", FUN));
        repository.save(fixture("t4", "m1", ANGRY));
        repository.save(fixture("t2", "m9", SAD));
        Object[] tuple = (Object[]) repository.queryMyEmotionStat("m1");

        assertThat("memberId는 m1이다.", tuple[0], is("m1"));
        assertThat("나의 전체 공감표현 개수는 6개이다.", tuple[1], is(valueOf(4)));
        assertThat("나의 좋아요 개수는 3개이다.", tuple[2], is(valueOf(2)));
        assertThat("나의 재밌어요 개수는 1개이다.", tuple[3], is(valueOf(1)));
        assertThat("나의 고마워 개수는 0개이다.", tuple[4], is(valueOf(0)));
        assertThat("나의 슬퍼 개수는 0개이다.", tuple[5], is(valueOf(0)));
        assertThat("나의 화나 개수는 1개이다.", tuple[6], is(valueOf(1)));
    }

    @Test
    public void queryMyEmotionStat_회원이_아무_공감표현도_하지_않았다면_빈_통계를_가져와야_함() {
        Object[] tuple = (Object[]) repository.queryMyEmotionStat("m1");

        assertThat("memberId는 m1이다.", tuple[0], is("m1"));
        assertThat("나의 전체 공감표현 개수는 0개이다.", tuple[1], is(valueOf(0)));
        assertThat("나의 좋아요 개수는 0개이다.", tuple[2], is(valueOf(0)));
        assertThat("나의 재밌어요 개수는 0개이다.", tuple[3], is(valueOf(0)));
        assertThat("나의 고마워 개수는 0개이다.", tuple[4], is(valueOf(0)));
        assertThat("나의 슬퍼 개수는 0개이다.", tuple[5], is(valueOf(0)));
        assertThat("나의 화나 개수는 0개이다.", tuple[6], is(valueOf(0)));
    }


    // -----------------------------
    private Emotion fixture(String targetId, String memberId, EmotionType type) {
        return new Emotion(targetId, memberId, type);
    }
}