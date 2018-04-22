package me.espere.feelings.impl.dictionary;

import me.espere.feelings.spec.VadValue;
import me.espere.feelings.spec.dictionary.Dictionary;
import me.espere.feelings.spec.dictionary.Entry;
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

public class InMemoryCsvVadDictionary implements Dictionary {
    private static final String DEFAULT_VAD_DICTIONARY_CSV = "vad-dictionary.csv";

    private static final int HEADER_RECORD_NUMBER = 1;

    private static final int WORD_RECORD_INDEX = 1;
    private static final int VALENCE_RECORD_INDEX = 2;
    private static final int AROUSAL_RECORD_INDEX = 5;
    private static final int DOMINANCE_RECORD_INDEX = 8;

    private Collection<Entry> entries;

    public InMemoryCsvVadDictionary() throws IOException {
        loadEntries(DEFAULT_VAD_DICTIONARY_CSV);
    }

    public InMemoryCsvVadDictionary(String dictionaryResourceName) throws IOException {
        loadEntries(dictionaryResourceName);
    }

    private void loadEntries(String dictionaryResourceName) throws IOException {
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

                    return new Entry(word, vadValue);
                })
                .collect(toList());
    }

    private InputStreamReader loadFileIntoReader(String dictionaryResourceName) {
        InputStream inputStream =
                getClass()
                        .getClassLoader()
                        .getResourceAsStream(dictionaryResourceName);

        return new InputStreamReader(inputStream);
    }

    @Override
    public Optional<Entry> getEntry(String word) {
        return entries
                .stream()
                .filter(entry ->
                        entry.getWord().equalsIgnoreCase(word))
                .findFirst();
    }

    @Override
    public VadValue getMeanVadValue() {
        return new VadValue(
                BigDecimal.valueOf(5.06),
                BigDecimal.valueOf(4.21),
                BigDecimal.valueOf(5.18)
        );
    }

    @Override
    public VadValue getMinVadValue() {
        return new VadValue(
                BigDecimal.valueOf(1.26),
                BigDecimal.valueOf(1.60),
                BigDecimal.valueOf(1.68)
        );
    }

    @Override
    public VadValue getMaxVadValue() {
        return new VadValue(
                BigDecimal.valueOf(8.53),
                BigDecimal.valueOf(7.79),
                BigDecimal.valueOf(7.90)
        );
    }
}
