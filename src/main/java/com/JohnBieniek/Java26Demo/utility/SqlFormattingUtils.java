package com.JohnBieniek.Java26Demo.utility;

public final class SqlFormattingUtils {
    private SqlFormattingUtils() {}

    public static String formatJoinedEmployeeAndTeam(String employeeName, String teamName) {
        return (employeeName == null ? "No employee" : employeeName)
                + " | "
                + (teamName == null ? "No team" : teamName);
    }
}
