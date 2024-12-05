package androidearly.heroApp

import com.google.gson.annotations.SerializedName

data class SuperHeroDataResponse(
    @SerializedName("response") val response: String = "",
    @SerializedName("results") val superHeroList: List<SuperHeroItemResponse> = emptyList(),
)

data class SuperHeroItemResponse(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("powerstats") val powerstats: Powerstats = Powerstats(),
    @SerializedName("biography") val biography: Biography,
    @SerializedName("image") val image: Image = Image(),
)

data class Powerstats(
    @SerializedName("combat") val combat: String = "",
    @SerializedName("durability") val durability: String = "",
    @SerializedName("intelligence") val intelligence: String = "",
    @SerializedName("power") val power: String = "",
    @SerializedName("speed") val speed: String = "",
    @SerializedName("strength") val strength: String = "",
)

data class Image(
    @SerializedName("url") val url: String = ""
)

data class Biography(
    @SerializedName("aliases") val aliases: List<String> = emptyList(),
    @SerializedName("alignment") val alignment: String,
    @SerializedName("alterEegos") val alterEegos: String,
    @SerializedName("first-appearance") val firstAppearance: String,
    @SerializedName("full-name") val fullName: String,
    @SerializedName("publisher") val publisher: String// DC Comics
)
