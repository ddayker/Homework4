package com.dayker;

public class Main {

    public static void main(String[] args) {

        int numberOfUsers = 8;
        int numberOfOperators = 5;

        CallCenter MyCallCenter = new CallCenter(numberOfOperators); //creating a call-center with "numberOfOperators" operators

        try {
            MyCallCenter.startCallCenter(numberOfUsers); // starting a call center with "numberOfUsers" users
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
