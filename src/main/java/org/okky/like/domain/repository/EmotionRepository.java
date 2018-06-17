package org.okky.like.domain.repository;

import org.okky.like.domain.model.Emotion;
import org.okky.like.domain.model.EmotionType;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = Emotion.class, idClass = String.class)
public interface EmotionRepository {
    boolean existsByTargetIdAndMemberId(String targetId, String memberId);
    long countByTargetId(String targetId);
    long countByMemberIdAndType(String targetId, EmotionType type);
    void save(Emotion emotion);
    Optional<Emotion> findByTargetIdAndMemberId(String targetId, String memberId);
    void deleteByTargetIdAndMemberId(String targetId, String memberId);

    default boolean wasAlreadyEmoted(String targetId, String memberId) {
        return existsByTargetIdAndMemberId(targetId, memberId);
    }
}
