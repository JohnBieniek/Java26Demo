package com.JohnBieniek.Java26Demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.RestController;

import com.JohnBieniek.Java26Demo.model.animal.Beetle;

@RestController
@RequestMapping(value = "/demo")
public class Java8Controller {	
    private static final Logger logger = LoggerFactory.getLogger(Java8Controller.class);

    @RequestMapping(method = RequestMethod.GET, path = "/demoLambda")
    public String demoLambdaStringDoubling(String input){
        Function<String, String> runnable = (currentInput) -> {
            String updatedResult = currentInput + "&" + currentInput; // double the input string
            logger.info("Running the lambda with input: {} and result: {}", currentInput, updatedResult);
            return updatedResult;
        };  
        return runnable.apply(input);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/demoForEachLambda")
    public String demoForEachLambda(){
        StringBuilder updatedResult = new StringBuilder();
        ArrayList<String> names = new ArrayList<>(List.of("Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Heidi", "Ivan", "Judy"));

        names.forEach(name -> {
            if(updatedResult.length() > 0){
                updatedResult.append("&");
            }
            updatedResult.append(name);
            logger.info("Running the lambda with input: {} and result: {}", name, updatedResult);
        });

        return "Names processed with forEach lambda: " + updatedResult;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/demoDefaultInterfaceMethod")
    public String demoDefaultInterfaceMethod(){
        Beetle beetle = new Beetle();
        String result = beetle.describeFlight() + " " + beetle.fly();
        logger.info("Default interface demo: {}", result);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/demoMethodReference")
    public String demoMethodReference(){
        List<String> names = List.of("Alice", "Bob", "Charlie");

        String result = names.stream()
            .map(String::toUpperCase)// method reference to convert each name to uppercase instead of name - > name.toUpperCase()
            .reduce((a, b) -> a + ", " + b)
            .orElse("No names");

        logger.info("Method reference demo: {}", result);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/demoStreamFilterAndReduce")
    public String demoStreamFilterAndReduce(){
        List<String> names = List.of("Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Heidi", "Ivan", "Judy");
        String result = names.stream()
            .filter(name -> name.startsWith("A") || name.startsWith("E") || name.startsWith("I") || name.startsWith("O") || name.startsWith("U")) // filter names that start with a vowel
            .map(name -> name.toUpperCase()) // convert to uppercase
            .reduce((name1, name2) -> name1 + " & " + name2) // concatenate with &
            .orElse("No names found"); // default if no names match
        return "Names starting with a vowel from A-I, filtered with a stream:"+result;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/testOptional")
    public String testOptional(Optional<String> input){
        String result = input.orElse("No input") + " complete";
        return result;
    }
}
