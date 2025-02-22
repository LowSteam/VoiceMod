package net.lowsteam.voicemod.voice;

import javax.sound.sampled.*;

public class VoiceClient {
    private TargetDataLine microphone;
    private boolean isCapturing;
    private static final AudioFormat FORMAT = new AudioFormat(44100, 16, 1, true, true);
    
    public void initialize() throws LineUnavailableException {
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, FORMAT);
        if (!AudioSystem.isLineSupported(info)) {
            throw new LineUnavailableException("Microphone not supported");
        }
        microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(FORMAT);
    }
    
    public void startVoiceCapture() {
        if (microphone != null && !isCapturing) {
            isCapturing = true;
            microphone.start();
            startCaptureThread();
        }
    }
    
    private void startCaptureThread() {
        Thread captureThread = new Thread(() -> {
            byte[] buffer = new byte[4096];
            while (isCapturing) {
                int count = microphone.read(buffer, 0, buffer.length);
                if (count > 0) {
                    // Here we'll handle the captured audio data
                    // For now, we can just store it or send it through the network
                }
            }
        });
        captureThread.start();
    }
    
    public void stopVoiceCapture() {
        isCapturing = false;
        if (microphone != null) {
            microphone.stop();
        }
    }
    
    public void cleanup() {
        stopVoiceCapture();
        if (microphone != null) {
            microphone.close();
        }
    }
} 