

package vampdev.vampclient.systems.modules.render.hud.modules;

import vampdev.vampclient.gui.screens.HudElementScreen;
import vampdev.vampclient.gui.tabs.builtin.HudTab;
import vampdev.vampclient.settings.Settings;
import vampdev.vampclient.systems.modules.render.hud.BoundingBox;
import vampdev.vampclient.systems.modules.render.hud.HUD;
import vampdev.vampclient.systems.modules.render.hud.HudRenderer;
import vampdev.vampclient.utils.Utils;
import vampdev.vampclient.utils.misc.ISerializable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;

public abstract class HudElement implements ISerializable<HudElement> {
    public final String name, title;
    public final String description;

    public boolean active;
    public final boolean defaultActive;

    protected final HUD hud;

    public final Settings settings = new Settings();
    public final BoundingBox box = new BoundingBox();

    protected final MinecraftClient mc;

    public HudElement(HUD hud, String name, String description, boolean defaultActive) {
        this.hud = hud;
        this.name = name;
        this.title = Utils.nameToTitle(name);
        this.description = description;
        this.defaultActive = defaultActive;
        this.mc = MinecraftClient.getInstance();
    }

    public HudElement(HUD hud, String name, String description) {
        this.hud = hud;
        this.name = name;
        this.title = Utils.nameToTitle(name);
        this.description = description;
        this.defaultActive = true;
        this.mc = MinecraftClient.getInstance();
    }

    public void toggle() {
        active = !active;
    }

    public abstract void update(HudRenderer renderer);

    public abstract void render(HudRenderer renderer);

    protected boolean isInEditor() {
        return (HudTab.INSTANCE != null && HudTab.INSTANCE.isScreen(mc.currentScreen)) || mc.currentScreen instanceof HudElementScreen || !Utils.canUpdate();
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        tag.putString("name", name);
        tag.putBoolean("active", active);
        tag.put("settings", settings.toTag());
        tag.put("box", box.toTag());

        return tag;
    }

    @Override
    public HudElement fromTag(NbtCompound tag) {
        active = tag.contains("active") ? tag.getBoolean("active") : defaultActive;
        if (tag.contains("settings")) settings.fromTag(tag.getCompound("settings"));
        box.fromTag(tag.getCompound("box"));

        return this;
    }
}
