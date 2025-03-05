package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.client.render.GL1_19;

import java.util.Objects;

public class GL1_19_4 extends GL1_19 {
    
    @Override protected VertexConsumer normal(VertexConsumer consumer, float x, float y, float z) {
        return Objects.nonNull(this.workingPose) ?
                consumer.normal(this.workingPose.normal(),x,y,z) : consumer.normal(x,y,z);
    }
    
    @Override protected VertexConsumer vertex(double x, double y, double z) {
        return Objects.nonNull(this.workingPose) ?
                this.workingBuffer.vertex(this.workingPose.pose(),(float)x,(float)y,(float)z) :
                this.workingBuffer.vertex(x,y,z);
    }
}