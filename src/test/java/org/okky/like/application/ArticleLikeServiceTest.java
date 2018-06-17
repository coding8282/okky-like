package org.okky.like.application;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.like.TestMother;
import org.okky.like.domain.model.ArticleLike;
import org.okky.like.domain.repository.ArticleLikeRepository;
import org.okky.like.domain.service.ArticleLikeConstraint;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = PRIVATE)
public class ArticleLikeServiceTest extends TestMother {
    @InjectMocks
    ArticleLikeService service;
    @Mock
    ArticleLikeRepository repository;
    @Mock
    ArticleLikeConstraint constraint;
    @Mock
    ArticleLike like;

    @Test
    public void toggleLike_좋아요를_하지_않은_경우() {
        when(repository.findByArticleIdAndLikerId("a-1", "m-1")).thenReturn(Optional.empty());

        service.toggleLike("a-1", "m-1");

        InOrder o = inOrder(repository, constraint);
        o.verify(repository).findByArticleIdAndLikerId("a-1", "m-1");
        o.verify(constraint).checkArticleExists("a-1");
        o.verify(repository).save(isA(ArticleLike.class));
    }

    @Test
    public void toggleLike_좋아요를_이미_한_경우() {
        when(repository.findByArticleIdAndLikerId("a-1", "m-1")).thenReturn(Optional.of(like));

        service.toggleLike("a-1", "m-1");

        InOrder o = inOrder(repository, constraint);
        o.verify(repository).findByArticleIdAndLikerId("a-1", "m-1");
        o.verify(repository).delete(like);
    }
}