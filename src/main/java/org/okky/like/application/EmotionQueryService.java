package org.okky.like.application;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.like.domain.repository.EmotionRepository;
import org.okky.like.resource.dto.EmotionStatSummaryDto;
import org.okky.like.resource.dto.MyEmotionStatSummaryDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class EmotionQueryService {
    EmotionRepository repository;

    public MyEmotionStatSummaryDto queryMyEmotionStat(String memberId) {
        Object[] tuple = (Object[]) repository.queryMyEmotionStat(memberId);
        return new MyEmotionStatSummaryDto(tuple);
    }

    public EmotionStatSummaryDto queryEmotionFullStat(String targetId, String memberId) {
        Object[] tuple = (Object[]) repository.queryEmotionStat(targetId);
        String myEmotionType = repository.findEmotionType(targetId, memberId);
        return new EmotionStatSummaryDto(tuple, myEmotionType);
    }
}
