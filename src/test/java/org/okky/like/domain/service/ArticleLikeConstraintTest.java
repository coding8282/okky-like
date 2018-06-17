package org.okky.like.domain.service;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.like.TestMother;
import org.okky.share.execption.ExternalServiceError;
import org.okky.share.execption.ModelNotExists;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import static lombok.AccessLevel.PRIVATE;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = PRIVATE)
public class ArticleLikeConstraintTest extends TestMother {
    @InjectMocks
    ArticleLikeConstraint constraint;
    @Mock
    RestTemplate template;
    @Mock
    ResponseEntity entity;
    @Mock
    HttpStatusCodeException externalError;

    @Test
    public void checkArticleExists_해당_게시글이_있는_경우() {
        ResponseEntity entity = mock(ResponseEntity.class);
        when(entity.getBody()).thenReturn(true);
        when(template.getForEntity(anyString(), eq(Boolean.class))).thenReturn(entity);

        constraint.checkArticleExists("a-1");

        InOrder o = inOrder(template);
        o.verify(template).getForEntity(eq("/articles/a-1/exists"), eq(Boolean.class));
    }

    @Test
    public void checkArticleExists_해당_게시글이_없는_경우() {
        expect(ModelNotExists.class, "해당 게시글은 존재하지 않기 때문에 좋아요를 할 수 없습니다: 'a-1'");

        when(entity.getBody()).thenReturn(false);
        when(template.getForEntity(anyString(), eq(Boolean.class))).thenReturn(entity);

        constraint.checkArticleExists("a-1");

        InOrder o = inOrder(template);
        o.verify(template).getForEntity(eq("/articles/a-1/exists"), eq(Boolean.class));
    }

    @Test
    public void checkArticleExists_요청_중_오류가_발생한_경우() {
        expect(ExternalServiceError.class);

        when(externalError.getResponseBodyAsByteArray()).thenReturn(new byte[]{1, 2, 3});
        when(template.getForEntity(anyString(), eq(Boolean.class))).thenThrow(externalError);

        constraint.checkArticleExists("a-1");

        InOrder o = inOrder(template, entity, externalError);
        o.verify(template).getForEntity(eq("/articles/a-1/exists"), eq(Boolean.class));
        o.verify(entity).getBody();
        o.verify(externalError).getResponseBodyAsByteArray();
    }
}