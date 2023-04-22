package dev.gregyyy.mensaapi.json

import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonProperty

data class ApiDish(
    @JsonProperty("meal")
    val meal: String?,
    @JsonProperty("dish")
    val dish: String?,
    @JsonProperty("add")
    val add: List<String>?,
    @JsonProperty("bio")
    val bio: Boolean?,
    @JsonProperty("fish")
    val fish: Boolean?,
    @JsonProperty("pork")
    val pork: Boolean?,
    @JsonProperty("pork_aw")
    val porkAw: Boolean?,
    @JsonProperty("cow")
    val cow: Boolean?,
    @JsonProperty("cow_aw")
    val cowAw: Boolean?,
    @JsonProperty("vegan")
    val vegan: Boolean?,
    @JsonProperty("mensa_vit")
    val mensaVit: Boolean?,
    @JsonProperty("info")
    val info: String?,
    @JsonProperty("price_1")
    val price1: Double?,
    @JsonProperty("price_2")
    val price2: Double?,
    @JsonProperty("price_3")
    val price3: Double?,
    @JsonProperty("price_4")
    val price4: Double?,
    @JsonProperty("price_flag")
    val priceFlag: Int?,
    @JsonProperty("nodata")
    val nodata: Boolean?
)

data class ApiMensa(
    val mensa: MutableMap<String, Map<String, Map<String, List<ApiDish>>>> = HashMap(),
    @JsonProperty("date")
    val date: Long,
    @JsonProperty("import_date")
    val importDate: Long
){
    @JsonAnySetter
    fun setMensa(key: String?, value: Map<String, Map<String, List<ApiDish>>>?) {
        if (key != null && value != null) {
            mensa[key] = value
        }
    }
}