package org.okky.like.application;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.like.domain.repository.EmotionRepository;
import org.okky.like.resource.dto.EmotionFullStatDto;
import org.okky.like.resource.dto.EmotionStatDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class EmotionQueryService {
    EmotionRepository repository;

    public EmotionStatDto findEmotionStat(String targetId) {
        Object[] tuple = (Object[]) repository.queryEmotionStatByTargetId(targetId);
        return new EmotionStatDto(tuple);
    }

    public EmotionFullStatDto findEmotionFullStat(String targetId, String memberId) {
        Object[] tuple = (Object[]) repository.queryEmotionStatByTargetId(targetId);
        String myEmotionType = repository.findEmotionType(targetId, memberId);
        return new EmotionFullStatDto(tuple, myEmotionType);
    }
}
