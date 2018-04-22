package me.espere.feelings.impl.dictionary;

import me.espere.feelings.spec.VadValue;
import me.espere.feelings.spec.dictionary.Entry;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static me.espere.feelings.impl.commons.Conditions.equalTo;
import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryCsvVadDictionaryTest {
    private InMemoryCsvVadDictionary dictionary;

    @Before
    public void setUp() throws Exception {
        dictionary = new InMemoryCsvVadDictionary("vad-dictionary.test.csv");
    }

    @Test
    public void shouldReturnVadDictionaryEntry() {
        Optional<Entry> optionalEntry = dictionary.getEntry("abnormal");

        assertThat(optionalEntry).isPresent();

        Entry entry = optionalEntry.get();

        assertThat(entry.getWord()).isEqualToIgnoringCase("abnormal");

        VadValue vadValue = entry.getVadValue();
        assertThat(vadValue.getValence()).is(equalTo(3.53));
        assertThat(vadValue.getArousal()).is(equalTo(4.48));
        assertThat(vadValue.getDominance()).is(equalTo(4.70));
    }

    @Test
    public void shouldReturnEmptyOptionalWhenWordIsNotPresent() {
        Optional<Entry> optionalEntry = dictionary.getEntry("normal");

        assertThat(optionalEntry).isNotPresent();
    }

    @Test
    public void shouldReturnDictionaryMeanVadValue() {
        VadValue meanVadValue = dictionary.getMeanVadValue();

        assertThat(meanVadValue.getValence()).is(equalTo(5.06));
        assertThat(meanVadValue.getArousal()).is(equalTo(4.21));
        assertThat(meanVadValue.getDominance()).is(equalTo(5.18));
    }

    @Test
    public void shouldReturnDictionaryMinVadValue() {
        VadValue meanVadValue = dictionary.getMinVadValue();

        assertThat(meanVadValue.getValence()).is(equalTo(1.26));
        assertThat(meanVadValue.getArousal()).is(equalTo(1.60));
        assertThat(meanVadValue.getDominance()).is(equalTo(1.68));
    }

    @Test
    public void shouldReturnDictionaryMaxVadValue() {
        VadValue meanVadValue = dictionary.getMaxVadValue();

        assertThat(meanVadValue.getValence()).is(equalTo(8.53));
        assertThat(meanVadValue.getArousal()).is(equalTo(7.79));
        assertThat(meanVadValue.getDominance()).is(equalTo(7.90));
    }
}
