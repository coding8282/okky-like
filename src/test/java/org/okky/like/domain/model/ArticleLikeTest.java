package org.okky.like.domain.model;

import org.junit.Test;
import org.okky.like.TestMother;
import org.okky.share.execption.BadArgument;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;

public class ArticleLikeTest extends TestMother {
    @Test
    public void id는_al로_시작() {
        ArticleLike like = new ArticleLike("a-1", "m-1");

        assertThat(like.getId(), startsWith("al-"));
    }

    @Test
    public void articleId는_필수() {
        expect(BadArgument.class, "게시글 id는 필수입니다.");

        new ArticleLike(null, "m-1");
    }

    @Test
    public void likerId는_필수() {
        expect(BadArgument.class, "좋아요한사람 id는 필수입니다.");

        new ArticleLike("a-1", null);
    }
}