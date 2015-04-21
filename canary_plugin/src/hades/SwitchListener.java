package hades;

import com.google.common.collect.ImmutableMap;
import net.canarymod.api.factory.ChatComponentFactory;
import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.blocks.properties.BlockBooleanProperty;
import net.canarymod.api.world.blocks.properties.BlockProperty;
import net.canarymod.hook.HookHandler;
import net.canarymod.hook.player.BlockRightClickHook;
import net.canarymod.hook.world.BlockUpdateHook;
import net.canarymod.plugin.PluginListener;

import java.util.Map;

/**
 * Created by thoma_000 on 13/04/2015.
 */
public class SwitchListener implements PluginListener {
    @HookHandler
    public void onSwitchClick(BlockRightClickHook blockRightClickHook) {
        Block block = blockRightClickHook.getBlockClicked();
        BlockType type = block.getType();
        if (type.equals(BlockType.Lever)) {
            ImmutableMap<BlockProperty, Comparable> properties = block.getProperties();
            for (Map.Entry<BlockProperty, Comparable> prop: properties.entrySet()) {
                System.out.println(prop.getValue());
            }
        }
    }
}
