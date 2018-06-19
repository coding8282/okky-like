package org.okky.like.domain.repository;

import org.okky.like.domain.model.Emotion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import static org.okky.like.domain.model.Emotion.GET_EMOTION_QUERY;

@RepositoryDefinition(domainClass = Emotion.class, idClass = String.class)
public interface EmotionRepository {
    void save(Emotion emotion);
    Optional<Emotion> findByTargetIdAndMemberId(String targetId, String memberId);
    @Query("select e.type from Emotion e where e.targetId=:targetId and e.memberId=:memberId")
    String findEmotionType(@Param("targetId") String targetId, @Param("memberId") String memberId);
    void deleteByTargetIdAndMemberId(String targetId, String memberId);
    boolean existsByTargetIdAndMemberId(String targetId, String memberId);
    @Query(nativeQuery = true, name = GET_EMOTION_QUERY)
    Object queryEmotionStatByTargetId(@Param("targetId") String targetId);
}
