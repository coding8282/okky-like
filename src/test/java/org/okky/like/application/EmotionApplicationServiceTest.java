package org.okky.like.application;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.like.TestMother;
import org.okky.like.domain.model.Emotion;
import org.okky.like.domain.repository.EmotionRepository;
import org.okky.like.domain.service.EmotionConstraint;
import org.okky.like.domain.service.EmotionHelper;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;
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
    Emotion emotion;

    /**
     * 예를 들어 'LIKE'한 상태에서 'LIKE'로 요청이 들어오면 아무 것도 하지 않는다.
     */
    @Test
    public void doEmotion_감정표현을_이미_했고_같은_것을_시도하는_경우_아무_것도_하지_않음() {
        when(emotion.isDifferentEmotionType(any())).thenReturn(false);
        when(helper.wasAlreadyEmoted("t", "m")).thenReturn(true);
        when(repository.findByTargetIdAndMemberId("t", "m")).thenReturn(Optional.of(emotion));

        service.doEmotion("t", "m", "LIKE");

        InOrder o = inOrder(repository, emotion);
        o.verify(repository).findByTargetIdAndMemberId("t", "m");
        o.verify(emotion).isDifferentEmotionType(LIKE);
        o.verify(emotion, never()).replaceEmotionType(any());
    }

    /**
     * 예를 들어 'LIKE'한 상태에서 'FUN'로 요청이 들어오면 감정 표현을 바꾼다.
     */
    @Test
    public void doEmotion_감정표현을_이미_했고_다른_것을_시도하는_경우_교체() {
        DoEmotionCommand cmd = new DoEmotionCommand("t", "m", "FUN");
        when(emotion.isDifferentEmotionType(any())).thenReturn(true);
        when(helper.wasAlreadyEmoted("t", "m")).thenReturn(true);
        when(repository.findByTargetIdAndMemberId("t", "m")).thenReturn(Optional.of(emotion));

        service.doEmotion("t", "m", "FUN");

        InOrder o = inOrder(repository, emotion);
        o.verify(repository).findByTargetIdAndMemberId("t", "m");
        o.verify(emotion).isDifferentEmotionType(FUN);
        o.verify(emotion).replaceEmotionType(FUN);
    }

    @Test
    public void doEmotion_감정표현을_아직_하지_않은_경우는_단순히_DO() {
        DoEmotionCommand cmd = new DoEmotionCommand("t", "m", "FUN");
        when(helper.wasAlreadyEmoted("t", "m")).thenReturn(false);

        service.doEmotion("t", "m", "FUN");

        InOrder o = inOrder(repository, constraint);
        o.verify(constraint).rejectIfArticleNotExists("t");
        o.verify(repository).save(any(Emotion.class));
    }

    @Test
    public void undoEmotion() {
        UndoEmotionCommand cmd = new UndoEmotionCommand("t", "m");

        service.undoEmotion(cmd);

        InOrder o = inOrder(repository);
        o.verify(repository).deleteByTargetIdAndMemberId("t", "m");
    }
}