package me.espere.feelings.impl.dictionary;

import me.espere.feelings.impl.commons.Conditions;
import me.espere.feelings.spec.dictionary.VadEntry;
import me.espere.feelings.spec.dictionary.VadValue;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryCsvVadDictionaryTest {
    private InMemoryCsvVadDictionary dictionary;

    @Before
    public void setUp() throws Exception {
        dictionary = new InMemoryCsvVadDictionary("vad-dictionary.test.csv");
    }

    @Test
    public void shouldGetVadDictionaryEntry() {
        Optional<VadEntry> optionalEntry = dictionary.getEntry("abnormal");

        assertThat(optionalEntry).isPresent();

        VadEntry entry = optionalEntry.get();

        assertThat(entry.getWord()).isEqualToIgnoringCase("abnormal");

        VadValue vadValue = entry.getVadValue();
        assertThat(vadValue.getValence()).is(Conditions.equalTo(3.53));
        assertThat(vadValue.getArousal()).is(Conditions.equalTo(4.48));
        assertThat(vadValue.getDominance()).is(Conditions.equalTo(4.70));
    }

    @Test
    public void shouldReturnEmptyOptionalWhenWordIsNotPresent() {
        Optional<VadEntry> optionalEntry = dictionary.getEntry("normal");

        assertThat(optionalEntry).isNotPresent();
    }
}
