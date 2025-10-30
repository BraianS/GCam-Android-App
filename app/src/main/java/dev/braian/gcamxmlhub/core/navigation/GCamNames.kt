package dev.braian.gcamxmlhub.core.navigation

sealed class GCamNames(
    val name: String,
    val developer: String = "",
    val version: String = ""
) {
    object LMC : GCamNames("LMC Camera", "Hasli", "R15")
    object BSG : GCamNames("BSG GCam", "BSG", "8.1")
    object Greatness : GCamNames("Greatness GCam", "Greatness", "10.0")
    object Shamim : GCamNames("Shamim GCam", "Shamim", "V23")
    object Arnova8G2 : GCamNames("Arnova8G2 GCam", "Arnova8G2", "8.1")
    object Parrot043 : GCamNames("Parrot043 GCam", "Parrot043", "V7")
    object Wichaya : GCamNames("Wichaya GCam", "Wichaya", "3.4")
    object BigKaka : GCamNames("BigKaka GCam", "BigKaka", "V47")
    object Hasli : GCamNames("Hasli GCam", "Hasli", "R15")
    object Urnyx05 : GCamNames("Urnyx05 GCam", "Urnyx05", "8.1")
    object Nikita : GCamNames("Nikita GCam", "Nikita", "V8.1")
    object TrCamera : GCamNames("TrCamera", "Tigr", "R8")
    object AGC : GCamNames("AGC GCam", "Aryan", "8.4")
    object NGCam : GCamNames("NGCam", "Nokia", "8.1")
    object Firebird : GCamNames("Firebird GCam", "Firebird", "V4")
    object MTSL : GCamNames("MTSL Camera", "MTSL", "R6")
    object PX : GCamNames("PX GCam", "PX", "8.1")
    object San1ty : GCamNames("San1ty GCam", "San1ty", "V14")
    object Fu24 : GCamNames("Fu24 GCam", "Fu24", "R7")
    object Javas : GCamNames("Javas GCam", "Javas", "V3")

    override fun toString(): String = name
}

