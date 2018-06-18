package org.okky.like.domain.repository;

import org.okky.like.domain.model.Emotion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import static org.okky.like.domain.model.Emotion.GET_EMOTION_QUERY;

@RepositoryDefinition(domainClass = Emotion.class, idClass = String.class)
public interface EmotionRepository {
    boolean existsByTargetIdAndMemberId(String targetId, String memberId);
    void save(Emotion emotion);
    Optional<Emotion> findByTargetIdAndMemberId(String targetId, String memberId);
    @Query(nativeQuery = true, name = GET_EMOTION_QUERY)
    Object findEmotionStatByTargetId(@Param("targetId") String targetId);
    void deleteByTargetIdAndMemberId(String targetId, String memberId);

    default boolean wasAlreadyEmoted(String targetId, String memberId) {
        return existsByTargetIdAndMemberId(targetId, memberId);
    }
    default String findEmotionType(String targetId, String memberId) {
        Emotion emotion = findByTargetIdAndMemberId(targetId, memberId).orElse(null);
        if (emotion == null) {
            return null;
        } else {
            return emotion.getType().name();
        }
    }
}
