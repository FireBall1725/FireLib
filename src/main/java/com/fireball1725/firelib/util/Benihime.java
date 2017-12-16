package com.fireball1725.firelib.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Benihime {
    private static final List<UUID> uuidList = new ArrayList<>();

    static {
        uuidList.add(UUID.fromString("e43e9766-f903-48e1-818f-d41bb48d80d5")); // FireBall1725
        uuidList.add(UUID.fromString("eb8ef7b9-3199-4a50-99e8-76a48baa6fdf")); // glasspelican
        uuidList.add(UUID.fromString("ed2d2e2c-654c-4e85-aa99-300f13561515")); // Phylogeny
        uuidList.add(UUID.fromString("3af04d2c-7629-4576-afc2-7867684d281d")); // Blay09
    }

    public static boolean hasUser(UUID playerUUID) {
        return uuidList.contains(playerUUID);
    }
}
