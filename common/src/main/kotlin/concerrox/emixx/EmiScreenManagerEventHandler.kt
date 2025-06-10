package concerrox.emixx

object EmiScreenManagerEventHandler {

//    fun Any?.print() {
//        LogUtils.getLogger().info(this.toString())
//    }

//    fun afterAddWidgets(screen: Screen) {
////        EmiPlusPlusScreenManager.screen = screen
//
//        EmiScreenManagerAccessor.getPanels().find { panel ->
//            panel.pages.pages.any {
//                it.type == SidebarType.INDEX
//            }
//        }?.getSpaces()?.forEach {
//            it.print()
//        }
//    }

    fun afterScreenTicked() {
        EmiPlusPlusScreenManager.tick()
    }

//    fun afterRender(context: EmiDrawContext, mouseX: Int, mouseY: Int, delta: Float) {
//
//    }

//    fun init() {
//
//    }

}