package org.okky.like.application;

import lombok.experimental.FieldDefaults;
import org.okky.like.domain.model.Emotion;
import org.okky.share.event.Emoted;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE)
class ModelMapper {
    Emoted toEvent(Emotion e) {
        return new Emoted(
                e.getId(),
                e.getTargetId(),
                e.getMemberId(),
                e.getEmotedOn(),
                e.getType().name()
        );
    }
}
