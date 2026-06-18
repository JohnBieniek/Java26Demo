module com.JohnBieniek.Java26Demo.java.nine {
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.aop;
    requires spring.expression;
    requires micrometer.observation;
    requires org.jspecify;

    exports com.JohnBieniek.Java26Demo.java9.manager;
    opens com.JohnBieniek.Java26Demo.java9.manager to spring.beans, spring.context, spring.core;
}
