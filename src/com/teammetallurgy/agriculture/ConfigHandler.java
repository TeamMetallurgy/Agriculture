package com.teammetallurgy.agriculture;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class ConfigHandler {
    private static Configuration configuration;

    public static int getBlockId(final String string, final int def)
    {
        int ret = def;

        try
        {
            ConfigHandler.configuration.load();

            ret = ConfigHandler.configuration.getBlock(string, def).getInt();
        }
        catch (final Exception ex)
        {
            Agriculture.instance.getLogger().warning("Could not load block id for " + string + ". Reason" + ex.getMessage());
        }
        finally
        {
            if (ConfigHandler.configuration.hasChanged())
            {
                ConfigHandler.configuration.save();
            }
        }

        return ret;
    }

    public static int getItemId(final String string, final int def)
    {
        int ret = def;

        try
        {
            ConfigHandler.configuration.load();

            ret = ConfigHandler.configuration.getItem(string, def).getInt();
        }
        catch (final Exception ex)
        {
            Agriculture.instance.getLogger().warning("Could not load item id for " + string + ". Reason" + ex.getMessage());
        }
        finally
        {
            if (ConfigHandler.configuration.hasChanged())
            {
                ConfigHandler.configuration.save();
            }
        }

        return ret;
    }

    public static void init(final File suggestedConfigurationFile)
    {
        ConfigHandler.configuration = new Configuration(suggestedConfigurationFile);

        try
        {
            ConfigHandler.configuration.load();

        }
        catch (final Exception ex)
        {
            Agriculture.instance.getLogger().warning("Could not load configurations. Reason" + ex.getMessage());
        }
        finally
        {
            if (ConfigHandler.configuration.hasChanged())
            {
                ConfigHandler.configuration.save();
            }
        }
    }

    public static void set(final String string, final int value)
    {

        try
        {
            ConfigHandler.configuration.load();

            for (final String category : ConfigHandler.configuration.getCategoryNames())
            {
                if (ConfigHandler.configuration.getCategory(category).containsKey(string))
                {
                    ConfigHandler.configuration.getCategory(category).get(string).set(value);
                }
            }

        }
        catch (final Exception ex)
        {
            Agriculture.instance.getLogger().warning("Could not set new id for " + string + ". Reason" + ex.getMessage());
        }
        finally
        {
            if (ConfigHandler.configuration.hasChanged())
            {
                ConfigHandler.configuration.save();
            }
        }

    }

}
