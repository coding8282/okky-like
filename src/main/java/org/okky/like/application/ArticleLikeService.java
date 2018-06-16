package org.okky.like.application;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.like.domain.model.ArticleLike;
import org.okky.like.domain.repository.ArticleLikeRepository;
import org.okky.like.domain.service.ArticleLikeConstraint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ArticleLikeService {
    ArticleLikeRepository repository;
    ArticleLikeConstraint constraint;

    public void toggleLike(String articleId, String likerId) {
        ArticleLike like = repository.find(articleId, likerId).orElse(null);
        if (like == null)
            like(articleId, likerId);
        else
            unlike(like);
    }

    // ------------------------------------------
    private void like(String articleId, String likerId) {
        constraint.checkArticleExists(articleId);
        ArticleLike newLike = new ArticleLike(articleId, likerId);
        repository.save(newLike);
    }

    private void unlike(ArticleLike like) {
        repository.delete(like);
    }
}
