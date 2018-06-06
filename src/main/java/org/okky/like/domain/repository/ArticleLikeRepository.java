package org.okky.like.domain.repository;

import org.okky.like.domain.model.ArticleLike;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@RepositoryDefinition(domainClass = ArticleLike.class, idClass = String.class)
public interface ArticleLikeRepository {
    void save(ArticleLike like);
    @Query("from ArticleLike l " +
            "where l.articleId = :articleId " +
            "and l.likerId = :likerId ")
    Optional<ArticleLike> find(@Param("articleId") String articleId, @Param("likerId") String likerId);
    @Query("select (count(l)>0) " +
            "from ArticleLike l " +
            "where l.articleId=:articleId and l.likerId=:likerId")
    Boolean isLikedBy(@Param("articleId") String articleId, @Param("likerId") String likerId);
    long countByArticleId(String articleId);
    long countByLikerId(String likerId);
    void delete(ArticleLike like);
}
