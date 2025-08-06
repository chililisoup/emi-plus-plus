package concerrox.emixx.content

import concerrox.emixx.content.ScreenManager.ENTRY_SIZE
import concerrox.emixx.content.stackgroup.GroupedEmiStack
import dev.emi.emi.runtime.EmiDrawContext
import dev.emi.emi.screen.EmiScreenManager

// TODO: this needs to be refactored as it is quiet a mess
object Layout {

    data class REn(val x: Int, val y: Int, var type: Int)

    enum class RF(val bit: Int) {
        LEFT(1), TOP(2), RIGHT(4), BOTTOM(8),
    }

    var si = -10
    var isClean = true

    var isTextureDirty = true
        set(value) {
            field = value
            // TODO: this is not safe
            StackManager.stackGrid =
                Array(ScreenManager.indexScreenSpace.th + 9) { arrayOfNulls(ScreenManager.indexScreenSpace.tw + 9) }
        }

    fun show(screenSpace: EmiScreenManager.ScreenSpace, context: EmiDrawContext) {
        if (isTextureDirty) {
            StackManager.stackTextureGrid.clear()
            for (y in 0 until screenSpace.th) {
                for (x in 0 until screenSpace.tw) {
                    val stack = StackManager.stackGrid[y][x]
                    if (stack != null && stack is GroupedEmiStack<*>) {
                        val obj = REn(x, y, 0)
                        if (y == 0 || ge(y - 1, x)?.stackGroup != stack.stackGroup) {
                            obj.type = obj.type or RF.TOP.bit
                        }
                        if (x == 0 || ge(y, x - 1)?.stackGroup != stack.stackGroup) {
                            obj.type = obj.type or RF.LEFT.bit
                        }
                        if (y == screenSpace.th - 1 || ge(y + 1, x)?.stackGroup != stack.stackGroup) {
                            obj.type = obj.type or RF.BOTTOM.bit
                        }
                        if (x == screenSpace.tw - 1 || ge(y, x + 1)?.stackGroup != stack.stackGroup) {
                            obj.type = obj.type or RF.RIGHT.bit
                        }
                        if (obj.type != 0) StackManager.stackTextureGrid.add(obj)
                    }
                }
            }
        }
        isTextureDirty = false
        render(screenSpace, context)
    }

    fun render(screenSpace: EmiScreenManager.ScreenSpace, context: EmiDrawContext) {
        StackManager.stackTextureGrid.forEach {
            val px = screenSpace.tx + it.x * ENTRY_SIZE
            val py = screenSpace.ty + it.y * ENTRY_SIZE
            if (it.check(RF.TOP)) {
                context.fill(px, py, ENTRY_SIZE, 1, 0x66FFFFFF)
            }
            if (it.check(RF.LEFT)) {
                context.fill(px, py, 1, ENTRY_SIZE, 0x66FFFFFF)
            }
            if (it.check(RF.BOTTOM)) {
                context.fill(px, py + 17, ENTRY_SIZE, 1, 0x66FFFFFF)
            }
            if (it.check(RF.RIGHT)) {
                context.fill(px + 17, py, 1, ENTRY_SIZE, 0x66FFFFFF)
            }
        }
    }

    private fun ge(y: Int, x: Int): GroupedEmiStack<*>? {
        return StackManager.stackGrid.getOrNull(y)?.getOrNull(x) as? GroupedEmiStack<*>
    }

}

inline fun Layout.REn.check(bit: Layout.RF) = this.type and bit.bit == bit.bit