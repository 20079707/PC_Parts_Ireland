package ie.wit.pcpartsireland.models

interface Store {
    fun findAll() : List<Model>
    fun findById(id: Long) : Model?
    fun create(advert: Model)
    //fun update(advert: Model)
    //fun delete(advert: Model)
}