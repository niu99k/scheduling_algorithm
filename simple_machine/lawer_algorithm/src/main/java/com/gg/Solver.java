package com.gg;

import com.gg.entity.Job;
import com.gg.entity.Machine;

import java.util.*;

public class Solver {
    private List<Job> jobList;
    private Machine machine;
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

        result = new ArrayList<>();
        while (!jobStack.isEmpty()) {
            result.add(jobStack.pop().getId());
        }
    }

    private Job jobWithNoSucAndMinConstrict(Map<Integer, Boolean> jobSearchMap) {
        Job result;
        result = jobList.stream()
                .filter(job -> !jobSearchMap.get(job.getId()))
                .filter(job -> jobWithNoSucOrSearchedSuc(job, jobSearchMap))
                .max((job1, job2) -> {
                    return constrict.constrict(job1.getId()) - constrict.constrict(job2.getId());
                })
                .get();
        return result;
    }

    private boolean jobWithNoSucOrSearchedSuc(Job job, Map<Integer, Boolean> jobSearchMap) {
        boolean result;
        if (isJobSucAllSearched(job, jobSearchMap)) {
            result = true;
        } else if (isJobNoSuc(job)) {
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
        result = job.getPrecNext() == null
                || job.getPrecNext().isEmpty();
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

    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Constrict getConstrict() {
        return constrict;
    }

    public void setConstrict(Constrict constrict) {
        this.constrict = constrict;
    }

    public List<Integer> getResult() {
        return result;
    }

    public void setResult(List<Integer> result) {
        this.result = result;
    }

    public Map<Integer, Boolean> getJobSearchMap() {
        return jobSearchMap;
    }

    public void setJobSearchMap(Map<Integer, Boolean> jobSearchMap) {
        this.jobSearchMap = jobSearchMap;
    }
}
