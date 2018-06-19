package org.okky.like.domain.repository;

import org.okky.like.domain.model.Emotion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import static org.okky.like.domain.model.Emotion.GET_EMOTION_STAT;
import static org.okky.like.domain.model.Emotion.GET_MY_EMOTION_STAT;

@RepositoryDefinition(domainClass = Emotion.class, idClass = String.class)
public interface EmotionRepository {
    void save(Emotion emotion);
    void saveAndFlush(Emotion emotion);
    Optional<Emotion> findByTargetIdAndMemberId(String targetId, String memberId);
    @Query("select e.type from Emotion e where e.targetId=:targetId and e.memberId=:memberId")
    String findEmotionType(@Param("targetId") String targetId, @Param("memberId") String memberId);
    void deleteByTargetIdAndMemberId(String targetId, String memberId);
    boolean existsByTargetIdAndMemberId(String targetId, String memberId);

    @Query(name = GET_EMOTION_STAT, nativeQuery = true)
    Object queryEmotionStat(@Param("targetId") String targetId);
    @Query(name = GET_MY_EMOTION_STAT, nativeQuery = true)
    Object queryMyEmotionStat(@Param("memberId") String memberId);
}
