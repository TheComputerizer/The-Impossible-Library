package mods.thecomputerizer.theimpossiblelibrary.fabric.v21.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.fabric.network.FabricNetwork;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;


public class NetworkFabric1_21 implements FabricNetwork<Object,Object> {

    @Override public void registerMessage(MessageDirectionInfo<Object> dir, int id) {
        registerMessageCustomPayload(dir,this::streamCodec);
    }
    
    protected <B extends ByteBuf> StreamCodec<B,MessageWrapperFabric1_21> streamCodec(Object dir) {
        StreamEncoder<B,MessageWrapperFabric1_21> encoder = (buf,payload) -> payload.encode(buf);
        StreamDecoder<B,MessageWrapperFabric1_21> decoder = buf -> MessageWrapperFabric1_21.getInstance(this,dir,buf);
        return StreamCodec.of(encoder,decoder);
    }
}