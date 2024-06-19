package dev.thomasglasser.sherdsapi;

import dev.thomasglasser.sherdsapi.impl.SherdsApiSherds;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SherdsApi {
    public static final String MOD_ID = "sherdsapi";
    public static final String MOD_NAME = "Sherds API";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        LOGGER.info("Initializing {} for {} in a {} environment...", MOD_NAME, TommyLibServices.PLATFORM.getPlatformName(), TommyLibServices.PLATFORM.getEnvironmentName());

        SherdsApiSherds.init();
    }

    public static ResourceLocation modLoc(String s)
    {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, s);
    }
}