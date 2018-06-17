package org.okky.like.domain.model;

import org.junit.Test;
import org.okky.like.TestMother;
import org.okky.share.execption.BadArgument;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.okky.like.domain.model.EmotionType.LIKE;
import static org.okky.like.domain.model.EmotionType.THANKS;

public class EmotionTest extends TestMother {
    @Test
    public void targetId가_null이면_예외() {
        expect(BadArgument.class, "공감표현 대상 id는 필수입니다.");

        new Emotion(null, "m-1", LIKE);
    }

    @Test
    public void memberId가_null이면_예외() {
        expect(BadArgument.class, "회원 id는 필수입니다.");

        new Emotion("a-1", null, LIKE);
    }

    @Test
    public void type이_null이면_예외() {
        expect(BadArgument.class, "공감표현은 필수입니다.");

        new Emotion("a-1", "m-1", null);
    }

    @Test
    public void isDifferentEmotionType_다른_공감표현일_경우_true() {
        Emotion emotion = new Emotion("a-1", "m-3", LIKE);
        boolean isDifferent = emotion.isDifferentEmotionType(THANKS);

        assertTrue("같은 타입이므로 true여야 한다.", isDifferent);
    }

    @Test
    public void isDifferentEmotionType_같은_공감표현일_경우_false() {
        Emotion emotion = new Emotion("a-1", "m-3", LIKE);
        boolean isDifferent = emotion.isDifferentEmotionType(LIKE);

        assertFalse("다른 타입이므로 false여야 한다.", isDifferent);
    }

    @Test
    public void isDifferentEmotionType_null일_경우_true() {
        Emotion emotion = new Emotion("a-1", "m-3", LIKE);
        boolean isDifferent = emotion.isDifferentEmotionType(null);

        assertTrue("다른 타입이므로 false여야 한다.", isDifferent);
    }
}