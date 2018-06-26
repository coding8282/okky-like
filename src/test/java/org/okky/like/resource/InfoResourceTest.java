package org.okky.like.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.okky.like.TestMother;
import org.okky.like.application.EmotionApplicationService;
import org.okky.like.application.EmotionQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = InfoResource.class)
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
    public void name() throws Exception {
        mvc.perform(get("/").content("message").with(user("admin1").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string("aaa"))
                .andDo(print());
        System.out.println(mvc);
    }

    @Test
    @WithMockUser
    public void name2() throws Exception {
        when(EmotionQueryService.queryEmotionFullStat("1", "2")).thenReturn(null);

        mvc.perform(get("/articles/1/emotions?memberId=2").contentType(MediaType.TEXT_PLAIN).content("message"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("okky-like service333")));
    }
}