

package vampdev.vampclient.utils.network;

import vampdev.vampclient.utils.Init;
import vampdev.vampclient.utils.InitStage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MeteorExecutor {
    public static ExecutorService executor;

    @Init(stage = InitStage.Pre)
    public static void init() {
        executor = Executors.newSingleThreadExecutor();
    }

    public static void execute(Runnable task) {
        executor.execute(task);
    }
}
