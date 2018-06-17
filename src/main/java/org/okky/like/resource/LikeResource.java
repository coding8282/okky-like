package org.okky.like.resource;

import lombok.AllArgsConstructor;
import org.okky.like.application.ArticleLikeService;
import org.okky.like.domain.repository.ArticleLikeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
class LikeResource {
    ArticleLikeService service;
    ArticleLikeRepository repository;

    @GetMapping(value = "/members/{memberId}/likes/count")
    long countMyLikes(@PathVariable String memberId) {
        return repository.countByLikerId(memberId);
    }

    @GetMapping(value = "/articles/{articleIds}/likes/count", produces = APPLICATION_JSON_VALUE)
    List<Long> count(@PathVariable List<String> articleIds) {
        return articleIds
                .stream()
                .map(repository::countByArticleId)
                .collect(toList());
    }

    @GetMapping(value = "/articles/{articleIds}/likers/{likerId}/liked", produces = APPLICATION_JSON_VALUE)
    List<Boolean> findLiked(
            @PathVariable List<String> articleIds,
            @PathVariable String likerId) {
        return articleIds
                .stream()
                .map(articleId -> repository.existsByArticleIdAndLikerId(articleId, likerId))
                .collect(toList());
    }

    @PutMapping(value = "/articles/{articleId}/likes/toggle")
    long toggleLike(
            @PathVariable String articleId) {
        service.toggleLike(articleId, ContextHelper.getId());
        return repository.countByArticleId(articleId);
    }
}
