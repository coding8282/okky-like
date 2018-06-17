package org.okky.like.domain.model;

import org.junit.Test;
import org.okky.like.TestMother;
import org.okky.share.execption.BadArgument;

import static org.junit.Assert.assertEquals;
import static org.okky.like.domain.model.EmotionType.*;

public class EmotionTypeTest extends TestMother {
    @Test
    public void parse_공감표현이_null이면_예외() {
        expect(BadArgument.class, "공감표현은 필수입니다.");

        EmotionType.parse(null);
    }

    @Test
    public void parse_공감표현이_빈문자열이면_예외() {
        expect(BadArgument.class, "공감표현은 필수입니다.");

        EmotionType.parse("");
    }

    @Test
    public void parse_COURIUS는_지원하지_않는_공감표현() {
        expect(BadArgument.class, "'COURIUS'는 지원하지 않는 공감표현입니다. 'LIKE,FUN,THANKS,SAD,ANGRY'만 가능합니다.");

        EmotionType.parse("COURIUS");
    }

    @Test
    public void parse_소문자를_줘도_분석_성공() {
        EmotionType type = EmotionType.parse("thanks");

        assertEquals("대소문자를 섞어서 줘도 분석에 성공해야 한다.", type, THANKS);
    }

    @Test
    public void parse_대소문자를_섞어서_줘도_분석_성공() {
        EmotionType type = EmotionType.parse("sAd");

        assertEquals("대소문자를 섞어서 줘도 분석에 성공해야 한다.", type, SAD);
    }

    @Test
    public void parse_trim_확인() {
        EmotionType type = EmotionType.parse("  LIKE\t\t");

        assertEquals("앞뒤 불필요한 공백 문자는 제거해야 한다.", type, LIKE);
    }
}