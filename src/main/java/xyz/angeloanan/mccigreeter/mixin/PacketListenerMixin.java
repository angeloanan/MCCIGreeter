package xyz.angeloanan.mccigreeter.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.angeloanan.mccigreeter.client.MCCIGreeterClient;
import xyz.angeloanan.mccigreeter.client.MCCIGreeterModConfig;

import java.util.Random;

import static xyz.angeloanan.mccigreeter.client.MCCIGreeterClient.LOGGER;

@Mixin(ClientPacketListener.class)
public abstract class PacketListenerMixin {
    @Unique
    private final MCCIGreeterModConfig config = MCCIGreeterClient.config;

    @Unique
    private final Random random = new Random();

    @Shadow
    public abstract void sendChat(String string);

    @Inject(method = "setTitleText", at = @At("TAIL"))
    private void setTitleText(ClientboundSetTitleTextPacket cbstp, CallbackInfo ci) {
        String title = cbstp.getText().getString();
        LOGGER.info("Title text set to: %s".formatted(title));
        switch (title) {
            case "Sky Battle":
            case "Hole in the Wall":
            case "Round 1": // Battle Box, TGTTOS(?)
            case "Leap 1":
                sendPreGameMessage();
                break;
            case "Victory!":
            case "Game Over!":
                sendPostGameMessage();
                break;
            default:
                break;
        }
    }

    @Unique
    private void sendPreGameMessage() {
        if (!config.preGameMessageEnabled) return;

        if (config.upliftingMode) {
            int choice = random.nextInt(config.preGameMessage.length);
            sendChat(config.preGameMessage[choice]);
        } else {
            sendChat("glhf");
        }
    }

    @Unique
    private void sendPostGameMessage() {
        if (!config.postGameMessageEnabled) return;

        if (config.upliftingMode) {
            int choice = random.nextInt(config.postGameMessage.length);
            sendChat(config.postGameMessage[choice]);
        } else {
            sendChat("gg");
        }
    }

}
