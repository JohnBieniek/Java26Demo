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
@Tag(name = "Interview Controller", description = "Endpoints demonstrating basic interview patterns.")
public class InterviewController {
    private InterviewManager interviewManager = new InterviewManager();

    public InterviewController(InterviewManager interviewManager) {
        this.interviewManager = interviewManager;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/largestNonRepeatingSubstring")
    @Operation(
        summary = "Longest string with no characters next to each other like aa in bcaadcb",
        description = "Uses a sliding window to itterate across the string and determine the longest string in the sequence without repeating characters"
    )
    public String largestNonRepeatingSubstring(@RequestBody(required = false) String input) {
        if(null == input || input.isEmpty()) {
            input = "bcaadcb";
        }
        return interviewManager.largestNonRepeatingSubstring(input);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/largestSubstringWithoutDuplicates")
    @Operation(
        summary = "Longest string with no characters next to each other like aa in bcaadcb",
        description = "Uses a sliding window to itterate across the string and determine the longest string in the sequence without repeating characters"
    )
    public int largestSubstringWithoutDuplicates(@RequestBody(required = false) String input) {
        if(null == input || input.isEmpty()) {
            input = "bcaadacbaa";
        }
        return interviewManager.largestSubstringLengthWithoutDuplicates(input);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/sumOfSortedListEquals")
    public String sumOfSortedListEquals(@RequestBody(required = false) int[] input, int desiredSum) {
        return interviewManager.sumOfSortedListEquals(input, desiredSum);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/numberOfIslands")
    public String numberOfIslands(@RequestBody(required = false) char[][] input) {
        return interviewManager.numberOfIslands(input);
    }
}
