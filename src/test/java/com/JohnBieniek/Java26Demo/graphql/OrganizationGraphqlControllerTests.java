package com.JohnBieniek.Java26Demo.graphql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.ExecutionGraphQlService;
import org.springframework.graphql.test.tester.ExecutionGraphQlServiceTester;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.JohnBieniek.Java26Demo.service.SqlService;

@SpringBootTest
class OrganizationGraphqlControllerTests {
    private GraphQlTester graphQlTester;

    @Autowired
    private ExecutionGraphQlService executionGraphQlService;

    @Autowired
    private SqlService sqlService;

    @BeforeEach
    void resetData() {
        graphQlTester = ExecutionGraphQlServiceTester.create(executionGraphQlService);
        sqlService.resetEmployeesTeamsAndProjects();
    }

    @Test
    void queriesNestedOrganizationDataAndComputedField() {
        graphQlTester.document("""
                query {
                  teams {
                    name
                    employees { name totalCompensation }
                    projects { name budget }
                  }
                }
                """)
                .execute()
                .path("teams[0].name").entity(String.class).isEqualTo("Alpha")
                .path("teams[0].employees[0].name").entity(String.class).isEqualTo("Alice")
                .path("teams[0].employees[0].totalCompensation").entity(Integer.class).isEqualTo(135000)
                .path("teams[0].projects").entityList(Object.class).hasSize(2);
    }

    @Test
    void createsTeamWithMutationVariables() {
        graphQlTester.document("""
                mutation CreateTeam($input: CreateTeamInput!) {
                  createTeam(input: $input) { id name location }
                }
                """)
                .variable("input", java.util.Map.of("name", "Delta", "location", "Denver"))
                .execute()
                .path("createTeam.id").hasValue()
                .path("createTeam.name").entity(String.class).isEqualTo("Delta")
                .path("createTeam.location").entity(String.class).isEqualTo("Denver");
    }

    @Test
    void returnsNullForUnknownEmployee() {
        graphQlTester.document("query { employee(id: 999999) { id } }")
                .execute()
                .path("employee").valueIsNull();
    }
}
