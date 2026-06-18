package com.JohnBieniek.Java26Demo.dto.java21;

public record LargeInputProcessingResult(
        int inputCharacters,
        int segmentsProcessed,
        long wordCount,
        int virtualThreadTasks,
        long processingMilliseconds) {
}
