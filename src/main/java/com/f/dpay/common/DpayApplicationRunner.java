package com.f.dpay.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.f.dpay.task.PullTranDataTask;
import com.f.thread.schedule.ThreadPoolManager;

@Component
public class DpayApplicationRunner implements ApplicationRunner {

	@Autowired
	private ThreadPoolManager threadPoolManager;
	@Autowired
	private PullTranDataTask pullTranDataTask;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		threadPoolManager.scheduleGeneralAtFixedRate(pullTranDataTask, 1000, 1000);
	}

}
