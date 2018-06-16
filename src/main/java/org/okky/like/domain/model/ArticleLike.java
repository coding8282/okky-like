package org.okky.like.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.okky.share.domain.Aggregate;
import org.okky.share.domain.AssertionConcern;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "U_ARTICLE_ID_LIKER_ID",
                columnNames = {"ARTICLE_ID", "LIKER_ID"})
})
public class ArticleLike implements Aggregate {
    @Id
    @Column(length = 50)
    private String id;

    @Column(name = "ARTICLE_ID", nullable = false, length = 50)
    private String articleId;

    @Column(name = "LIKER_ID", nullable = false, length = 50)
    private String likerId;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "BIGINT UNSIGNED")
    private long likedOn;

    public ArticleLike(String articleId, String likerId) {
        setId("al-" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15));
        setArticleId(articleId);
        setLikerId(likerId);
    }

    // -----------------------------------------------------
    public static ArticleLike sample() {
        String articleId = "article-id";
        String likerId = "m-30044";
        return new ArticleLike(articleId, likerId);
    }

    public static void main(String[] args) {
        System.out.println(sample());
    }

    private void setId(String id) {
        AssertionConcern.assertArgNotNull(id, "id는 필수입니다.");
        this.id = id;
    }

    private void setArticleId(String articleId) {
        AssertionConcern.assertArgNotNull(articleId, "게시글 id는 필수입니다.");
        this.articleId = articleId;
    }

    private void setLikerId(String likerId) {
        AssertionConcern.assertArgNotNull(likerId, "좋아요한사람은 필수입니다.");
        this.likerId = likerId;
    }
}
