package org.okky.like.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigInteger;

@AllArgsConstructor
@Getter
public class MyEmotionStatSummaryDto {
    String id;
    int totalEmotionCount;
    int likeCount;
    int funCount;
    int thanksCount;
    int sadCount;
    int angryCount;

    public MyEmotionStatSummaryDto(Object[] tuple) {
        // TODO: 2018. 6. 18. @SqlResultSetMapping이 원하는 대로 작동하지 않아 부득이하게 저수준으로 작업함. 자세한 사항은 58ab0f7bfb775d2e2dd960d4058fb9c1045c79b9 참고.
        this.id = (String) tuple[0];
        this.totalEmotionCount = ((BigInteger) tuple[1]).intValue();
        this.likeCount = ((BigInteger) tuple[2]).intValue();
        this.funCount = ((BigInteger) tuple[3]).intValue();
        this.thanksCount = ((BigInteger) tuple[4]).intValue();
        this.sadCount = ((BigInteger) tuple[5]).intValue();
        this.angryCount = ((BigInteger) tuple[6]).intValue();
    }
}
