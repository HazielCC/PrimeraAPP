package androidearly.ui.heroApp.data

import com.google.gson.annotations.SerializedName


data class SuperHeroDetailResponse(
    @SerializedName("appearance") val appearance: Appearance?,
    @SerializedName("biography") val biography: Biography?,
    @SerializedName("connections") val connections: Connections?,
    @SerializedName("id") val id: String?, // 70
    @SerializedName("image") val image: Image?,
    @SerializedName("name") val name: String?, // Batman
    @SerializedName("powerstats") val powerstats: Powerstats?,
    @SerializedName("response") val response: String?, // success
    @SerializedName("work") val work: Work?
)

data class Appearance(
    @SerializedName("eye-color") val eyeColor: String?, // blue
    @SerializedName("gender") val gender: String?, // Male
    @SerializedName("hair-color") val hairColor: String?, // black
    @SerializedName("height") val height: List<String>?,
    @SerializedName("race") val race: String?, // Human
    @SerializedName("weight") val weight: List<String>?
)


data class Connections(
    @SerializedName("group-affiliation") val groupAffiliation: String?, // Batman Family, Batman Incorporated, Justice League, Outsiders, Wayne Enterprises, Club of Heroes, formerly White Lantern Corps, Sinestro Corps
    @SerializedName("relatives") val relatives: String? // Damian Wayne (son), Dick Grayson (adopted son), Tim Drake (adopted son), Jason Todd (adopted son), Cassandra Cain (adopted ward)Martha Wayne (mother, deceased), Thomas Wayne (father, deceased), Alfred Pennyworth (former guardian), Roderick Kane (grandfather, deceased), Elizabeth Kane (grandmother, deceased), Nathan Kane (uncle, deceased), Simon Hurt (ancestor), Wayne Family
)


data class Work(
    @SerializedName("base") val base: String?, // Batcave, Stately Wayne Manor, Gotham City; Hall of Justice, Justice League Watchtower
    @SerializedName("occupation") val occupation: String? // Businessman
)
