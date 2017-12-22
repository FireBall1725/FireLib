/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.firelib.renderer.obj;

import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.Map;

/**
 * Common interface for advanced model loading from files, based on file suffix
 * Model support can be queried through the {@link #getSupportedSuffixes()} method.
 * Instances can be created by calling {@link #loadModel(String)} with a class-loadable-path
 *
 * @author cpw
 */
@SideOnly(Side.CLIENT)
public class AdvancedModelLoader {
    private static Map<String, IModelCustomLoader> instances = Maps.newHashMap();

    static {
        registerModelHandler(new ObjModelLoader());
    }

    /**
     * Register a new model handler
     *
     * @param modelHandler The model handler to register
     */
    public static void registerModelHandler(IModelCustomLoader modelHandler) {
        for (String suffix : modelHandler.getSuffixes()) {
            instances.put(suffix, modelHandler);
        }
    }

    /**
     * Load the model from the supplied classpath resolvable resource name
     *
     * @param resource The resource name
     * @return A model
     * @throws IllegalArgumentException if the resource name cannot be understood
     * @throws ModelFormatException     if the underlying model handler cannot parse the model format
     */
    public static IModelCustom loadModel(ResourceLocation resource) throws IllegalArgumentException, ModelFormatException {
        String name = resource.getResourcePath();
        int i = name.lastIndexOf('.');
        if (i == -1) {
            FMLLog.severe("The resource name %s is not valid", resource);
            throw new IllegalArgumentException("The resource name is not valid");
        }
        String suffix = name.substring(i + 1);
        IModelCustomLoader loader = instances.get(suffix);
        if (loader == null) {
            FMLLog.severe("The resource name %s is not supported", resource);
            throw new IllegalArgumentException("The resource name is not supported");
        }

        return loader.loadInstance(resource);
    }

    public static Collection<String> getSupportedSuffixes() {
        return instances.keySet();
    }
}
