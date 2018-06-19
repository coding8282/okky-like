package org.okky.like.application;

import lombok.experimental.FieldDefaults;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.okky.like.TestMother;
import org.okky.like.domain.model.Emotion;
import org.okky.like.domain.repository.EmotionRepository;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static lombok.AccessLevel.PRIVATE;
import static org.mockito.Mockito.inOrder;

// TODO: 2018. 6. 19. PowerMock 의존 설정에 문제가 있어서 생성자 mocking이 안 되어 테스트 진행 불가.
@RunWith(PowerMockRunner.class)
@PrepareForTest(Emotion.class)
@FieldDefaults(level = PRIVATE)
public class EmotionQueryServiceTest extends TestMother {
    @InjectMocks
    EmotionQueryService service;
    @Mock
    EmotionRepository repository;

    @Ignore
    @Test
    public void findEmotionStat() {
        service.findEmotionStat("t1");

        InOrder o = inOrder(repository);
        o.verify(repository).queryEmotionStat("t1");
    }

    @Ignore
    @Test
    public void findEmotionFullStat() {
        service.findEmotionFullStat("t1", "m1");

        InOrder o = inOrder(repository);
        o.verify(repository).queryEmotionStat("t1");
        o.verify(repository).findEmotionType("t1", "m1");
    }
}