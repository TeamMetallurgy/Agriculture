package com.teammetallurgy.agriculture.client.proxy;

import com.teammetallurgy.agriculture.common.proxy.IProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ClientProxy implements IProxy {

    @Override
    public void init() {
        // ScreenManager.registerFactory(GENERATOR, SCREEN::new);
        // TODO(freya): register Juicer Screen
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }

}