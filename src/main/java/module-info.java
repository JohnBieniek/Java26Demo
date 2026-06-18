module com.JohnBieniek.Java26Demo {
    requires java.net.http;
    requires jdk.httpserver;
    requires commons.logging;

    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.web;
    requires spring.beans;
    requires spring.core;
    requires spring.webmvc;
    requires spring.data.jpa;
    requires spring.data.commons;
    requires spring.tx;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires jakarta.validation;
    requires io.swagger.v3.oas.annotations;
    requires org.slf4j;
    requires com.JohnBieniek.Java26Demo.java.nine;

    opens com.JohnBieniek.Java26Demo to spring.core, spring.beans, spring.context;
    opens com.JohnBieniek.Java26Demo.controller to spring.core, spring.beans, spring.context, spring.web;
    opens com.JohnBieniek.Java26Demo.manager to spring.core, spring.beans, spring.context;
    opens com.JohnBieniek.Java26Demo.model.organization to spring.core, spring.beans, org.hibernate.orm.core;
    opens com.JohnBieniek.Java26Demo.dto.organization to spring.core, spring.beans;

    exports com.JohnBieniek.Java26Demo;
    exports com.JohnBieniek.Java26Demo.controller;
    exports com.JohnBieniek.Java26Demo.manager;
}