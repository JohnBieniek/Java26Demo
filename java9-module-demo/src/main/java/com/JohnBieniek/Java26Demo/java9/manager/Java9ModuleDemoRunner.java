package com.JohnBieniek.Java26Demo.java9.manager;

public class Java9ModuleDemoRunner {
    public static void main(String[] args) {
        Java9Manager manager = new Java9Manager();
        System.out.println(manager.moduleDemo());
        System.out.println(manager.immutableCollectionsDemo());
    }
}