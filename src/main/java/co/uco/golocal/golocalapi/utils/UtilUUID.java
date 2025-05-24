package co.uco.golocal.golocalapi.utils;

import java.util.UUID;

public class UtilUUID {
    private final static String UUID_STRING = "ffffffff-ffff-ffff-ffff-ffffffffffff";
    private final static UUID uuidDefaultValue = UUID.fromString(UUID_STRING);


    private UtilUUID() {

    }

    public static  UUID generateNewUUID(){
        return UUID.randomUUID();
    }

    public static String getUuidString() {
        return UUID_STRING;
    }

    public static  UUID getUuidDefaultValue() {
        return uuidDefaultValue;
    }
}