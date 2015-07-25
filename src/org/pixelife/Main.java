package org.pixelife;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {
	
	private String chatFormat;

	public void onEnable()
	{
		loadConfig();
	    ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChatCommand());
	}
	
	public void loadConfig()
	{
		ConfigurationLoader configurationLoader = new ConfigurationLoader(this, "config.yml");
    
		try
		{
			this.chatFormat = configurationLoader.getConfiguration().getString("chatFormat");
		} catch (Exception e) {
		e.printStackTrace();
		}
	}

	public class StaffChatCommand extends net.md_5.bungee.api.plugin.Command
	{
		public StaffChatCommand()
		{
			super("staff");
		}
	  
		public void execute(net.md_5.bungee.api.CommandSender sender, String[] args)
		{
			if ((sender instanceof ProxiedPlayer)) {
				ProxiedPlayer player = (ProxiedPlayer)sender;
				if (player.hasPermission("pixel.staffchat")) {
					String message;
					if (args.length > 0) {
						message = "";
						for (String s : args) {
							message = message + ChatColor.WHITE + " " + s;
						}
						message = ChatColor.translateAlternateColorCodes('&', message);
						chatFormat = ChatColor.translateAlternateColorCodes('&', chatFormat);
						String finalChatFormat = chatFormat.replace("{DISPLAYNAME}", player.getName());
						for (ProxiedPlayer pl : ProxyServer.getInstance().getPlayers()) {
							if (pl.hasPermission("pixel.staffchat")) {
								pl.sendMessage(new ComponentBuilder(String.format(finalChatFormat) + message).create());
							}
						}
					} else {
						player.sendMessage(new ComponentBuilder("Private Chat for Staff members").create());
						player.sendMessage(new ComponentBuilder("/staff <message>").create());
					}
				} else {
					player.sendMessage(new ComponentBuilder(ChatColor.RED + "Sorry but you can't do that !").create());
				}
			}
		}
	}
}
