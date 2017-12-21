/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.testmod;

public class ModInfo {
    public static final String PACKAGE_NAME = "com.fireball1725.";
    public static final String MOD_ID = "testmod";
    public static final String MOD_NAME = "Mod to test FireLib";
    public static final String VERSION_BUILD = "@VERSION@";
    public static final String MINECRAFT_VERSION = "@MCVERSION@";
    public static final String DEPENDENCIES = "";
    public static final String SERVER_PROXY_CLASS = PACKAGE_NAME + MOD_ID + ".proxy.ServerProxy";
    public static final String CLIENT_PROXY_CLASS = PACKAGE_NAME + MOD_ID + ".proxy.ClientProxy";
    public static final String FINGERPRINT = "@FINGERPRINT@";
    public static final String GUI_FACTORY = PACKAGE_NAME + MOD_ID + ".common.config.ConfigGuiFactory";
    public static final String PATH_INTEGRATIONS = PACKAGE_NAME + MOD_ID + ".common.integrations.";
}