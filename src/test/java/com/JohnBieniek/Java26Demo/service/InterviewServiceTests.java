package com.JohnBieniek.Java26Demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class InterviewServiceTests {
    @Test
    void numberOfIslandsUsesDepthFirstTraversal() {
        char[][] grid = {
                {'1', '1', '0'},
                {'0', '1', '0'},
                {'1', '0', '1'}
        };

        assertThat(new InterviewService().numberOfIslands(grid))
                .endsWith("Number of Islands: 3");
    }
}
