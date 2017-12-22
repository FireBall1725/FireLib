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

import com.fireball1725.firelib.FireMod;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import net.minecraftforge.fml.common.FMLModContainer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/*
 * Originally created by InfinityRaider
 * Adapted for FireLib
 *
 * Original Source Code:
 * https://github.com/InfinityRaider/InfinityLib/blob/master/src/main/java/com/infinityraider/infinitylib/utility/ModEventHandlerHack.java
 *
 * Original License: MIT
 * https://github.com/InfinityRaider/InfinityLib/blob/master/LICENSE
 */

public class ModEventHandlerHack {
    private static final List<Method> METHODS = getMethods();
    private static final Field FIELD = getField();

    public static void doHack(FireMod instance) {
        Loader.instance().getModList().stream()
                .filter(modContainer -> modContainer instanceof FMLModContainer)
                .filter(modContainer -> modContainer.getModId().equals(instance.getModId()))
                .forEach(modContainer -> addModEventHandlerMethods((FMLModContainer) modContainer));
    }

    private static void addModEventHandlerMethods(FMLModContainer modContainer) {
        ListMultimap<Class<? extends FMLEvent>, Method> methodMap = getModEventMap(modContainer);
        for (Method method : METHODS) {
            Class<? extends FMLEvent> paramClass = (Class<? extends FMLEvent>) method.getParameterTypes()[0];
            methodMap.put(paramClass, method);
        }
    }

    private static ListMultimap<Class<? extends FMLEvent>, Method> getModEventMap(FMLModContainer modContainer) {
        try {
            return (ListMultimap<Class<? extends FMLEvent>, Method>) FIELD.get(modContainer);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Caught error while trying to retrieve the FML mod container even methods map");
        }
    }

    private static List<Method> getMethods() {
        List<Method> foundMethods = new ArrayList<>();
        for (Method method : FireMod.class.getDeclaredMethods()) {
            for (Annotation a : method.getAnnotations()) {
                if (a.annotationType().equals(Mod.EventHandler.class)) {
                    if (method.getParameterTypes().length == 1 && FMLEvent.class.isAssignableFrom(method.getParameterTypes()[0])) {
                        method.setAccessible(true);
                        foundMethods.add(method);
                    }
                }
            }
        }

        return ImmutableList.copyOf(foundMethods);
    }

    private static Field getField() {
        Field[] fields = FMLModContainer.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == ListMultimap.class) {
                field.setAccessible(true);
                return field;
            }
        }

        // Should not ever happen
        throw new RuntimeException("Could not retrieve the FML mod container event methods field");
    }
}
