package me.espere.feelings.impl.lemmatizer;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class NlpCoreBasedLemmatizerTest {
    private NlpCoreBasedLemmatizer lemmatizer;

    @Before
    public void setUp() {
        lemmatizer = new NlpCoreBasedLemmatizer();
    }

    @Test
    public void shouldLemmatizeSentence() {
        Collection<String> lemmas = lemmatizer.lemmas("Our deliveries are consistent");
        assertThat(lemmas).contains("we", "delivery", "be", "consistent");
    }
}
