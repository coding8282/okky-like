package org.okky.like.application;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.like.domain.repository.EmotionRepository;
import org.okky.like.resource.dto.EmotionDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class EmotionQueryService {
    EmotionRepository repository;

    public EmotionDto findEmotionStat(String targetId) {
        Object[] tuple = (Object[]) repository.queryEmotionStatByTargetId(targetId);
        return new EmotionDto(tuple);
    }

    public EmotionDto findEmotionFullStat(String targetId, String memberId) {
        Object[] tuple = (Object[]) repository.queryEmotionStatByTargetId(targetId);
        String myEmotionType = repository.findEmotionType(targetId, memberId);
        return new EmotionDto(tuple, myEmotionType);
    }
}
