package com.edgeburnmedia.ipapi.ipapi.api;

import org.bukkit.entity.Player;

public class IpApiRequest {
	private String ip;
	static final int FIELDS = 21227513;

	public IpApiRequest(String ip) {
		this.ip = ip;
	}

	public IpApiRequest(Player player) {
		this.ip = player.getAddress().getAddress().getHostAddress();
	}

	public IpApiResponse getResponse() throws RateLimitException {
		return IpApiResponse.getResponse(this);
	}

	public String getIp() {
		return ip;
	}

	public static class RateLimitException extends Exception {
		public RateLimitException(String message) {
			super(message);
		}
	}
}
