package org.okky.like.application;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.okky.like.TestMother;
import org.okky.like.domain.model.Emotion;
import org.okky.share.event.Emoted;

import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.okky.like.domain.model.EmotionType.LIKE;

@FieldDefaults(level = PRIVATE)
public class ModelMapperTest extends TestMother {
    ModelMapper mapper = new ModelMapper();

    @Test
    public void toEvent_이벤트로_정확히_변환이_되는지_확인() {
        Emotion emotion = new Emotion("t", "m", LIKE);
        Emoted event = mapper.toEvent(emotion);

        assertThat("id가 다르다.", event.getId(), is(emotion.getId()));
        assertThat("targetId가 다르다.", event.getTargetId(), is(emotion.getTargetId()));
        assertThat("memberId가 다르다.", event.getMemberId(), is(emotion.getMemberId()));
        assertThat("emotedOn가 다르다.", event.getEmotedOn(), is(emotion.getEmotedOn()));
        assertThat("type가 다르다.", event.getType(), is(emotion.getType().name()));
    }
}