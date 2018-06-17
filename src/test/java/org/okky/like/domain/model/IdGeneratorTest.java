package org.okky.like.domain.model;

import org.junit.Test;
import org.okky.like.TestMother;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

public class IdGeneratorTest extends TestMother {
    @Test
    public void Emotion은_em로_시작() {
        String id = IdGenerator.newEmotionId();

        assertThat("id는 em-으로 시작해야 한다.", id, startsWith("em-"));
    }

    @Test
    public void newEmotionId_25자_확인() {
        String newId = IdGenerator.newEmotionId();

        assertThat("아이디는 25자여야 한다.", newId.length(), is(25));
    }
}