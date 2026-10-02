package com.nukateam.cgs.client.renderers.weapon;

import com.nukateam.cgs.client.layers.HammerHeadLayer;

public class HammerRenderer extends BaseWeaponRenderer {
    public HammerRenderer() {
        super();
        withRenderLayer(new HammerHeadLayer<>(this));
    }
}
