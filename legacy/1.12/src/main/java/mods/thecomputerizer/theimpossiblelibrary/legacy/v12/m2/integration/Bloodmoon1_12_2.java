package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.integration;

import lumien.bloodmoon.Bloodmoon;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.BloodmoonAPI;

public class Bloodmoon1_12_2 extends BloodmoonAPI {

    @Override public boolean isBloodMoon() {
        return Bloodmoon.proxy.isBloodmoon();
    }
}