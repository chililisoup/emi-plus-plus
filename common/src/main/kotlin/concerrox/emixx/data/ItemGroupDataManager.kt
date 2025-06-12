package concerrox.emixx.data

import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import concerrox.emixx.config.EmiPlusPlusConfig
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.Executors
import kotlin.io.path.div
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.readText


data class ItemGroup(val items: List<ItemHolder>, var id: String? = null)

sealed class ItemHolder
data class ItemStackHolder(val id: String) : ItemHolder()
data class OrderedItemStackHolder(val id: String, val order: Int) : ItemHolder()
data class ItemTagHolder(val id: String) : ItemHolder()

object ItemGroupDataManager {

    private val ITEM_GROUPS_DIRECTORY_PATH = EmiPlusPlusConfig.CONFIG_DIRECTORY_PATH / "groups"
    private val ITEM_HOLDER_TYPE_ADAPTER = object : TypeAdapter<ItemHolder>() {
        override fun write(output: JsonWriter, value: ItemHolder) {
            when (value) {
                is ItemStackHolder -> output.value(value.id)
                is OrderedItemStackHolder -> output.value("&${value.order}=${value.id}")
                is ItemTagHolder -> output.value("#${value.id}")
            }
        }

        override fun read(input: JsonReader): ItemHolder {
            val value = input.nextString()
            return when {
                value.startsWith("&") -> OrderedItemStackHolder(value.substringAfter("="),
                    value.substringBefore("=").substring(1).toInt())

                value.startsWith("#") -> ItemTagHolder(value.substringAfter("#"))
                else -> ItemStackHolder(value)
            }
        }
    }
    private val GSON = GsonBuilder().registerTypeAdapter(ItemHolder::class.java, ITEM_HOLDER_TYPE_ADAPTER).create()

    val itemGroups = mutableListOf<ItemGroup>()

    fun initialize() {
        val executors = Executors.newCachedThreadPool()
        Files.createDirectories(ITEM_GROUPS_DIRECTORY_PATH).listDirectoryEntries("*.json").forEach {
            executors.submit { loadGroupFromPath(it) }
        }
    }

    private fun loadGroupFromPath(path: Path) {
        itemGroups += GSON.fromJson(path.readText(), ItemGroup::class.java).apply {
            id = id ?: path.nameWithoutExtension
        }
    }

}