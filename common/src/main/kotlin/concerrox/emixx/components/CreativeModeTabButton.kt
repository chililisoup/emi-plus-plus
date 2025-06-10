package concerrox.emixx.components

//@Environment(EnvType.CLIENT)
//private fun GuiGraphics.renderSizedItem(
//    entity: LivingEntity? = null,
//    level: Level = entity?.level() ?: Minecraft.getInstance().level as Level,
//    stack: ItemStack,
//    x: Int,
//    y: Int,
//    size: Float = 16.0F,
//    seed: Int = 0,
//    guiOffset: Int = 0
//) {
//    if (!stack.isEmpty) {
//        val itemRenderer = Minecraft.getInstance().itemRenderer
//        val bakedModel = itemRenderer.getModel(stack, level, entity, seed)
//        pose.pushPose()
//        pose.translate(x + size / 2, y + size / 2, 150F + (if (bakedModel.isGui3d) guiOffset else 0))
//        try {
//            pose.mulPose((Matrix4f()).scaling(1.0f, -1.0f, 1.0f))
//            pose.scale(size, size, size)
//            if (!bakedModel.usesBlockLight()) {
//                Lighting.setupForFlatItems()
//            }
//            itemRenderer.render(stack, ItemDisplayContext.GUI, false, pose, bufferSource(), 15728880,
//                OverlayTexture.NO_OVERLAY, bakedModel)
//            flush()
//            if (!bakedModel.usesBlockLight()) {
//                Lighting.setupFor3DItems()
//            }
//        } catch (throwable: Throwable) {
//            val crashReport = CrashReport.forThrowable(throwable, "Rendering item")
//            val crashReportCategory = crashReport.addCategory("Item being rendered")
//            crashReportCategory.setDetail("Item Type") { stack.item.toString() }
//            crashReportCategory.setDetail("Item Damage") { stack.damageValue.toString() }
//            crashReportCategory.setDetail("Item NBT") { stack.tags.toString() }
//            crashReportCategory.setDetail("Item Foil") { stack.hasFoil().toString() }
//            throw ReportedException(crashReport)
//        } finally {
//            pose.popPose()
//        }
//    }
//}

