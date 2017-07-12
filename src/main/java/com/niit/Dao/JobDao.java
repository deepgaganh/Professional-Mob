package com.niit.Dao;

import java.util.List;

import com.niit.model.Job;

public interface JobDao {

	void saveJob(Job job);
	List<Job> getAllJobs();
	public Job getJobById(int id);
}
