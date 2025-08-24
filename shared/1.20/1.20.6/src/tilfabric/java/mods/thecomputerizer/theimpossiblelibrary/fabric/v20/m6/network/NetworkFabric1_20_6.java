package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.message.MessageDirectionInfo;
import mods.thecomputerizer.theimpossiblelibrary.fabric.network.FabricNetwork;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;

public class NetworkFabric1_20_6 implements FabricNetwork<Object,Object> {

    @Override public void registerMessage(MessageDirectionInfo<Object> dir, int id) {
        registerMessageCustomPayload(dir,this::streamCodec);
    }
    
    protected <B extends ByteBuf> StreamCodec<B,MessageWrapperFabric1_20_6> streamCodec(Object dir) {
        StreamEncoder<B,MessageWrapperFabric1_20_6> encoder = (buf,payload) -> payload.encode(buf);
        StreamDecoder<B,MessageWrapperFabric1_20_6> decoder = buf -> MessageWrapperFabric1_20_6.getInstance(this,dir,buf);
        return StreamCodec.of(encoder,decoder);
    }
}