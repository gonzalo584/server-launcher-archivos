package com.server.servermenu;

import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;

@Mod(ServerMenu.MODID)
public class ServerMenu {
    public static final String MODID = "servermenu";

    public ServerMenu() {
        // Mod solo del cliente: el server no necesita tenerlo para que puedas entrar
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class,
                () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }
}
