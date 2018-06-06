package org.okky.like.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.okky.share.execption.ExternalServiceError;
import org.okky.share.execption.ModelNotExists;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class ArticleLikeConstraint {
    private RestTemplate template;

    public void checkArticleExists(String articleId) {
        try {
            ResponseEntity<Boolean> result = template.getForEntity(format("/articles/%s/exists", articleId), Boolean.class);
            if (!result.getBody())
                throw new ModelNotExists(format("해당 게시글은 존재하지 않기 때문에 좋아요를 할 수 없습니다: '%s'", articleId));
        } catch (HttpStatusCodeException e) {
            throw new ExternalServiceError(e.getResponseBodyAsByteArray());
        }
    }
}
