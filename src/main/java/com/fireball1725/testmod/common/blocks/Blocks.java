/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.testmod.common.blocks;

import com.fireball1725.firelib.blocks.BlockBase;
import com.fireball1725.firelib.blocks.IFireBlocks;
import com.fireball1725.testmod.common.blocks.test.TestBlock;
import com.fireball1725.testmod.common.blocks.test.TestBlock2;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public enum Blocks implements IFireBlocks {
    TEST_BLOCK(TestBlock.class),
    TEST_BLOCK_2(TestBlock2.class)
    ;

    private final Class<? extends BlockBase> blockClass;
    private final Class<? extends ItemBlock> itemBlockClass;
    private Block block;

    Blocks(Class<? extends BlockBase> blockClass) {
        this(blockClass, ItemBlock.class);
    }

    Blocks(Class<? extends BlockBase> blockClass, Class<? extends ItemBlock> itemBlockClass) {
        this.blockClass = blockClass;
        this.itemBlockClass = itemBlockClass;
    }

    @Override
    public Class<? extends BlockBase> getBlockClass() {
        return this.blockClass;
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return this.itemBlockClass;
    }

    @Override
    public void setBlock(Block block) {
        this.block = block;
    }

    @Override
    public Block getBlock() {
        return this.block;
    }
}
