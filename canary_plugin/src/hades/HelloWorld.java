package hades;

import net.canarymod.Canary;
import net.canarymod.plugin.Plugin;

/**
 * Created by ibbo on 13/04/2015.
 */
public class HelloWorld extends Plugin {

    @Override
    public boolean enable() {
        Canary.hooks().registerListener(new HelloListener(), this);
        Canary.hooks().registerListener(new SwitchListener(), this);
        getLogman().info("Enabling " + getName() + " Version " + getVersion());
        getLogman().info("Authored by " + getAuthor());
        return true;
    }

    @Override
    public void disable() {
        getLogman().info("Disabling " + getName());
    }
}
