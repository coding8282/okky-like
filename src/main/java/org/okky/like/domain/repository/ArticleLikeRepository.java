package org.okky.like.domain.repository;

import org.okky.like.domain.model.ArticleLike;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = ArticleLike.class, idClass = String.class)
public interface ArticleLikeRepository {
    void save(ArticleLike like);
    void saveAndFlush(ArticleLike like);
    Optional<ArticleLike> findByArticleIdAndLikerId(String articleId, String likerId);
    boolean existsByArticleIdAndLikerId(String articleId, String likerId);
    long countByArticleId(String articleId);
    long countByLikerId(String likerId);
    void delete(ArticleLike like);
}
