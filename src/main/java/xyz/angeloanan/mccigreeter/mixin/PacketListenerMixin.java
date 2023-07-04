package xyz.angeloanan.mccigreeter.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ClientPacketListener.class)
public abstract class PacketListenerMixin {
    @Unique
    private static final String[] preGameMessage = {
            "This is your reminder to drink water and stretch. glhf! :D",
            "Don't forget to stretch and drink water. glhf! :D",
            "Reminder to drink water. glhf! :D",
            "Good luck and have fun, y'all! :D",
    };

    @Unique
    private static final String[] postGameMessage = {
            "gg!",
            "ggwp!",
            "gg, thanks for the game!",
            "gg, thanks for playing! :D",
    };

    @Unique
    private final Random random = new Random();


    @Shadow
    @Final
    private static Logger LOGGER;

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
                int preGameMessageChoice = random.nextInt(preGameMessage.length);
                sendChat(preGameMessage[preGameMessageChoice]);
                break;
            case "Victory!":
            case "Game Over!":
                // Randomly send a message after the game
                int postGameMessageChoice = random.nextInt(postGameMessage.length);
                sendChat(postGameMessage[postGameMessageChoice]);
                break;
            default:
                break;
        }
    }
}
