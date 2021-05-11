package ie.wit.pcpartsireland.models

import android.content.Context
import android.system.Os.read
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ie.wit.pcpartsireland.helpers.exists
import ie.wit.pcpartsireland.helpers.read
import ie.wit.pcpartsireland.helpers.write
import java.lang.reflect.Type
import java.nio.file.Files.exists
import java.nio.file.Files.write
import java.util.*

const val JSON_FILE = "parts.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting().create()
val listType: Type = object : TypeToken<ArrayList<Model>>() {}.type


fun generateRandomId(): Long {
    return Random().nextLong()
}

class JSONStore (private val context: Context) : Store {

    private var parts = mutableListOf<Model>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findById(id: Long): Model? {
        return parts.find { it.id == id}
    }

    override fun findAll(): MutableList<Model> {
        return parts
    }

    override fun create(part: Model) {
        part.id = generateRandomId()
        parts.add(part)
        serialize()
    }

    // finds value saved and replaces it with updated values
    override fun update(part: Model) {
        val parts = findAll() as ArrayList<Model>
        val foundPart: Model? = parts.find { p -> p.id == part.id }
        if (foundPart != null) {
            foundPart.title = part.title
            foundPart.category = part.category
            foundPart.price = part.price
            foundPart.quantity = part.quantity
            foundPart.image = part.image
            foundPart.description = part.description
        }
        serialize()
    }


    // deletes values from db
    override fun delete(part: Model) {
        parts.remove(part)
        serialize()
    }

    override fun clear() {
        parts.clear()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(parts, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        parts = Gson().fromJson(jsonString, listType)
    }

}