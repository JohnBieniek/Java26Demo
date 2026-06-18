package com.JohnBieniek.Java26Demo.manager;

import static com.JohnBieniek.Java26Demo.utility.Java21ProcessingUtils.countWords;
import static com.JohnBieniek.Java26Demo.utility.Java21ProcessingUtils.simulateBlockingValidation;
import static com.JohnBieniek.Java26Demo.utility.Java21ProcessingUtils.splitIntoSegments;
import static com.JohnBieniek.Java26Demo.utility.Java21ProcessingUtils.useDefaultInputIfBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.JohnBieniek.Java26Demo.dto.java21.LargeInputProcessingResult;
import com.JohnBieniek.Java26Demo.dto.java21.SegmentProcessingResult;
import com.JohnBieniek.Java26Demo.dto.java21.SequencedCollectionResult;
import com.JohnBieniek.Java26Demo.dto.organization.EmployeeProjectAssignment;
import com.JohnBieniek.Java26Demo.dto.organization.EmployeeSummary;
import com.JohnBieniek.Java26Demo.dto.organization.ProjectSummary;

@Service
public class Java21Manager {
    private final SqlManager sqlManager;

    public Java21Manager(SqlManager sqlManager) {
        this.sqlManager = sqlManager;
    }

    /**
     * Demonstrates Java 21 pattern matching for switch with nested record patterns.
     * The switch destructures a real employee/project assignment and uses a guarded
     * case to classify major project budgets.
     */
    public String getEmployeeProjectAssignmentDescription(Long employeeId, Long projectId) {
        EmployeeProjectAssignment assignment =
                sqlManager.findEmployeeProjectAssignment(employeeId, projectId);

        return switch (assignment) {
            case EmployeeProjectAssignment(
                    EmployeeSummary(Long id, String employeeName, String department, String teamName),
                    ProjectSummary(Long matchedProjectId, String projectName, int budget, String projectTeamName)
            ) when budget >= 200000 ->
                    employeeName + " (" + department + ") is assigned through team " + teamName
                            + " to major project " + projectName + " with budget " + budget
                            + ". Employee id: " + id + ", project id: " + matchedProjectId
                            + ", project team: " + projectTeamName + ".";
            case EmployeeProjectAssignment(
                    EmployeeSummary(Long id, String employeeName, String department, String teamName),
                    ProjectSummary(Long matchedProjectId, String projectName, int budget, String projectTeamName)
            ) ->
                    employeeName + " (" + department + ") is assigned through team " + teamName
                            + " to project " + projectName + " with budget " + budget
                            + ". Employee id: " + id + ", project id: " + matchedProjectId
                            + ", project team: " + projectTeamName + ".";
        };
    }

    /**
     * Demonstrates Java 21 virtual threads with a realistic large-input workload.
     * The input is divided into fixed-size segments, each virtual-thread task performs
     * a simulated blocking validation and word count, and the results are combined.
     */
    public LargeInputProcessingResult processLargeInput(String input) {
        long startedAt = System.nanoTime();
        String processedInput = useDefaultInputIfBlank(input);
        List<String> segments = splitIntoSegments(processedInput);

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<SegmentProcessingResult>> futures = segments.stream()
                    .map(segment -> executor.submit(() -> {
                        simulateBlockingValidation();
                        return new SegmentProcessingResult(
                                countWords(segment),
                                Thread.currentThread().isVirtual());
                    }))
                    .toList();

            long wordCount = 0;
            int virtualThreadTasks = 0;
            for (Future<SegmentProcessingResult> future : futures) {
                SegmentProcessingResult result = future.get();
                wordCount += result.wordCount();
                if (result.virtualThread()) {
                    virtualThreadTasks++;
                }
            }

            long elapsedMilliseconds = (System.nanoTime() - startedAt) / 1_000_000;
            return new LargeInputProcessingResult(
                    processedInput.length(),
                    segments.size(),
                    wordCount,
                    virtualThreadTasks,
                    elapsedMilliseconds
            );
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Large input processing was interrupted.", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("A virtual-thread task failed.", exception.getCause());
        }
    }

    /**
     * Demonstrates the Java 21 SequencedCollection API through ordered first, last,
     * and reversed views of a mutable list.
     */
    public SequencedCollectionResult sequencedCollectionDemo(List<String> values) {
        List<String> sequence = values == null || values.isEmpty()
                ? new ArrayList<>(List.of("Bird", "Dog", "Beetle"))
                : new ArrayList<>(values);

        sequence.addFirst("First");
        sequence.addLast("Last");

        return new SequencedCollectionResult(
                List.copyOf(sequence),
                List.copyOf(sequence.reversed()),
                sequence.getFirst(),
                sequence.getLast()
        );
    }

}
