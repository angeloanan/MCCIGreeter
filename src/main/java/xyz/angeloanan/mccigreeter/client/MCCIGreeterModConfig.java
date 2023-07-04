package xyz.angeloanan.mccigreeter.client;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = MCCIGreeterClient.MOD_ID)
public class MCCIGreeterModConfig implements ConfigData {
    @Comment("""
        Whether to make AutoGG and AutoGLHF messages uplifting or not.
        If true, uses the messages defined below.
    """)
    public boolean upliftingMode = false;

    @Comment("The message to send before the game starts")
    public String[] preGameMessage = {
            "Reminder to drink water and stretch. glhf! :D",
            "Don't forget to stretch. glhf! :D",
            "Posture check! Sit with your back straight. glhf! :D",
            "Just a reminder that you should take a break after a while. glhf! :D",
            "Have you ate or drink recently? If not, you should do that. glhf! :D",
            "Reminder to drink water. glhf! :D",
            "Good luck and have fun, y'all! :D",
    };

    @Comment("The message to send after the game ends")
    public String[] postGameMessage = {
            "gg!",
            "ggwp!",
            "gg, thanks for the game!",
            "gg, thanks for playing! :D",
    };
}
