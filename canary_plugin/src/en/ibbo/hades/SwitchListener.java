package en.ibbo.hades;

import com.google.common.collect.ImmutableMap;
import en.ibbo.hades.server.SendableEvent;
import en.ibbo.hades.server.SwitchEvent;
import net.canarymod.api.chat.ChatComponent;
import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.blocks.Sign;
import net.canarymod.api.world.blocks.properties.BlockProperty;
import net.canarymod.hook.HookHandler;
import net.canarymod.hook.player.BlockRightClickHook;
import net.canarymod.plugin.PluginListener;
import net.minecraft.block.BlockWallSign;

import java.util.ArrayList;
import java.util.List;
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
            // Try and find a neighbouring sign block.
            List<Block> blocks = getAllNeighbours(block);
            Block sign = findSign(blocks);
            String extraInfo = "";
            if (sign == null) {
                System.out.println("Did not find a sign next to this lever.");
            } else {
                System.out.println("Found a sign!");
                Sign signTile = (Sign)sign.getTileEntity();
                ChatComponent[] lines = signTile.getLines();
                StringBuilder allLines = new StringBuilder();
                for (ChatComponent c: lines) {
                    String line = c.getFullText();
                    line = line.trim();
                    allLines.append(line);
                    allLines.append(" ");
                }
                extraInfo = allLines.toString();
                extraInfo = extraInfo.trim();
            }
            for (Map.Entry<BlockProperty, Comparable> prop: properties.entrySet()) {
                if (prop.getKey().getName().equals("powered")) {
                    Boolean value = (Boolean)prop.getValue();
                    String id = "1";
                    eventQueue.add(new SwitchEvent(id, !value, extraInfo));
                }
            }
        }
    }

    private List<Block> getAllNeighbours(Block block) {
        int[][] nearestOffsets = {
                {1, 0, 0},
                {-1, 0, 0},
                {0, 1, 0},
                {0, -1, 0},
                {0, 0, 1},
                {0, 0, -1}
        };
        List<Block> neighbours = new ArrayList<>();
        for (int[] blockOffset: nearestOffsets) {
            neighbours.add(block.getRelative(blockOffset[0], blockOffset[1], blockOffset[2]));
        }
        return neighbours;
    }

    private Block findSign(List<Block> blocks) {
        for (Block testBlock: blocks) {
            if (testBlock.getType().equals(BlockType.WallSign)) {
                return testBlock;
            }
        }
        return null;
    }
}
