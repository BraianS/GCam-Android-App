package dev.braian.gcamxmlhub.data.utils

sealed class MainContent (val route: String) {
    object Home: MainContent("home")
    object XmlDetail: MainContent("xml_details") {
        fun createRoute(id: Int) = "/$id"
    }
    object Search: MainContent("search")
    object Profile: MainContent("profile")
}
