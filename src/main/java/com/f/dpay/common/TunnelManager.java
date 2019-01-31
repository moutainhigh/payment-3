package com.f.dpay.common;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.beust.jcommander.internal.Maps;
import com.f.dpay.tunnel.ITunnel;
import com.f.dpay.tunnel.Tunnel;
import com.f.utils.ClassUtils;

import lombok.extern.log4j.Log4j2;

/**
 * 管理持有所以支付通道
 * 
 * @author max
 *
 */
@Repository
@Log4j2
public class TunnelManager {
	private static final String TUNNEL_PACKAGE = "com.f.dpay.tunnel";
	/** key=tunnelId,value=tunnel */
	private Map<Integer, ITunnel> tunnels = Maps.newHashMap();

	@PostConstruct
	public void loadTunnels() {
		log.info("start load all tunnels to manager sets.");
		Set<Class<?>> list = ClassUtils.loadClasses(TUNNEL_PACKAGE, true);
		for (Class<?> clazz : list) {
			Tunnel tunnel = clazz.getAnnotation(Tunnel.class);
			if (tunnel != null) {
				try {
					ITunnel itunnel = (ITunnel) clazz.newInstance();
					tunnels.put(tunnel.tunnelId(), itunnel);
					log.info("LOAD_TUNNEL|" + tunnel.tunnelId() + "|" + tunnel.desc());
				} catch (InstantiationException e) {
					log.error("LOAD_TUNNEL_ERR|" + e.getMessage());
				} catch (IllegalAccessException e) {
					log.error("LOAD_TUNNEL_ERR|" + e.getMessage());
				}
			}
		}
	}

	/**
	 * 获取指定ID的支付平台
	 * 
	 * @param tunnelId
	 * @return
	 */
	public Optional<ITunnel> getTunnel(int tunnelId) {
		return Optional.ofNullable(tunnels.get(tunnelId));
	}
}
