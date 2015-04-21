package hades;

import net.canarymod.chat.Colors;
import net.canarymod.hook.HookHandler;
import net.canarymod.hook.player.ConnectionHook;
import net.canarymod.plugin.PluginListener;

/**
 * Created by ibbo on 13/04/2015.
 */
public class HelloListener implements PluginListener {

    @HookHandler
    public void onLogin(ConnectionHook hook) {
        hook.getPlayer().message(Colors.YELLOW+"Hello World," + hook.getPlayer().getName());
    }
}
