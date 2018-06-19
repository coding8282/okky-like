package org.okky.like.resource.dto;

import org.junit.Test;
import org.okky.like.TestMother;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class MyEmotionStatSummaryDtoTest extends TestMother {
    @Test
    public void new_튜플을_제대로_읽어오는지_확인() {
        Object[] tuple = new Object[8];
        tuple[0] = "m1";
        tuple[1] = BigInteger.valueOf(101);//전체 공감표현 개수
        tuple[2] = BigInteger.valueOf(94);//좋아요 개수
        tuple[3] = BigInteger.valueOf(6);//재밌어요 개수
        tuple[4] = BigInteger.valueOf(0);//고마워요 개수
        tuple[5] = BigInteger.valueOf(0);//슬퍼요 개수
        tuple[6] = BigInteger.valueOf(1);//화나요 개수
        MyEmotionStatSummaryDto dto = new MyEmotionStatSummaryDto(tuple);

        assertEquals("memberId는 m1이다.", dto.getId(), "m1");
        assertEquals("전체 공감표현 개수는 101개이다.", dto.getTotalEmotionCount(), 101);
        assertEquals("좋아요 개수는 94개이다.", dto.getLikeCount(), 94);
        assertEquals("재밌어요 개수는 6개이다.", dto.getFunCount(), 6);
        assertEquals("고마워 개수는 0개이다.", dto.getThanksCount(), 0);
        assertEquals("슬퍼 개수는 0개이다.", dto.getSadCount(), 0);
        assertEquals("화나 개수는 1개이다.", dto.getAngryCount(), 1);
    }
}