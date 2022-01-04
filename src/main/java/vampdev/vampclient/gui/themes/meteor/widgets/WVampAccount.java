

package vampdev.vampclient.gui.themes.meteor.widgets;

import vampdev.vampclient.gui.WidgetScreen;
import vampdev.vampclient.gui.themes.meteor.VampWidget;
import vampdev.vampclient.gui.widgets.WAccount;
import vampdev.vampclient.systems.accounts.Account;
import vampdev.vampclient.utils.render.color.Color;

public class WVampAccount extends WAccount implements VampWidget {
    public WVampAccount(WidgetScreen screen, Account<?> account) {
        super(screen, account);
    }

    @Override
    protected Color loggedInColor() {
        return theme().loggedInColor.get();
    }

    @Override
    protected Color accountTypeColor() {
        return theme().textSecondaryColor.get();
    }
}
