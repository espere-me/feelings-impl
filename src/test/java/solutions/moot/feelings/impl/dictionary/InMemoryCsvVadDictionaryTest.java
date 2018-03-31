package solutions.moot.feelings.impl.dictionary;

import org.junit.Before;
import org.junit.Test;
import solutions.moot.feelings.spec.dictionary.VadEntry;
import solutions.moot.feelings.spec.dictionary.VadValue;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static solutions.moot.feelings.impl.commons.Conditions.equalTo;

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
        assertThat(vadValue.getValence()).is(equalTo(3.53));
        assertThat(vadValue.getArousal()).is(equalTo(4.48));
        assertThat(vadValue.getDominance()).is(equalTo(4.70));
    }

    @Test
    public void shouldReturnEmptyOptionalWhenWordIsNotPresent() {
        Optional<VadEntry> optionalEntry = dictionary.getEntry("normal");

        assertThat(optionalEntry).isNotPresent();
    }
}
