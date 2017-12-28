/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.firelib.guimaker.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class NetworkHelper {
    public static NetworkRegistry.TargetPoint getTargetPoint(int dim, BlockPos blockPos, int range) {
        return new NetworkRegistry.TargetPoint(dim, blockPos.getX(), blockPos.getY(), blockPos.getZ(), range);
    }

    public static void writeBlockPos(ByteBuf byteBuf, BlockPos blockPos) {
        byteBuf.writeInt(blockPos.getX());
        byteBuf.writeInt(blockPos.getY());
        byteBuf.writeInt(blockPos.getZ());
    }

    public static BlockPos readBlockPos(ByteBuf byteBuf) {
        return new BlockPos(byteBuf.readInt(), byteBuf.readInt(), byteBuf.readInt());
    }
}
