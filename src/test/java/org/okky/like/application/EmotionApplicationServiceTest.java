package org.okky.like.application;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.like.TestMother;
import org.okky.like.domain.model.Emotion;
import org.okky.like.domain.model.EmotionType;
import org.okky.like.domain.repository.EmotionRepository;
import org.okky.like.domain.service.EmotionConstraint;
import org.okky.like.domain.service.EmotionHelper;
import org.okky.like.domain.service.EmotionProxy;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.okky.like.domain.model.EmotionType.FUN;
import static org.okky.like.domain.model.EmotionType.LIKE;

@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = PRIVATE)
public class EmotionApplicationServiceTest extends TestMother {
    @InjectMocks
    EmotionApplicationService service;
    @Mock
    EmotionRepository repository;
    @Mock
    EmotionConstraint constraint;
    @Mock
    EmotionHelper helper;
    @Mock
    EmotionProxy proxy;
    @Mock
    ModelMapper mapper;
    @Mock
    Emotion emotion;
    @Captor
    ArgumentCaptor<EmotionType> captor;

    /**
     * 예를 들어 'LIKE'한 상태에서 'LIKE'로 요청이 들어오면 공감표현을 제거한다.
     */
    @Test
    public void doEmotion_감정표현을_이미_했고_같은_것을_시도하는_경우_공감표현을_제거() {
        when(emotion.isSameEmotionType(any())).thenReturn(true);
        when(helper.wasAlreadyEmoted("t", "m")).thenReturn(true);
        when(repository.findByTargetIdAndMemberId("t", "m")).thenReturn(Optional.of(emotion));

        service.doEmotion("t", "m", "LIKE");

        InOrder o = inOrder(repository, emotion, constraint, mapper, proxy);
        o.verify(repository).findByTargetIdAndMemberId("t", "m");
        o.verify(emotion).isSameEmotionType(captor.capture());
        o.verify(repository).delete(emotion);
        o.verify(emotion, never()).replaceEmotionType(any());
        o.verify(constraint, never()).rejectIfArticleNotExists(anyString());
        o.verify(repository, never()).save(any());
        o.verify(mapper, never()).toEvent(any());
        o.verify(proxy, never()).sendEvent(any());

        assertEquals("인자는 LIKE여야 한다.", LIKE, captor.getValue());
    }

    /**
     * 예를 들어 'LIKE'한 상태에서 'FUN'로 요청이 들어오면 공감표현을 바꾼다.
     */
    @Test
    public void doEmotion_감정표현을_이미_했고_다른_것을_시도하는_경우_교체() {
        when(helper.wasAlreadyEmoted("t", "m")).thenReturn(true);
        when(repository.findByTargetIdAndMemberId("t", "m")).thenReturn(Optional.of(emotion));

        service.doEmotion("t", "m", "FUN");

        InOrder o = inOrder(repository, emotion, constraint, mapper, proxy);
        o.verify(repository).findByTargetIdAndMemberId("t", "m");
        o.verify(emotion).isSameEmotionType(captor.capture());
        o.verify(emotion).replaceEmotionType(captor.capture());
        o.verify(repository, never()).delete(emotion);
        o.verify(constraint, never()).rejectIfArticleNotExists(anyString());
        o.verify(repository, never()).save(any());
        o.verify(mapper, never()).toEvent(any());
        o.verify(proxy, never()).sendEvent(any());

        assertEquals("첫번째 인자는 FUN여야 한다.", FUN, captor.getAllValues().get(0));
        assertEquals("두번째 인자는 FUN여야 한다.", FUN, captor.getAllValues().get(0));
    }

    @Test
    public void doEmotion_감정표현을_아직_하지_않은_경우는_단순히_DO() {
        when(helper.wasAlreadyEmoted("t", "m")).thenReturn(false);

        service.doEmotion("t", "m", "FUN");

        InOrder o = inOrder(repository, constraint, mapper, proxy);
        o.verify(constraint).rejectIfArticleNotExists("t");
        o.verify(repository).save(isA(Emotion.class));
        o.verify(mapper).toEvent(isA(Emotion.class));
        o.verify(proxy).sendEvent(any());
        o.verify(repository, never()).findByTargetIdAndMemberId(anyString(), anyString());
    }

    @Test
    public void undoEmotion() {
        service.undoEmotion("t", "m");

        InOrder o = inOrder(repository);
        o.verify(repository).deleteByTargetIdAndMemberId("t", "m");
    }
}