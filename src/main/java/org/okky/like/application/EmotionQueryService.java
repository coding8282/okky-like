package org.okky.like.application;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.like.domain.repository.EmotionRepository;
import org.okky.like.resource.dto.EmotionFullStatDto;
import org.okky.like.resource.dto.EmotionStatDto;
import org.okky.like.resource.dto.MyEmotionStatDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class EmotionQueryService {
    EmotionRepository repository;

    public MyEmotionStatDto findMyEmotionStat(String memberId) {
        Object[] tuple = (Object[]) repository.queryMyEmotionStat(memberId);
        return new MyEmotionStatDto(tuple);
    }

    public EmotionStatDto findEmotionStat(String targetId) {
        Object[] tuple = (Object[]) repository.queryEmotionStat(targetId);
        return new EmotionStatDto(tuple);
    }

    public EmotionFullStatDto findEmotionFullStat(String targetId, String memberId) {
        Object[] tuple = (Object[]) repository.queryEmotionStat(targetId);
        String myEmotionType = repository.findEmotionType(targetId, memberId);
        return new EmotionFullStatDto(tuple, myEmotionType);
    }
}
