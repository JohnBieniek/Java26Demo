package com.JohnBieniek.Java26Demo.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;


@Service
public class InterviewManager {
    private static final String DEFAULT_NON_REPEATING_INPUT = "bcaadcb";
    private static final String DEFAULT_UNIQUE_SUBSTRING_INPUT = "bcaadacbaa";

    /**
     * Demonstrates a sliding window algorithm to find the longest substring without repeating characters.
     * @param input
     * @return Longest substring of non repeating characters
     */
    public String largestNonRepeatingSubstring(String input) {
        input = input == null || input.isEmpty() ? DEFAULT_NON_REPEATING_INPUT : input;
        String biggestString = "";
        int start = 0;

        Map<Character, Integer> lastSeen = new HashMap<>();

        for (int end = 0; end < input.length(); end++) {
            char currentChar = input.charAt(end);

            if (lastSeen.containsKey(currentChar) && lastSeen.get(currentChar) >= start) {
                start = lastSeen.get(currentChar) + 1;
            }

            lastSeen.put(currentChar, end);

            if (end - start + 1 > biggestString.length()) {
                biggestString = input.substring(start, end + 1);
            }
        }

        return "Input: " + input + ", Longest substring without repeating characters: " + biggestString;
    }

    /**
     * Demonstrates a sliding window algorithm to find the length of the longest substring without repeating characters.
     * @param input
     * @return
     */
    public int largestSubstringLengthWithoutDuplicates(String input) {
        input = input == null || input.isEmpty() ? DEFAULT_UNIQUE_SUBSTRING_INPUT : input;
        int left = 0;
        int longest=0;
        Character currentString = ' ';
        Map<Character,Integer> chars= new HashMap<>();
        for(int right =0; right< input.length(); right++){
            currentString = input.charAt(right);
            chars.put(currentString,chars.getOrDefault(currentString, 0)+1);
            while(chars.get(currentString)>1){
                chars.put(input.charAt(left),chars.get(input.charAt(left))-1);
                left++;
            }
            if(right-left+1>longest){
                longest=right-left+1;
            }
        }
        return longest;
    }

    /**
     * Demonstrates a two pointer algorithm to find two numbers in a sorted list that sum to a desired value.   
     * @param input
     * @param desiredSum
     * @return
     */
    public String sumOfSortedListEquals(int[] input, int desiredSum) {
        if(input==null || input.length<2){
            input = new int[]{1,4,5,8,9,12};
        }
        if(desiredSum==0){
            desiredSum=12;
        }

        int[] result = new int[2];
        int left = 0;
        int right = input.length-1;

        while(left<right){
            int sum = input[left]+input[right];
            if(sum<desiredSum){
                left++;
            }
            else if(sum>desiredSum){
                right--;
            }
            else{
                result[0]=input[left];
                result[1]=input[right];
                break;
            }
        }

        return "Input: " + java.util.Arrays.toString(input) + ", Desired Sum: " + desiredSum + ", Result: " + java.util.Arrays.toString(result);
    }


    /**
     * Demonstrates a depth first search algorithm to find the number of islands in a 2D grid.
     * @param grid
     * @return
     */
    public String numberOfIslands(char[][] grid) {
        int numIslands = 0;
        if(grid==null){
            grid = new char[][]{
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
            };
        }

        if (grid != null && grid.length == 0) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == '1') {
                        numIslands++;
                        dfs(i, j, grid);
                    }
                }
            }
        }
        return "Input: " + java.util.Arrays.deepToString(grid) + ", Number of Islands: " + numIslands;
    }

    /**
     * Depth first search helper method to traverse a grid and mark visited cells.
     * @param row
     * @param col
     * @param grid
     */
    void dfs(int row, int col, char[][] grid) {	
        if (row < 0 || col < 0 ||	
            row >= grid.length ||
            col >= grid[0].length ||
            grid[row][col] != '1') {
            return;
        }	
        grid[row][col] = '0';	
        dfs(row + 1, col, grid);	
        dfs(row - 1, col, grid);	
        dfs(row, col + 1, grid);	
        dfs(row, col - 1, grid);	
    }	
}
