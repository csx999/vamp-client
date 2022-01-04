

package vampdev.vampclient.gui.widgets;

import vampdev.vampclient.gui.WidgetScreen;
import vampdev.vampclient.gui.widgets.containers.WHorizontalList;
import vampdev.vampclient.gui.widgets.pressable.WButton;
import vampdev.vampclient.gui.widgets.pressable.WMinus;
import vampdev.vampclient.systems.accounts.Account;
import vampdev.vampclient.systems.accounts.Accounts;
import vampdev.vampclient.utils.network.MeteorExecutor;
import vampdev.vampclient.utils.render.color.Color;
import vampdev.vampclient.VampClient;

public abstract class WAccount extends WHorizontalList {
    public Runnable refreshScreenAction;

    private final WidgetScreen screen;
    private final Account<?> account;

    public WAccount(WidgetScreen screen, Account<?> account) {
        this.screen = screen;
        this.account = account;
    }

    protected abstract Color loggedInColor();

    protected abstract Color accountTypeColor();

    @Override
    public void init() {
        // Head
        add(theme.texture(32, 32, account.getCache().shouldRotateHeadTexture() ? 90 : 0, account.getCache().getHeadTexture()));

        // Name
        WLabel name = add(theme.label(account.getUsername())).widget();
        if (VampClient.mc.getSession().getUsername().equalsIgnoreCase(account.getUsername())) name.color = loggedInColor();

        // Type
        WLabel label = add(theme.label("(" + account.getType() + ")")).expandCellX().right().widget();
        label.color = accountTypeColor();

        // Login
        WButton login = add(theme.button("Login")).widget();
        login.action = () -> {
            login.minWidth = login.width;
            login.set("...");
            screen.locked = true;

            MeteorExecutor.execute(() -> {
                if (account.login()) {
                    name.set(account.getUsername());

                    Accounts.get().save();

                    screen.taskAfterRender = refreshScreenAction;
                }

                login.minWidth = 0;
                login.set("Login");
                screen.locked = false;
            });
        };

        // Remove
        WMinus remove = add(theme.minus()).widget();
        remove.action = () -> {
            Accounts.get().remove(account);
            if (refreshScreenAction != null) refreshScreenAction.run();
        };
    }
}
