package org.onap.pnfsimulator.simulator.keywords;


import io.vavr.Tuple1;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class TwoParameterKeywordTest {
    @Test
    public void whenGivenKeywordShouldReturnTwoParameterKeywordObjectWithParsedValues() {
        //given
        final String expectedName = "TEST";
        final Integer expectedParam1 = 123;
        final Integer expectedParam2 = 456;

        String keyword = "#" + expectedName + "(" + expectedParam1 + "," + expectedParam2 + ")";

        //when
        Tuple1<TwoParameterKeyword> keywordTuple = TwoParameterKeyword.twoParameterKeyword(keyword);
        TwoParameterKeyword twoParameterKeyword = keywordTuple._1();

        //then
        assertEquals(twoParameterKeyword.getName(), expectedName);
        assertEquals(twoParameterKeyword.getAdditionalParameter1(), expectedParam1);
        assertEquals(twoParameterKeyword.getAdditionalParameter2(), expectedParam2);
    }
}