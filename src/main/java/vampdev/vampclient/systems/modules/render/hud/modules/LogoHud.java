

package vampdev.vampclient.systems.modules.render.hud.modules;

import vampdev.vampclient.renderer.GL;
import vampdev.vampclient.renderer.Renderer2D;
import vampdev.vampclient.settings.DoubleSetting;
import vampdev.vampclient.settings.Setting;
import vampdev.vampclient.settings.SettingGroup;
import vampdev.vampclient.systems.modules.render.hud.HUD;
import vampdev.vampclient.systems.modules.render.hud.HudRenderer;
import net.minecraft.util.Identifier;
import vampdev.vampclient.utils.Utils;

public class LogoHud extends HudElement {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> scale = sgGeneral.add(new DoubleSetting.Builder()
        .name("scale")
        .description("The scale of the logo.")
        .defaultValue(3)
        .min(0.1)
        .sliderRange(0.1, 10)
        .build()
    );

    private final Identifier TEXTURE = new Identifier("vamp", "textures/meteor.png");

    public LogoHud(HUD hud) {
        super(hud, "logo", "Shows the Meteor logo in the HUD.");
    }

    @Override
    public void update(HudRenderer renderer) {
        box.setSize(64 * scale.get(), 64 * scale.get());
    }

    @Override
    public void render(HudRenderer renderer) {
        GL.bindTexture(TEXTURE);
        Renderer2D.TEXTURE.begin();
        Renderer2D.TEXTURE.texQuad(box.getX(), box.getY(), box.width, box.height, Utils.WHITE);
        Renderer2D.TEXTURE.render(null);
    }
}
