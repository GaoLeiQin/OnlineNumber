import com.ledo.manager.FileManager;
import com.ledo.task.BaseTask;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.ledo.common.FileConstant.ZONE_OPT_PATH;

public class FileTest extends BaseTask {
    @Test
    public void getLinuxInfo() {
        HashMap<String, String> zoneOpt = FileManager.getInstance().getZoneOpt(ZONE_OPT_PATH);
        for (Map.Entry<String, String> zoneOptEntry : zoneOpt.entrySet()) {
            String[] idAndIp = zoneOpt.get(zoneOptEntry.getKey()).split("=");
            if (idAndIp.length > 2) {
                int optOrId = Integer.valueOf(idAndIp[0]);
                String innerIp = idAndIp[1];
                String outerIp = idAndIp[2];
                System.out.println(optOrId + " " + innerIp + " " +outerIp);
            }
        }
    }
}
