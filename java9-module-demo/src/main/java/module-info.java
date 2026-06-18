module com.JohnBieniek.Java26Demo.java.nine {
    requires spring.context;

    exports com.JohnBieniek.Java26Demo.java9.manager;
    opens com.JohnBieniek.Java26Demo.java9.manager to spring.beans, spring.context, spring.core;
}
