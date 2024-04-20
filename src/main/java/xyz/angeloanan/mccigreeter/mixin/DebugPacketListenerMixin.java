package xyz.angeloanan.mccigreeter.mixin;


import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.angeloanan.mccigreeter.client.MCCIGreeterClient;
import xyz.angeloanan.mccigreeter.client.MCCIGreeterModConfig;

import java.util.Objects;

import static xyz.angeloanan.mccigreeter.client.MCCIGreeterClient.LOGGER;

@Mixin(ClientPacketListener.class)
public class DebugPacketListenerMixin {
  @Unique
  private final MCCIGreeterModConfig config = MCCIGreeterClient.config;

  @Inject(method = "handleAddObjective", at = @At("TAIL"))
  private void handleAddObjective(ClientboundSetObjectivePacket packet, CallbackInfo ci) {
    if (!config.debug) return;
    LOGGER.info("Scoreboard objective %s (%s) has been added".formatted(packet.getObjectiveName(), packet.getDisplayName()));
    LOGGER.info("Display string: %s".formatted(packet.getDisplayName().getString()));
  }

  @Inject(method = "handleSetScore", at = @At("TAIL"))
  private void handleSetScore(ClientboundSetScorePacket packet, CallbackInfo ci) {
    if (!config.debug) return;

    try {
      var objectiveName = Objects.requireNonNullElse(packet.getObjectiveName(), "null");
      var method = packet.getMethod();
      var owner = Objects.requireNonNullElse(packet.getOwner(), "UNKNOWN");
      switch (method) {
        case CHANGE -> LOGGER.info("Scoreboard %s has player %s value changed to %d".formatted(objectiveName, owner, packet.getScore()));
        case REMOVE -> LOGGER.info("Scoreboard %s has player %s value reset".formatted(objectiveName, owner));
      }
    } catch (NoSuchMethodError e) {
      LOGGER.info("Unknown scoreboard set score packet has been sent");
    }
  }

  @Inject(method = "handleSetDisplayObjective", at = @At("TAIL"))
  private void handleSetDisplayObjective(ClientboundSetDisplayObjectivePacket packet, CallbackInfo ci) {
    if (!config.debug) return;
    LOGGER.info("Scoreboard objective %s has been displayed".formatted(packet.getObjectiveName()));
  }

}
