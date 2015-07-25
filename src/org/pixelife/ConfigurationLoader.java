package org.pixelife;
import java.io.File;
import java.nio.file.CopyOption;
import java.nio.file.Files;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.ConfigurationProvider;

public class ConfigurationLoader
{
  private net.md_5.bungee.config.Configuration configuration;
  
  public ConfigurationLoader(Plugin plugin, String filename)
  {
    try
    {
      if (!plugin.getDataFolder().exists())
      {
        plugin.getLogger().info("Creating configuration folder");
        plugin.getDataFolder().mkdir();
      }
      
      File file = new File(plugin.getDataFolder(), filename);
      
      if (!file.exists())
      {
        plugin.getLogger().info("Creating default configuration file");
        Files.copy(plugin.getResourceAsStream("config.yml"), file.toPath(), new CopyOption[0]);
      }
      
      this.configuration = ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(file);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public net.md_5.bungee.config.Configuration getConfiguration()
  {
    return this.configuration;
  }
}
