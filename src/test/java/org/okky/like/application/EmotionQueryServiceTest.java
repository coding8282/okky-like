package org.okky.like.application;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.like.TestMother;
import org.okky.like.domain.repository.EmotionRepository;
import org.okky.like.resource.dto.EmotionStatSummaryDto;
import org.okky.like.resource.dto.MyEmotionStatSummaryDto;

import static java.math.BigInteger.valueOf;
import static lombok.AccessLevel.PRIVATE;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = PRIVATE)
public class EmotionQueryServiceTest extends TestMother {
    @InjectMocks
    EmotionQueryService service;
    @Mock
    EmotionRepository repository;

    @Test
    public void queryMyEmotionStat() {
        Object[] tuple = {"1", valueOf(150), valueOf(140), valueOf(10), valueOf(0), valueOf(0), valueOf(0)};
        when(repository.queryMyEmotionStat("m1")).thenReturn(tuple);

        MyEmotionStatSummaryDto dto = service.queryMyEmotionStat("m1");

        assertNotNull(dto);

        InOrder o = inOrder(repository);
        o.verify(repository).queryMyEmotionStat("m1");
    }

    @Test
    public void queryEmotionFullStat() {
        Object[] tuple = {"1", valueOf(150), valueOf(140), valueOf(10), valueOf(0), valueOf(0), valueOf(0)};
        when(repository.queryEmotionStat("t1")).thenReturn(tuple);
        when(repository.findEmotionType("t1", "m1")).thenReturn("LIKE");

        EmotionStatSummaryDto dto = service.queryEmotionFullStat("t1", "m1");

        assertNotNull(dto);

        InOrder o = inOrder(repository);
        o.verify(repository).queryEmotionStat("t1");
        o.verify(repository).findEmotionType("t1", "m1");
    }
}