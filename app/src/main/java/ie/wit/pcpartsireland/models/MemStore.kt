package ie.wit.pcpartsireland.models

import it.sephiroth.android.library.uigestures.UIGestureRecognizer.Companion.id
import java.util.ArrayList

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class MemStore : Store{

    private val parts = mutableListOf<Model>()


    override fun findById(id: Long): Model? {
        return parts.find { it.id == id}
    }

    override fun findAll(): MutableList<Model> {
        return parts
    }

    override fun create(part: Model) {
        part.id = getId()
        parts.add(part)
        logAll()
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
            logAll()
        }
    }

    override fun delete(part: Model) {
        parts.remove(part)
    }

    override fun clear() {
        parts.clear()
    }

    private fun logAll() {

    }
}