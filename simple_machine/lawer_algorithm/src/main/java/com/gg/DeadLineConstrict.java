package com.gg;

import com.gg.entity.Job;

import java.util.List;

public class DeadLineConstrict implements Constrict {
    private List<Job> jobList;

    @Override
    public int constrict(int jobId) {
        int result;
        long deadLine = jobList.stream()
                .filter(job -> job.getId() == jobId)
                .findFirst()
                .get().getDeadLine();
        result = (int) (deadLine);
        return result;
    }

    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }
}
