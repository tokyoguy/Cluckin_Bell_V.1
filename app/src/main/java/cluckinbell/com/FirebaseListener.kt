package cluckinbell.com

interface FirebaseListener {
    fun onFirebaseLoadSuccess(itemGroupList: List<ItemGroup>)
    fun onFirebaseLoadFailed(message: String)
}