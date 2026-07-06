package com.JohnBieniek.Java26Demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
        info = @Info(
                title = "Java Feature, SQL, and Algorithm Demonstration API",
                version = "26",
                description = """
                        This portfolio API provides runnable examples of functionality introduced across Java releases.
                        It combines language fundamentals, release-specific APIs, database examples, common algorithms,
                        outbound HTTP integration, runtime diagnostics, and scheduled batch processing in one application.

                        The animal endpoints cover core object-oriented Java concepts using a small, approachable domain.
                        They demonstrate interfaces and default interface methods, abstract classes, inheritance,
                        polymorphism through a shared Animal type, method overriding and dynamic dispatch, static method
                        hiding, final declarations, switch expressions, text blocks, pattern matching for instanceof,
                        and sealed interfaces with explicitly permitted implementations. Separate String endpoints show
                        reference identity with ==, value equality with equals, literal interning, and immutability.

                        The versioned controller groups provide a feature timeline. Java 8 examples cover lambdas,
                        Function, forEach, streams, filtering, mapping, reduction, method references, Optional, and default
                        interface methods. Java 9 demonstrates the Java Platform Module System with a separate named module
                        and immutable List.of, Set.of, and Map.of factories. Java 10 demonstrates local-variable type
                        inference with var. Java 11 covers the standardized HTTP Client, String.isBlank, lines, strip,
                        and Optional.isEmpty. Java 14 through 17 features appear in the animal and organization examples,
                        including switch expressions, text blocks, records, pattern matching for instanceof, and sealed
                        types. Java 21 demonstrates nested record patterns, guarded switch cases, virtual threads for
                        blocking work, and sequenced collections with first, last, and reversed access.

                        Java 26 coverage includes the built-in HTTP Client opting into HTTP/3 while reporting real HTTP/3,
                        HTTP/2, or HTTP/1.1 negotiation; primitive type patterns and constants in a long switch; the
                        LazyConstant preview API and one-time initialization; structured concurrency combining related
                        profile, order, and recommendation tasks; the preview PEM API with an RSA public-key round trip;
                        warnings and restrictions around deep-reflection mutation of final fields; scalar versus incubating
                        Vector API array addition; AOT, garbage collector, VM, and JVM-argument runtime information; and
                        verification that the obsolete Applet API has been removed.

                        The SQL and organization endpoints demonstrate common relational database operations including
                        validated CRUD requests, parameterized queries, soft deletion, INNER/RIGHT/LEFT joins, UNION ALL,
                        grouping and aggregate functions, window functions, record projections, and relationship mapping.

                        SQL storage uses an embedded, in-memory H2 database intended for repeatable demonstrations rather
                        than permanent storage. The JDBC URL is jdbc:h2:mem:demo-db, the username is sa, and the password
                        is blank. While the application is running, the H2 console is available at /h2-console. Hibernate
                        creates the schema when the application starts and drops it when the application stops, so all
                        application data is lost on restart.

                        A Spring Batch job also removes all project, employee, and team rows every morning at 6:00 AM in
                        the America/New_York time zone. Spring Batch execution metadata is retained. The cron expression
                        and time zone can be changed with app.jobs.database-cleanup.cron and
                        app.jobs.database-cleanup.zone.

                        The interview endpoints contain common algorithm demonstrations, including sliding-window string
                        searches for unique substrings, two-pointer sum matching over sorted values, duplicate detection,
                        and recursive depth-first search for horizontally or vertically connected islands in a
                        two-dimensional grid.
                        """))
public class Java26DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Java26DemoApplication.class, args);
	}

}
