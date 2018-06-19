package org.okky.like.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.share.domain.Aggregate;

import javax.persistence.*;

import static java.lang.System.currentTimeMillis;
import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;
import static org.okky.like.domain.model.EmotionType.LIKE;
import static org.okky.share.domain.AssertionConcern.assertArgNotEmpty;
import static org.okky.share.domain.AssertionConcern.assertArgNotNull;
import static org.okky.share.util.JsonUtil.toPrettyJson;

/**
 * targetId는 게시글, 답글, 코멘트, 회원 프로필 등 어느 것이라도 그 대상이 될 수 있다.
 *
 * @author coding8282
 */
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = PRIVATE)
@Getter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "U_TARGET_ID_MEMBER_ID",
                columnNames = {"TARGET_ID", "MEMBER_ID"})
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name = Emotion.GET_EMOTION_STAT,
                query = "select \n" +
                        "  :targetId as ID, \n" +
                        "  (select count(*) from emotion e where e.target_id=:targetId) AS TOTAL_EMOTION_COUNT, \n" +
                        "  (select count(*) from emotion e where e.target_id=:targetId and e.type='LIKE') AS LIKE_COUNT, \n" +
                        "  (select count(*) from emotion e where e.target_id=:targetId and e.type='FUN') AS FUN_COUNT, \n" +
                        "  (select count(*) from emotion e where e.target_id=:targetId and e.type='THANKS') AS THANKS_COUNT, \n" +
                        "  (select count(*) from emotion e where e.target_id=:targetId and e.type='SAD') AS SAD_COUNT, \n" +
                        "  (select count(*) from emotion e where e.target_id=:targetId and e.type='ANGRY') AS ANGRY_COUNT "
        ),
        @NamedNativeQuery(
                name = Emotion.GET_MY_EMOTION_STAT,
                query = "select \n" +
                        "  :memberId as ID, \n" +
                        "  (select count(*) from emotion e where e.member_id=:memberId) AS TOTAL_EMOTION_COUNT, \n" +
                        "  (select count(*) from emotion e where e.member_id=:memberId and e.type='LIKE') AS LIKE_COUNT, \n" +
                        "  (select count(*) from emotion e where e.member_id=:memberId and e.type='FUN') AS FUN_COUNT, \n" +
                        "  (select count(*) from emotion e where e.member_id=:memberId and e.type='THANKS') AS THANKS_COUNT, \n" +
                        "  (select count(*) from emotion e where e.member_id=:memberId and e.type='SAD') AS SAD_COUNT, \n" +
                        "  (select count(*) from emotion e where e.member_id=:memberId and e.type='ANGRY') AS ANGRY_COUNT "
        )

})
public class Emotion implements Aggregate {
    public static final String GET_EMOTION_STAT = "Emotion.queryEmotionStat";
    public static final String GET_MY_EMOTION_STAT = "Emotion.queryMyEmotionStat";

    @Id
    @Column(length = 50)
    String id;

    @Column(name = "TARGET_ID", nullable = false, length = 50)
    String targetId;

    @Column(name = "MEMBER_ID", nullable = false, length = 50)
    String memberId;

    @Column(nullable = false, updatable = false, columnDefinition = "BIGINT UNSIGNED")
    long emotedOn;

    @Column(nullable = false, length = 10)
    @Enumerated(STRING)
    EmotionType type;

    public Emotion(String targetId, String memberId, EmotionType type) {
        setId(IdGenerator.newEmotionId());
        setTargetId(targetId);
        setMemberId(memberId);
        setEmotedOn(currentTimeMillis());
        setType(type);
    }

    public static Emotion sample() {
        String targetId = "a-1";
        String memberId = "m-3";
        EmotionType type = LIKE;
        return new Emotion(targetId, memberId, type);
    }

    public static void main(String[] args) {
        System.out.println(toPrettyJson(sample()));
    }

    public void replaceEmotionType(EmotionType newType) {
        setType(newType);
    }

    public boolean isSameEmotionType(EmotionType type) {
        return this.type == type;
    }

    public boolean isDifferentEmotionType(EmotionType type) {
        return this.type != type;
    }

    // -----------------------------------------------------------
    private void setId(String id) {
        assertArgNotEmpty(id, "id는 필수입니다.");
        this.id = id;
    }

    private void setTargetId(String targetId) {
        assertArgNotEmpty(targetId, "공감표현 대상 id는 필수입니다.");
        this.targetId = targetId;
    }

    private void setMemberId(String memberId) {
        assertArgNotEmpty(memberId, "회원 id는 필수입니다.");
        this.memberId = memberId;
    }

    private void setEmotedOn(long emotedOn) {
        this.emotedOn = emotedOn;
    }

    private void setType(EmotionType type) {
        assertArgNotNull(type, "공감표현은 필수입니다.");
        this.type = type;
    }
}
