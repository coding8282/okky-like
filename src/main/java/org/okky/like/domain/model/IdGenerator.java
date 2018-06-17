package org.okky.like.domain.model;

import java.util.UUID;

interface IdGenerator {
    static String newEmotionId() {
        return "em-" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 25);
    }
}
