package concerrox.emixx.content

import com.google.common.collect.HashBasedTable
import concerrox.emixx.content.stackgroup.GroupedEmiStack
import concerrox.emixx.content.ScreenManager.ENTRY_SIZE
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.runtime.EmiDrawContext
import dev.emi.emi.screen.EmiScreenManager

object Layout {

    var si = -10
    var isF = true
    val layout: HashBasedTable<Int, Int, EmiIngredient> = HashBasedTable.create()

    fun show(screenSpace: EmiScreenManager.ScreenSpace, context: EmiDrawContext) {
        for (y in 0 until screenSpace.ty) {
            for (x in 0 until screenSpace.tx) {
                val stack = layout.get(y, x)
                if (stack != null && stack is GroupedEmiStack<*>) {
                    val px = screenSpace.tx + x * ENTRY_SIZE
                    val py = screenSpace.ty + y * ENTRY_SIZE
                    if (y == 0 || ge(y - 1, x)?.stackGroup != stack.stackGroup) {
                        context.fill(px, py, ENTRY_SIZE, 1, 0x66FFFFFF)
                    }
                    if (x == 0 || ge(y, x - 1)?.stackGroup != stack.stackGroup) {
                        context.fill(px, py, 1, ENTRY_SIZE, 0x66FFFFFF)
                    }
                    if (y == screenSpace.th - 1 || ge(y + 1, x)?.stackGroup != stack.stackGroup) {
                        context.fill(px, py + 17, ENTRY_SIZE, 1, 0x66FFFFFF)
                    }
                    if (x == screenSpace.tw - 1 || ge(y, x + 1)?.stackGroup != stack.stackGroup) {
                        context.fill(px + 17, py, 1, ENTRY_SIZE, 0x66FFFFFF)
                    }
                }
            }
        }
    }

    private fun ge(y: Int, x: Int): GroupedEmiStack<*>? {
        return layout.get(y, x) as? GroupedEmiStack<*>
    }

}