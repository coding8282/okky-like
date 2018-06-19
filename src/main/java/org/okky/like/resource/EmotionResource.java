package org.okky.like.resource;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.like.application.EmotionApplicationService;
import org.okky.like.application.EmotionQueryService;
import org.okky.like.resource.dto.EmotionFullStatDto;
import org.okky.like.resource.dto.EmotionStatDto;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
class EmotionResource {
    EmotionApplicationService applicationService;
    EmotionQueryService queryService;

    @GetMapping(value = "/articles/{articleId}/emotion", produces = APPLICATION_JSON_VALUE)
    EmotionStatDto getEmotionStat(
            @PathVariable String articleId) {
        return queryService.findEmotionStat(articleId);
    }

    @GetMapping(value = "/articles/{articleId}/members/{memberId}/emotion", produces = APPLICATION_JSON_VALUE)
    EmotionFullStatDto getEmotionFullStat(
            @PathVariable String articleId,
            @PathVariable String memberId) {
        return queryService.findEmotionFullStat(articleId, memberId);
    }

    @PutMapping(value = "/articles/{articleId}/members/{memberId}/emotions", produces = APPLICATION_JSON_VALUE)
    EmotionStatDto doEmotion(
            @PathVariable String articleId,
            @PathVariable String memberId,
            @RequestParam String emotion) {
        applicationService.doEmotion(articleId, memberId, emotion);
        return queryService.findEmotionFullStat(articleId, memberId);
    }

    @DeleteMapping(value = "/articles/{articleId}/members/{memberId}/emotions", produces = APPLICATION_JSON_VALUE)
    EmotionStatDto undoEmotion(
            @PathVariable String articleId,
            @PathVariable String memberId) {
        applicationService.undoEmotion(articleId, memberId);
        return queryService.findEmotionFullStat(articleId, memberId);
    }
}
