package solutions.moot.feelings.impl.lemmatizer;

import edu.stanford.nlp.simple.Sentence;
import solutions.moot.feelings.spec.lemmatizer.Lemmatizer;

import java.util.Collection;
import java.util.Properties;

public class NlpCoreBasedLemmatizer implements Lemmatizer {
    @Override
    public Collection<String> lemmas(String sentence) {
        Properties configurationProperties = new Properties();
        configurationProperties.setProperty("pos.model", "pos.tagger");
        return new Sentence(sentence).lemmas(configurationProperties);
    }
}