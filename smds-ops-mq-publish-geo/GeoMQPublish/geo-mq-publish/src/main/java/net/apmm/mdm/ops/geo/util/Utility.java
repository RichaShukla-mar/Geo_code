package net.apmm.mdm.ops.geo.util;

public class Utility {
    public static PublishService getMQInterface() {
        PublishService mqInterface;
        mqInterface = new PublishGeographyViaMQ();
        return mqInterface;
    }
}
