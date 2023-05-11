package com.edgeburnmedia.ipapi.ipapi;

import com.edgeburnmedia.ipapi.ipapi.api.IpApiRequest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class LocateIpCommand implements CommandExecutor {

	/**
	 * Executes the given command, returning its success.
	 * <br>
	 * If false is returned, then the "usage" plugin.yml entry for this command (if defined) will be
	 * sent to the player.
	 *
	 * @param sender  Source of the command
	 * @param command Command which was executed
	 * @param label   Alias of the command which was used
	 * @param args    Passed command arguments
	 * @return true if a valid command, otherwise false
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args[0] != null && !args[0].equals("")) {
			String ip = args[0];
			IpApiRequest request = new IpApiRequest(ip);
			new BukkitRunnable() {
				@Override
				public void run() {
					try {
						sender.spigot().sendMessage(request.getResponse().toMinecraftChat());
					} catch (IpApiRequest.RateLimitException e) {
						e.printStackTrace();
					}
				}
			}.runTaskAsynchronously(IpApi.getPlugin(IpApi.class));
			return true;

		}
		return false;
	}
}
