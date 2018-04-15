package me.espere.feelings.impl.lemmatizer;

import me.espere.feelings.spec.lemmatizer.Lemma;
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
        Collection<Lemma> lemmas = lemmatizer.lemmas("Our deliveries are consistent");
        assertThat(lemmas).contains(
                new Lemma("Our", "we"),
                new Lemma("are", "be"),
                new Lemma("deliveries", "delivery"),
                new Lemma("consistent", "consistent")
        );
    }
}
