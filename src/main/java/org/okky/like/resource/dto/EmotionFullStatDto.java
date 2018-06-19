package org.okky.like.resource.dto;

import lombok.Getter;

@Getter
public class EmotionFullStatDto extends EmotionStatDto {
    String myEmotionType;

    public EmotionFullStatDto(Object[] tuple, String myEmotionType) {
        super(tuple);
        this.myEmotionType = myEmotionType;
    }
}
