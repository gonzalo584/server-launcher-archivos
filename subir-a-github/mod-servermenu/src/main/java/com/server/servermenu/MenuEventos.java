package com.server.servermenu;

import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ServerMenu.MODID, value = Dist.CLIENT)
public class MenuEventos {

    // Cambia el menú de Minecraft (y las listas de mundos/servers) por el menú del server
    @SubscribeEvent
    public static void alAbrirPantalla(ScreenEvent.Opening e) {
        var nueva = e.getNewScreen();
        if (nueva instanceof PantallaMenu) return;
        if (nueva instanceof TitleScreen || nueva instanceof JoinMultiplayerScreen || nueva instanceof SelectWorldScreen) {
            e.setNewScreen(new PantallaMenu());
        }
    }
}
