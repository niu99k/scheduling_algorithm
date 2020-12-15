package com.gg;

import com.gg.entity.Job;
import com.gg.entity.Machine;

import java.util.*;

public class Solver {
    private List<Job> jobList;
    private List<Machine> machineList;
    private Constrict constrict;

    private List<Integer> result;
    private Map<Integer, Boolean> jobSearchMap;

    public void solve() {
        sortJobWithLA();
        printResult();
    }

    private void printResult() {
        result.forEach(jobId -> System.out.print(jobId + " "));
    }

    private void sortJobWithLA() {
        Stack<Job> jobStack = new Stack<Job>();
        while (isStillJobUnfinished(jobSearchMap())) {
            jobStack.push(jobWithNoSucAndMinConstrict(jobSearchMap()));
            jobSearchMap().put(jobWithNoSucAndMinConstrict(jobSearchMap()).getId(), true);
        }
        while (!jobStack.isEmpty()) {
            result.add(jobStack.pop().getId());
        }
    }

    private Job jobWithNoSucAndMinConstrict(Map<Integer, Boolean> jobSearchMap) {
        Job result;
        result = jobList.stream()
                .filter(job -> jobWithNoSucOrSearchedSuc(job, jobSearchMap))
                .min((job1, job2) -> {
                    return constrict.constrict(job1.getId()) - constrict.constrict(job2.getId());
                })
                .get();
        return result;
    }

    private boolean jobWithNoSucOrSearchedSuc(Job job, Map<Integer, Boolean> jobSearchMap) {
        boolean result;
        if (isJobNoSuc(job)) {
            result = true;
        } else if (isJobSucAllSearched(job, jobSearchMap)) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    private boolean isJobSucAllSearched(Job job, Map<Integer, Boolean> jobSearchMap) {
        boolean result;
        result = !job.getPrecNext().stream()
                .filter(sucJob -> !jobSearchMap.get(sucJob.getId()))
                .findAny()
                .isPresent();
        return result;
    }

    private boolean isJobNoSuc(Job job) {
        boolean result;
        result = job.getPrecNext() == null || job.getPrecNext().isEmpty();
        return result;
    }

    private Map<Integer, Boolean> jobSearchMap() {
        Map<Integer, Boolean> result;
        if (jobSearchMap == null || jobSearchMap.isEmpty()) {
            jobSearchMap = new HashMap<Integer, Boolean>();
            jobList.forEach(job -> jobSearchMap.put(job.getId(), false));
            result = jobSearchMap;
        } else {
            result = jobSearchMap;
        }
        return result;
    }

    private boolean isStillJobUnfinished(Map<Integer, Boolean> jobSearchMap) {
        boolean result;
        result = jobSearchMap.values().stream()
                .filter(isSearched -> !isSearched)
                .findAny()
                .isPresent();
        return result;
    }
}
