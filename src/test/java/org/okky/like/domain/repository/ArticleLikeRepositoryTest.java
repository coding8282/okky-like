package org.okky.like.domain.repository;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.okky.like.TestMother;
import org.okky.like.domain.model.ArticleLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static lombok.AccessLevel.PRIVATE;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@FieldDefaults(level = PRIVATE)
public class ArticleLikeRepositoryTest extends TestMother {
    @Autowired
    ArticleLikeRepository repository;

    @Test
    public void findByArticleIdAndLikerId_있을_떄() {
        ArticleLike like1 = fixture("a-1", "m-1");
        ArticleLike like2 = fixture("a-2", "m-1");
        repository.save(like1);
        repository.save(like2);
        ArticleLike found = repository.findByArticleIdAndLikerId("a-1", "m-1").get();

        assertEquals("찾아진 것과 같지 않다.", like1, found);
    }

    @Test
    public void findByArticleIdAndLikerId_없을_떄() {
        ArticleLike like1 = fixture("a-1", "m-1");
        ArticleLike like2 = fixture("a-2", "m-1");
        repository.save(like1);
        repository.save(like2);
        ArticleLike found = repository.findByArticleIdAndLikerId("a-9", "m-9").orElse(null);

        assertNull("없으므로 null이 나와야 한다.", found);
    }

    @Test
    public void existsByArticleIdAndLikerId_있을_때() {
        ArticleLike like1 = fixture("a-1", "m-1");
        repository.save(like1);
        Boolean likedBy = repository.existsByArticleIdAndLikerId("a-1", "m-1");

        assertTrue("있으므로 true여야만 한다.", likedBy);
    }

    @Test
    public void existsByArticleIdAndLikerId_없을_때() {
        ArticleLike like1 = fixture("a-1", "m-1");
        repository.save(like1);
        Boolean likedBy = repository.existsByArticleIdAndLikerId("a-9", "m-1");

        assertFalse("없으므로 false여야만 한다.", likedBy);
    }

    // --------------------------------------
    private ArticleLike fixture(String articleId, String likerId) {
        return new ArticleLike(articleId, likerId);
    }
}