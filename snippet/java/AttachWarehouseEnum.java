package cn.dces.btoe.domain.entity;

import com.google.common.base.Strings;
import java.util.Optional;

import java.util.Arrays;

public enum AttachWarehouseEnum {
    LOCAL("本地"),
    OBS("华为OBS");

    private final String label;

    AttachWarehouseEnum(String label) {
        this.label = label;
    }

    public String label() {
        return this.label;
    }

    public static Optional<AttachWarehouseEnum> of(String name) {
        if (Strings.isNullOrEmpty(name)) {
            return null;
        }
        return Arrays.stream(AttachWarehouseEnum.values()).filter(m -> m.name().equals(name.toUpperCase())).findFirst();
    }
}
