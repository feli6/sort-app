package com.brn.sort.service;


import com.brn.sort.service.db.entity.SortResult;
import com.brn.sort.service.db.entity.SortResultDao;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SortingServiceTest {

    private final Sorter sorter = mock(Sorter.class);

    private final SortResultDao dao = mock(SortResultDao.class);

    private final SortingService target = new SortingService(sorter, dao);

    @Test
    public void shouldSortNumbersInAscendingOrder() {
        when(sorter.sort(any())).thenReturn(new Sorter.SorterResult(new int[]{1,2,3,4,5}, 1, 2));
        when(dao.create(any())).thenReturn(new SortResult("1,2,3", "2,3,4", 1,2));
        SortResult result = target.sortNumbers(new int[]{2, 3, 4, 5, 6});
        assertThat(result).isNotNull();
    }

}