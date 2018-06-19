package org.okky.like.domain.model;

import org.okky.share.execption.BadArgument;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.lang.String.join;
import static org.okky.share.domain.AssertionConcern.assertArgNotEmpty;

public enum EmotionType {
    LIKE,// 좋아요 ^^!
    FUN,// 재밌어요 :) !
    THANKS,// 고마워요 >_<!
    SAD,// 슬퍼요 ㅜㅜ!
    ANGRY,// 화나요 \./
    ;

    public static EmotionType parse(String value) {
        try {
            assertArgNotEmpty(value, "공감표현은 필수입니다.");
            return valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            String possibleValues = join(",", Arrays
                    .stream(values())
                    .map(EmotionType::name)
                    .collect(Collectors.toList()));
            throw new BadArgument(format("'%s'는 지원하지 않는 공감표현입니다. '%s'만 가능합니다.", value, possibleValues));
        }
    }
}
