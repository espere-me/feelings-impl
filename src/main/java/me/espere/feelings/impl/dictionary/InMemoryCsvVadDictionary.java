package me.espere.feelings.impl.dictionary;

import me.espere.feelings.spec.dictionary.VadDictionary;
import me.espere.feelings.spec.dictionary.VadEntry;
import me.espere.feelings.spec.dictionary.VadValue;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class InMemoryCsvVadDictionary implements VadDictionary {
    private static final int HEADER_RECORD_NUMBER = 1;

    private static final int WORD_RECORD_INDEX = 1;
    private static final int VALENCE_RECORD_INDEX = 2;
    private static final int AROUSAL_RECORD_INDEX = 5;
    private static final int DOMINANCE_RECORD_INDEX = 8;

    private Collection<VadEntry> entries;

    public InMemoryCsvVadDictionary(String dictionaryResourceName) throws IOException {
        Reader reader = loadFileIntoReader(dictionaryResourceName);

        CSVParser csvParser = CSVFormat.DEFAULT.parse(reader);

        entries = csvParser
                .getRecords()
                .stream()
                .filter(record ->
                        record.getRecordNumber() != HEADER_RECORD_NUMBER)
                .map(record -> {
                    String word = record.get(WORD_RECORD_INDEX);

                    VadValue vadValue = new VadValue(
                            new BigDecimal(record.get(VALENCE_RECORD_INDEX)),
                            new BigDecimal(record.get(AROUSAL_RECORD_INDEX)),
                            new BigDecimal(record.get(DOMINANCE_RECORD_INDEX))
                    );

                    return new VadEntry(word, vadValue);
                })
                .collect(toList());
    }

    @Override
    public Optional<VadEntry> getEntry(String word) {
        return entries
                .stream()
                .filter(entry ->
                        entry.getWord().equalsIgnoreCase(word))
                .findFirst();
    }

    private InputStreamReader loadFileIntoReader(String dictionaryResourceName) {
        InputStream inputStream =
                getClass()
                        .getClassLoader()
                        .getResourceAsStream(dictionaryResourceName);

        return new InputStreamReader(inputStream);
    }
}
