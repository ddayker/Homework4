package com.dayker;

import java.util.concurrent.Semaphore;

public class CallCenter {

    public static final int SHORT_BREAK_TIME = 400;
    public static final int LONG_BREAK_TIME = 5000;

    private int numberOfOperators;

    private boolean[] operators;

    //if the operator is talking to the user, set "true"

    private Semaphore semaphore;

    public CallCenter() {
    }

    public CallCenter(int numberOfOperators) {
        this.numberOfOperators = numberOfOperators;
        operators = new boolean[numberOfOperators];
        semaphore = new Semaphore(numberOfOperators);
    }

    public void startCallCenter(int numberOfUsers) throws InterruptedException {

        System.out.println("All operators are ready to work !!!");

        for (int i = 1; i <= numberOfUsers; i++) {

            CallCenter.User user = this.new User(i);

            new Thread(user).start();

            Thread.sleep(SHORT_BREAK_TIME);

        }

    }


    public class User implements Runnable {

        private int userNumber;

        public User(int userNumber) {
            this.userNumber = userNumber;
        }

        @Override
        public void run() {

            int operatorNumber = -1;

            try {

                //acquire() requests access to the code block following the call of this method,
                // if access is not allowed, the thread that called this method is blocked until,
                //until the semaphore allows access
                semaphore.acquire();

                //Search for a free operator
                synchronized (operators) {
                    for (int i = 0; i < numberOfOperators; i++)
                        if (!operators[i]) {
                            operators[i] = true;  //take the operator
                            operatorNumber = i;
                            System.out.printf("Operator №%d started a conversation with the User %d.\n", (i + 1), userNumber);
                            break;
                        }
                }

                Thread.sleep(LONG_BREAK_TIME);

                synchronized (operators) {
                    System.out.printf("Operator №%d finished a conversation with the User %d.\n", (operatorNumber + 1), userNumber);
                    operators[operatorNumber] = false;// releasing the operator
                }

                //releasing the semaphore permission
                semaphore.release();


            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            } finally {
                System.out.printf("Operator №%d is having a rest :)\n", (operatorNumber + 1));

            }

        }
    }
}