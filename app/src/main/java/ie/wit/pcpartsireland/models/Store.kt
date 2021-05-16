package ie.wit.pcpartsireland.models

interface Store {
    fun create(part: Model)
    fun clear()
    fun updateUserPart(userId: String, uid: String?, part: Model)
    fun updatePart(uid: String?, part: Model)
    fun deleteUserPart(userId: String, uid: String?)
    fun deletePart(uid: String?)
}