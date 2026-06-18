package com.JohnBieniek.Java26Demo.utility;

import java.util.ArrayList;
import java.util.List;

public final class Java21ProcessingUtils {
    private static final int SEGMENT_SIZE = 10_000;
    private static final String LOREM_IPSUM = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt
            ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
            laboris nisi ut aliquip ex ea commodo consequat.
            """.repeat(250);

    private Java21ProcessingUtils() {}

    public static long countWords(String segment) {
        return segment.isBlank() ? 0 : segment.trim().split("\\s+").length;
    }

    public static void simulateBlockingValidation() throws InterruptedException {
        Thread.sleep(10);
    }

    public static String useDefaultInputIfBlank(String input) {
        return input == null || input.isBlank() ? LOREM_IPSUM : input;
    }

    public static List<String> splitIntoSegments(String input) {
        if (input.isEmpty()) {
            return List.of("");
        }

        List<String> segments = new ArrayList<>();
        int start = 0;
        while (start < input.length()) {
            int end = Math.min(start + SEGMENT_SIZE, input.length());
            while (end < input.length() && !Character.isWhitespace(input.charAt(end))) {
                end++;
            }
            segments.add(input.substring(start, end));
            start = end;
        }
        return segments;
    }
}
