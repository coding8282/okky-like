package org.okky.like.resource.dto;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;

import static java.math.BigInteger.valueOf;
import static lombok.AccessLevel.PRIVATE;
import static org.okky.share.util.JsonUtil.toPrettyJson;

@FieldDefaults(level = PRIVATE)
@Getter
public class EmotionStatSummaryDto {
    String myEmotionType;
    String id;
    int totalEmotionCount;
    int likeCount;
    int funCount;
    int thanksCount;
    int sadCount;
    int angryCount;

    public EmotionStatSummaryDto(Object[] tuple, String myEmotionType) {
        // TODO: 2018. 6. 18. @SqlResultSetMapping이 원하는 대로 작동하지 않아 부득이하게 저수준으로 작업함. 자세한 사항은 58ab0f7bfb775d2e2dd960d4058fb9c1045c79b9 참고.
        this.id = (String) tuple[0];
        this.totalEmotionCount = ((BigInteger) tuple[1]).intValue();
        this.likeCount = ((BigInteger) tuple[2]).intValue();
        this.funCount = ((BigInteger) tuple[3]).intValue();
        this.thanksCount = ((BigInteger) tuple[4]).intValue();
        this.sadCount = ((BigInteger) tuple[5]).intValue();
        this.angryCount = ((BigInteger) tuple[6]).intValue();
        this.myEmotionType = myEmotionType;
    }

    // ---------------------------------------------------
    public static void main(String[] args) {
        System.out.println(toPrettyJson(sample()));
    }

    public static EmotionStatSummaryDto sample() {
        Object[] tuple = {"1", valueOf(150), valueOf(140), valueOf(10), valueOf(0), valueOf(0), valueOf(0)};
        String emotionType = "LIKE";
        return new EmotionStatSummaryDto(tuple, emotionType);
    }
}
