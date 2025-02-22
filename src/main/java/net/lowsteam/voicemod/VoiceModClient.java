package net.lowsteam.voicemod;

import net.fabricmc.api.ClientModInitializer;
import net.lowsteam.voicemod.voice.VoiceClient;
import javax.sound.sampled.LineUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoiceModClient implements ClientModInitializer {
    private static VoiceClient voiceClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(VoiceModClient.class);

    @Override
    public void onInitializeClient() {
        try {
            voiceClient = new VoiceClient();
            voiceClient.initialize();
        } catch (LineUnavailableException e) {
            LOGGER.error("Failed to initialize voice client: ", e);
        }
    }

    public static VoiceClient getVoiceClient() {
        return voiceClient;
    }
}
