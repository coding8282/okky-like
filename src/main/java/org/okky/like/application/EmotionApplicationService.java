package org.okky.like.application;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.like.domain.model.Emotion;
import org.okky.like.domain.model.EmotionType;
import org.okky.like.domain.repository.EmotionRepository;
import org.okky.like.domain.service.EmotionConstraint;
import org.okky.like.domain.service.EmotionHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class EmotionApplicationService {
    EmotionRepository repository;
    EmotionConstraint constraint;
    EmotionHelper helper;

    public void doEmotion(String targetId, String memberId, String emotionType) {
        EmotionType type = EmotionType.parse(emotionType);

        boolean alreadyEmoted = helper.wasAlreadyEmoted(targetId, memberId);
        if (alreadyEmoted) {
            Emotion emotion = repository.findByTargetIdAndMemberId(targetId, memberId).get();
            if (emotion.isDifferentEmotionType(type))
                emotion.replaceEmotionType(type);
        } else {
            doEmotion(targetId, memberId, type);
        }
    }

    public void undoEmotion(String targetId, String memberId) {
        repository.deleteByTargetIdAndMemberId(targetId, memberId);
    }

    // ------------------------------------------
    private void doEmotion(String targetId, String memberId, EmotionType type) {
        constraint.rejectIfArticleNotExists(targetId);
        Emotion emotion = new Emotion(targetId, memberId, type);
        repository.save(emotion);
    }
}
