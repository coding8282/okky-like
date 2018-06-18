package org.okky.like.domain.service;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.like.domain.repository.EmotionRepository;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class EmotionHelper {
    EmotionRepository repository;

    public boolean wasAlreadyEmoted(String targetId, String memberId) {
        return repository.existsByTargetIdAndMemberId(targetId, memberId);
    }
}
