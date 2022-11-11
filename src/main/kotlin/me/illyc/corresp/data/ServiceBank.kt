package me.illyc.corresp.data

import com.fasterxml.jackson.databind.JsonNode
import me.illyc.corresp.model.*
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.web.client.postForObject
import javax.servlet.http.HttpServletRequest

// Dao
class ServiceBank(request: HttpServletRequest) {

    fun getCarriersByDate(date: String) =
        shittyNodeToPrettyList<Carrier>(
            postForEntityByDate("perList", date)
                .get("perList"))

    fun getCountriesByDate(date: String) =
        shittyNodeToPrettyList<Country>(
            postForEntityByDate("gosList", date)
                .get("gosList"))

    fun getRoadsByCountryCodesAndDate(countryCodes: Set<String>, date: String): List<Road> =
        countryCodes
            .map{ countryCode ->
                shittyNodeToPrettyList<Road>(
                    postForEntityByDate("dorList", date, """ "gos": "$countryCode" """)
                        .get("dorList") ).map{ road -> road.countryCode = countryCode; road }
            }.flatten()

    fun getStationsByRoadCodesAndDate(roadCodes: Set<String>, date: String): List<Station> {
        // In each code only the last char is exactly the road code and all the rest is the country code.
        // So I used the country codes as a keys and the actual road codes as a values
        val codesMap = HashMap<String, ArrayList<Char>>()
        for (roadCode in roadCodes){
            val roadCode = roadCode.replace("comma", ",")
            val countryCode = roadCode.substringBeforeLast(roadCode.last())
            if (codesMap[countryCode] == null)
                codesMap[countryCode] = ArrayList()

            codesMap[countryCode]!!.add(roadCode.last())
        }
        // Now let's fill the List using the gotten HashSet
        val prettyList = ArrayList<Station>()
        for (code in codesMap){
            prettyList.addAll(
                shittyNodeToPrettyList(
                    postForEntityByDate("stanList", date,
                        """ "gos": "${code.key}", "dor": "${code.value.joinToString()}" """)
                        .get("stanList")
                )
            )
        }
        return prettyList
    }

    private val serviceBankURL = "${request.scheme}://${request.serverName}:${request.serverPort}/servicebank/getdata"

    private val client = RestTemplateBuilder()
        .defaultHeader("Authorization", request.getHeader("Authorization"))
        .defaultHeader("Content-Type", "application/json")
        .build()

    private fun postForEntityByDate(entity: String,
                                    date:   String,
                                    params: String = "") =
        client
            .postForObject<JsonNode>(serviceBankURL,
                """{"$entity":[{ "data": "$date"${if (params != "") "," else ""} $params}]}""")

    private inline fun <reified T: ParamsFormEntity> shittyNodeToPrettyList(shitty: JsonNode) = shitty
        .map{ entityNode -> nodeToEntity<T>(entityNode) }

    private inline fun <reified T: ParamsFormEntity> nodeToEntity(node: JsonNode) = when(T::class) {
        Carrier::class -> Carrier(name = node.get("nazvp").asText(),  code = node.get("skp").asText())   as T
        Country::class -> Country(name = node.get("g_name").asText(), code = node.get("g_kod").asText()) as T
        Road   ::class ->    Road(name = node.get("d_name").asText(), code = node.get("d_kod").asText()) as T
        Station::class -> Station(name = node.get("pnazv").asText(),  code = node.get("stan").asText())  as T

        else -> throw Error("Unknown type")
    }
}