package org.okky.like.domain.service;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.share.execption.ExternalServiceError;
import org.okky.share.execption.ModelNotExists;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class EmotionConstraint {
    RestTemplate template;

    public void rejectIfArticleNotExists(String articleId) {
        try {
            ResponseEntity<Boolean> result = template.getForEntity(format("/articles/%s/exists", articleId), Boolean.class);
            if (!result.getBody())
                throw new ModelNotExists(format("해당 게시글은 존재하지 않기 때문에 공감표현을 할 수 없습니다: '%s'", articleId));
        } catch (HttpStatusCodeException e) {
            throw new ExternalServiceError(e.getResponseBodyAsByteArray());
        }
    }
}
