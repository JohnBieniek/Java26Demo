package com.JohnBieniek.Java26Demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.JohnBieniek.Java26Demo.manager.InterviewManager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/interview")
@Tag(name = "Interview Controller", description = "Runnable algorithm exercises commonly discussed in technical interviews, including sliding windows, two pointers, duplicate detection, and depth-first traversal of a two-dimensional grid.")
public class InterviewController {
    private final InterviewManager interviewManager;

    public InterviewController(InterviewManager interviewManager) {
        this.interviewManager = interviewManager;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/largestNonRepeatingSubstring")
    @Operation(
        summary = "Longest string with no characters next to each other like aa in bcaadcb",
        description = "Uses a sliding window to itterate across the string and determine the longest string in the sequence without repeating characters"
    )
    public String largestNonRepeatingSubstring(@RequestBody(required = false) String input) {
        return interviewManager.largestNonRepeatingSubstring(input);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/largestSubstringWithoutDuplicates")
    @Operation(
        summary = "Longest string with no characters next to each other like aa in bcaadcb",
        description = "Uses a sliding window to itterate across the string and determine the longest string in the sequence without repeating characters"
    )
    public int largestSubstringWithoutDuplicates(@RequestBody(required = false) String input) {
        return interviewManager.largestSubstringLengthWithoutDuplicates(input);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/sumOfSortedListEquals")
    @Operation(
        summary = "Find two sorted values that equal a target sum",
        description = "Uses left and right pointers over a sorted integer array, moving inward according to the current sum until it finds a pair matching desiredSum. The manager supplies demonstration defaults when input is omitted."
    )
    public String sumOfSortedListEquals(@RequestBody(required = false) int[] input, int desiredSum) {
        return interviewManager.sumOfSortedListEquals(input, desiredSum);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/numberOfIslands")
    @Operation(
        summary = "Count connected islands in a character grid",
        description = "Traverses a grid of land and water cells with depth-first search, marking connected land cells as visited so each horizontal or vertical island is counted once. The manager supplies a sample grid when none is provided."
    )
    public String numberOfIslands(@RequestBody(required = false) char[][] input) {
        return interviewManager.numberOfIslands(input);
    }
}
