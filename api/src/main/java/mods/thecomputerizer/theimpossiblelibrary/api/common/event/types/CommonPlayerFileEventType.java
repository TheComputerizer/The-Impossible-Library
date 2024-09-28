package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;

import java.io.File;
import java.util.Objects;

public abstract class CommonPlayerFileEventType<E> extends CommonPlayerEventType<E> {

    protected EventFieldWrapper<E,File> directory;
    protected EventFieldWrapper<E,String> uuid;

    protected CommonPlayerFileEventType(CommonType<?> type) {
        super(type);
    }

    public File getDirectory() {
        return this.directory.get(this.event);
    }

    public File getPlayerFile(String suffix) {
        if("dat".equals(suffix)) throw new IllegalArgumentException("The suffix 'dat' is reserved");
        File directory = getDirectory();
        if(Objects.isNull(directory)) throw new NullPointerException("Unable to get player file of null directory");
        String uuid = getUUID();
        if(Objects.isNull(uuid)) throw new NullPointerException("Unable to get file of player with null UUID");
        return new File(directory,uuid+"."+suffix);
    }

    public String getUUID() {
        return this.uuid.get(this.event);
    }

    @Override public void populate() {
        super.populate();
        this.directory = wrapDirectoryField();
        this.uuid = wrapUUIDField();
    }

    protected abstract EventFieldWrapper<E,File> wrapDirectoryField();
    protected abstract EventFieldWrapper<E,String> wrapUUIDField();
}