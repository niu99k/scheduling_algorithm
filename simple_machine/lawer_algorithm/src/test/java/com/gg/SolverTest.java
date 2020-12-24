package com.gg;


import org.gg.sa.testcase.TestCase1;
import org.junit.Test;

public class SolverTest {
    @Test
    public void testLA() {
        TestCase1 testCase1 = new TestCase1();
        Solver solver4LA = new Solver();
        solver4LA.setJobList(testCase1.getJobList());
        solver4LA.setMachine(testCase1.getMachine());

        DeadLineConstrict deadLineConstrict = new DeadLineConstrict();
        deadLineConstrict.setJobList(testCase1.getJobList());

        solver4LA.setConstrict(deadLineConstrict);

        solver4LA.solve();
    }
}