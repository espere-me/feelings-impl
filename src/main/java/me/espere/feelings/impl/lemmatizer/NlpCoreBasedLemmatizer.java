package me.espere.feelings.impl.lemmatizer;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import me.espere.feelings.spec.lemmatizer.Lemma;
import me.espere.feelings.spec.lemmatizer.Lemmatizer;

import java.util.Collection;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class NlpCoreBasedLemmatizer implements Lemmatizer {
    @Override
    public Collection<Lemma> lemmas(String text) {
        StanfordCoreNLP pipeline = buildStanfordCoreNlpPipeline();

        Annotation annotatedDocument = pipeline.process(text);

        return getTextStream(annotatedDocument)
                .flatMap(toTokensStream())
                .map(token -> new Lemma(
                        token.word(),
                        token.lemma()
                ))
                .collect(toList());
    }

    private StanfordCoreNLP buildStanfordCoreNlpPipeline() {
        Properties configuration = new Properties();
        configuration.setProperty("pos.model", "pos.tagger");
        configuration.setProperty("annotators", "tokenize, ssplit, pos, lemma");

        return new StanfordCoreNLP(configuration);
    }

    private Stream<CoreMap> getTextStream(Annotation annotation) {
        return annotation.get(SentencesAnnotation.class).stream();
    }

    private Function<CoreMap, Stream<CoreLabel>> toTokensStream() {
        return s -> s.get(TokensAnnotation.class).stream();
    }
}
