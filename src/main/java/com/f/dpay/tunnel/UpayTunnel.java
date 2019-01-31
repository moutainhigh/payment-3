package com.f.dpay.tunnel;

import org.springframework.stereotype.Component;

@Tunnel(tunnelId = 1, desc = "UpayTunnel")
@Component
public class UpayTunnel extends BaseTunnel {

	@Override
	public void loadAccountBalance() {
		//System.err.println("loadAccountBalance");
	}

}
