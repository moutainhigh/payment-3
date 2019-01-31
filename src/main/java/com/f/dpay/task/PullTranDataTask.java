package com.f.dpay.task;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.f.dpay.common.TunnelManager;
import com.f.dpay.tunnel.ITunnel;

/**
 * 拉取交易数据
 * 
 * @author max
 *
 */
@Component
public class PullTranDataTask implements Runnable {

	@Autowired
	private TunnelManager tunnelManager;

	@Override
	public void run() {
		Optional<ITunnel> tunnel = tunnelManager.getTunnel(1);
		if (tunnel.isPresent())
			tunnel.get().loadAccountBalance();
		else {
			System.err.println("------------------1小时拉取一次数据wrok|tunnel null.");
		}
	}

}
