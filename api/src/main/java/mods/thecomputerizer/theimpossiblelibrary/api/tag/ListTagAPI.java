package mods.thecomputerizer.theimpossiblelibrary.api.tag;

public interface ListTagAPI extends BaseTagAPI {

    void addTag(BaseTagAPI tag);
    Iterable<BaseTagAPI> iterable();
}