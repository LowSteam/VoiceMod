package net.lowsteam.voicemod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.DrawContext;
import net.lowsteam.voicemod.voice.VoiceClient;
import javax.sound.sampled.LineUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.text.Text;

public class VoiceModClient implements ClientModInitializer {
    private static VoiceClient voiceClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(VoiceModClient.class);
    private volatile double currentAudioLevel = -60.0;

    @Override
    public void onInitializeClient() {
        try {
            voiceClient = new VoiceClient();
            voiceClient.initialize();
            
            // Register tick event for HUD updates
            ClientTickEvents.START_CLIENT_TICK.register(client -> {
                if (client.player != null && client.inGameHud != null) {
                    client.inGameHud.setOverlayMessage(
                        Text.literal(String.format("Audio Level: %.1f dB", currentAudioLevel)),
                        false
                    );
                }
            });

        } catch (LineUnavailableException e) {
            LOGGER.error("Failed to initialize voice client: ", e);
        }
    }

    public static VoiceClient getVoiceClient() {
        return voiceClient;
    }
}
