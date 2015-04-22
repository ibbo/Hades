package en.ibbo.hades;

import com.google.common.collect.ImmutableMap;
import en.ibbo.hades.server.SendableEvent;
import en.ibbo.hades.server.SwitchEvent;
import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.blocks.properties.BlockProperty;
import net.canarymod.hook.HookHandler;
import net.canarymod.hook.player.BlockRightClickHook;
import net.canarymod.plugin.PluginListener;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class SwitchListener implements PluginListener {

    private final BlockingQueue<SendableEvent> eventQueue;

    public SwitchListener(BlockingQueue<SendableEvent> eventQueue) {
        this.eventQueue = eventQueue;
    }

    @HookHandler
    public void onSwitchClick(BlockRightClickHook blockRightClickHook) {
        Block block = blockRightClickHook.getBlockClicked();
        BlockType type = block.getType();
        if (type.equals(BlockType.Lever)) {
            ImmutableMap<BlockProperty, Comparable> properties = block.getProperties();
            for (Map.Entry<BlockProperty, Comparable> prop: properties.entrySet()) {
                if (prop.getKey().getName().equals("powered")) {
                    Boolean value = (Boolean)prop.getValue();
                    String id = "1";
                    eventQueue.add(new SwitchEvent(id, !value));
                }
            }
        }
    }
}
