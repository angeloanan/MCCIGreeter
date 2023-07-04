package xyz.angeloanan.mccigreeter.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MCCIGreeterClient implements ClientModInitializer {
    public static final String MOD_ID = "mcci_greeter";
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static MCCIGreeterModConfig config;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(MCCIGreeterModConfig.class, GsonConfigSerializer::new);

        config = AutoConfig.getConfigHolder(MCCIGreeterModConfig.class).getConfig();
    }
}
