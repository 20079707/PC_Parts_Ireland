package ie.wit.pcpartsireland.models

interface Store {
    fun findAll() : List<Model>
    fun findById(id: Long) : Model?
    fun create(part: Model)
    fun update(part: Model)
    fun delete(part: Model)
    fun clear()
}