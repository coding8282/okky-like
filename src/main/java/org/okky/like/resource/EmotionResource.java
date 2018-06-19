package org.okky.like.resource;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.like.application.EmotionApplicationService;
import org.okky.like.application.EmotionQueryService;
import org.okky.like.resource.dto.EmotionFullStatDto;
import org.okky.like.resource.dto.MyEmotionStatDto;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
class EmotionResource {
    EmotionApplicationService applicationService;
    EmotionQueryService queryService;

    @GetMapping(value = "/members/{memberId}/emotions", produces = APPLICATION_JSON_VALUE)
    MyEmotionStatDto getMyEmotionStat(@PathVariable String memberId) {
        return queryService.queryMyEmotionStat(memberId);
    }

    @GetMapping(value = "/articles/{articleId}/emotions", produces = APPLICATION_JSON_VALUE)
    EmotionFullStatDto getEmotionStat(
            @PathVariable String articleId,
            @RequestParam(required = false) String memberId) {
        return queryService.queryEmotionFullStat(articleId, memberId);
    }

    @PutMapping(value = "/articles/{articleId}/members/{memberId}/emotions/{emotion}", produces = APPLICATION_JSON_VALUE)
    EmotionFullStatDto doEmotion(
            @PathVariable String articleId,
            @PathVariable String memberId,
            @PathVariable String emotion) {
        applicationService.doEmotion(articleId, memberId, emotion);
        return queryService.queryEmotionFullStat(articleId, memberId);
    }

    @DeleteMapping(value = "/articles/{articleId}/members/{memberId}/emotions", produces = APPLICATION_JSON_VALUE)
    EmotionFullStatDto undoEmotion(
            @PathVariable String articleId,
            @PathVariable String memberId) {
        applicationService.undoEmotion(articleId, memberId);
        return queryService.queryEmotionFullStat(articleId, memberId);
    }
}
