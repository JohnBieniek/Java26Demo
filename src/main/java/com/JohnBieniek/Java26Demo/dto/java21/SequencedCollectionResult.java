package com.JohnBieniek.Java26Demo.dto.java21;

import java.util.List;

public record SequencedCollectionResult(
        List<String> encounterOrder,
        List<String> reversedOrder,
        String firstElement,
        String lastElement) {
}
