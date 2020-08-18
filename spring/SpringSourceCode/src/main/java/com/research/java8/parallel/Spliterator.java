package com.research.java8.parallel;

import sun.plugin2.os.windows.SECURITY_ATTRIBUTES;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Spliterator {
    static final String SENTENCE = " Nel mezzo del cammin di nostra vita " +
            "mi ritrovai in una selva oscura" +
            " ché la dritta via era smarrita ";

    public static void main(String[] args) {
        System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");
        Stream<Character> characterStream = IntStream.range(0, SENTENCE.length()).mapToObj(i -> {
            Character c = SENTENCE.charAt(i);
            return c;
        });
        characterStream = IntStream.range(0, SENTENCE.length()).mapToObj(SENTENCE::charAt);

//        WordCounter reduce = characterStream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
//        System.out.println(reduce.getCounter());

        // 存在线程安全问题
        WordCounter reduce = characterStream.parallel().reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
        System.out.println(reduce.getCounter());

        java.util.Spliterator<Character> wordCounterSpliterator = new WordCounterSpliterator(SENTENCE);
        Stream<Character> stream = StreamSupport.stream(wordCounterSpliterator, true);
        System.out.println("Found " + countWords(stream) + " words");
    }

    private static int countWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
                WordCounter::accumulate,
                WordCounter::combine);
        return wordCounter.getCounter();
    }

    public static int countWordsIteratively(String words) {
        int count = 0;
        boolean isSpace = true;
        for (char c : words.toCharArray()) {
            if (Character.isWhitespace(c)) {
                isSpace = true;
            } else {
                if (isSpace) count++;
                isSpace = false;
            }
        }
        return count;
    }



}
