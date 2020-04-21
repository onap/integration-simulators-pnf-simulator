package org.onap.pnfsimulator.event;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventDataTest {

    @Test
    public void shouldReturnProperString() {
        EventData eventData = new EventData("id", "template", "patched", "input", "keywords", 1);
        String expected = "EventData{"
                + "id='id'" +
                ", template='template'" +
                ", patched='patched'" +
                ", input='input'"
                + ", keywords='keywords'"
                + '}';
        assertEquals(expected, eventData.toString());
    }
}
