package concerrox.emixx.config

import com.google.gson.GsonBuilder
import com.mojang.logging.LogUtils
import concerrox.emixx.EmiPlusPlus
import java.nio.file.Files
import java.util.concurrent.Executors
import kotlin.io.path.extension
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.readText

fun Any?.p() {
    LogUtils.getLogger().warn(this.toString())
}

object ContentData {

    val gson = GsonBuilder().create()

    val tabs = linkedMapOf<String, Tab>() // Use linkedMap to preserve tab order
    val contents = linkedMapOf<Tab, LinkedHashMap<String, List<StackHolder>>>()
    var isLoaded = false

    fun readXXxxxx() {
        val data = Files.createDirectories(EmiPlusPlusConfig.CONFIG_DIRECTORY_PATH).listDirectoryEntries()
            .filter { it.extension == "json" }.mapNotNull {
                try {
                    gson.fromJson(it.readText(), A::class.java)
                } catch (e: Exception) {
                    EmiPlusPlus.LOGGER.warn("Failed to read $it", e)
                    null
                }
            }

        data.forEach {
            it.tabs.forEach { tab ->
                tabs[tab.id] = tab
            }
        }
        data.forEach(::collectS)

        tabs.p()
        contents.p()
    }

    fun collectS(obj: A) {
        obj.contents.forEach { (tabId, typeMap) ->
            val tab = tabs[tabId]
            if (tab != null) {
                contents.getOrPut(tab, ::linkedMapOf).putAll(typeMap.mapValues { (_, items) ->
                    items.map { ItemStackHolder(it) }
                })
            } else {
                EmiPlusPlus.LOGGER.warn("Tab $tabId not found")
            }
        }
    }

    fun doSth() {
        val executorService = Executors.newCachedThreadPool()
        executorService.submit {
            readXXxxxx()
            isLoaded = true
            executorService.shutdown()
        }
    }

}

data class A(var tabs: List<Tab>, var contents: Map<String, Map<String, List<String>>>)

data class Tab(val id: String, val icon: String, val title: String)


data class C(val name: String, val items: List<String>, val order: Int = -1)

sealed class StackHolder() {

}

data class ItemStackHolder(val id: String) : StackHolder() {

}

data class ItemCollectionStackHolder(val ids: List<String>) : StackHolder() {

}