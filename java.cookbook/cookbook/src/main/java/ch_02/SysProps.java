package ch_02;

import java.text.MessageFormat;

public class SysProps {
    static int getSysPropsCount() {
        var props = System.getProperties();

        props.forEach((k, v) -> {
            var msg = MessageFormat.format("{0} -> {1}", k, v);
            System.out.println(msg);
        });

        return props.size();
    }
}
