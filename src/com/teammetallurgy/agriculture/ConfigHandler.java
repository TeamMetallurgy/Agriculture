package com.teammetallurgy.agriculture;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class ConfigHandler
{
    private static Configuration configuration;

    public static void init(File suggestedConfigurationFile)
    {
        configuration = new Configuration(suggestedConfigurationFile);

        try
        {
            configuration.load();

        } catch (Exception ex)
        {
            Agriculture.instance.getLogger().warning("Could not load configurations. Reason" + ex.getMessage());
        } finally
        {
            if (configuration.hasChanged())
            {
                configuration.save();
            }
        }
    }

    public static int getBlockId(String string, int def)
    {
        int ret = def;

        try
        {
            configuration.load();

            ret = configuration.getBlock(string, def).getInt();
        } catch (Exception ex)
        {
            Agriculture.instance.getLogger().warning("Could not load block id for " + string + ". Reason" + ex.getMessage());
        } finally
        {
            if (configuration.hasChanged())
            {
                configuration.save();
            }
        }

        return def;
    }

    public static int getItemId(String string, int def)
    {
        int ret = def;

        try
        {
            configuration.load();

            ret = configuration.getItem(string, def).getInt();
        } catch (Exception ex)
        {
            Agriculture.instance.getLogger().warning("Could not load item id for " + string + ". Reason" + ex.getMessage());
        } finally
        {
            if (configuration.hasChanged())
            {
                configuration.save();
            }
        }

        return def;
    }

}
