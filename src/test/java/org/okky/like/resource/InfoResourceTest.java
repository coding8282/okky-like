package org.okky.like.resource;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.okky.like.TestMother;
import org.okky.like.application.EmotionApplicationService;
import org.okky.like.application.EmotionQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static lombok.AccessLevel.PRIVATE;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = InfoResource.class)
@FieldDefaults(level = PRIVATE)
public class InfoResourceTest extends TestMother {
    @Autowired
    MockMvc mvc;
    @MockBean
    ClaimInterceptor claimInterceptor;
    @MockBean
    EmotionQueryService EmotionQueryService;
    @MockBean
    EmotionApplicationService EmotionApplicationService;

    @Test
    public void name() {
        // Empty
    }
}