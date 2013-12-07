package com.teammetallurgy.agriculture.modstuff;

public class ModIntegration {

    public static void init(String mod)
    {
        if(mod.equalsIgnoreCase("MFR"))
        {
            MineFactoryReloaded.initMFR();
        }
        
    }

}
