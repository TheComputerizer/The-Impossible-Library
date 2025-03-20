package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.render.GL1_20;

import java.util.Objects;

public class GL1_20_6 extends GL1_20 {
    
    @Override protected VertexConsumer normal(VertexConsumer consumer, float x, float y, float z) {
        return Objects.nonNull(this.workingPose) ?
                consumer.normal(this.workingPose,x,y,z) : consumer.normal(x,y,z);
    }
}
