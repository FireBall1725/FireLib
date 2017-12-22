/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
