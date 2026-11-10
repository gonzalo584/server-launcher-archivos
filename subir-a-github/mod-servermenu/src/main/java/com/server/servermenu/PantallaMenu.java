package com.server.servermenu;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Files;
import java.nio.file.Path;

public class PantallaMenu extends Screen {
    private static final ResourceLocation FONDO = new ResourceLocation(ServerMenu.MODID, "textures/gui/fondo.png");
    private static final ResourceLocation MASCOTA = new ResourceLocation(ServerMenu.MODID, "textures/gui/mascota.png");
    private static final String SERVER_POR_DEFECTO = "gonzalobh.exaroton.me";
    private static final String TITULO = "SERVER";

    public PantallaMenu() {
        super(Component.literal("Server"));
    }

    // El launcher escribe la dirección en config/servermenu.txt
    private static String direccion() {
        try {
            Path p = FMLPaths.CONFIGDIR.get().resolve("servermenu.txt");
            if (Files.exists(p)) {
                String s = Files.readString(p).trim();
                if (!s.isEmpty()) return s;
            }
        } catch (Exception ignored) {}
        return SERVER_POR_DEFECTO;
    }

    @Override
    protected void init() {
        int ancho = 200, alto = 20, x = this.width / 2 - ancho / 2;
        int y = this.height / 2 + 30;

        addRenderableWidget(Button.builder(Component.literal("Jugar"), b -> {
            String ip = direccion();
            ServerData datos = new ServerData("Server", ip, false);
            ConnectScreen.startConnecting(this, this.minecraft, ServerAddress.parseString(ip), datos, false);
        }).bounds(x, y, ancho, alto).build());

        addRenderableWidget(Button.builder(Component.literal("Configuración"),
                b -> this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options))
        ).bounds(x, y + 24, ancho, alto).build());

        addRenderableWidget(Button.builder(Component.literal("Cerrar"),
                b -> this.minecraft.stop()
        ).bounds(x, y + 48, ancho, alto).build());
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        // Fondo estirado a toda la pantalla
        g.blit(FONDO, 0, 0, this.width, this.height, 0, 0, 960, 540, 960, 540);
        g.fill(0, 0, this.width, this.height, 0x33000000);

        // Manzana flotando
        double t = Util.getMillis() / 400.0;
        int brinco = (int) (Math.sin(t) * 4);
        int tamMascota = 56;
        int cx = this.width / 2;
        int yMascota = this.height / 2 - 100 + brinco;
        g.blit(MASCOTA, cx - tamMascota / 2, yMascota, tamMascota, tamMascota, 0, 0, 128, 128, 128, 128);

        // Título grande con sombra rosa
        float escala = 4.0f;
        int anchoTexto = this.font.width(TITULO);
        g.pose().pushPose();
        g.pose().translate(cx - anchoTexto * escala / 2f, this.height / 2f - 38, 0);
        g.pose().scale(escala, escala, 1f);
        g.drawString(this.font, TITULO, 0, 2, 0xFF7B2A7F, false);
        g.drawString(this.font, TITULO, 0, 1, 0xFFFF5F8F, false);
        g.drawString(this.font, TITULO, 0, 0, 0xFFFFF6E8, false);
        g.pose().popPose();

        g.drawCenteredString(this.font, direccion(), cx, this.height / 2 + 4, 0xFFD9CCFF);

        super.render(g, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
