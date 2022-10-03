
export const httpClient = {}

httpClient.send=(resourse, method, body) => fetch(resourse,
    {method, body, headers: {"Content-Type": "application/json"}})
    .then(response => {
        if (response.ok) {
            if (response.status === 200) return response.json()
            else throw response.text()
        } else throw response.text()
    })

httpClient.get= (resourse) => httpClient.send(resourse, "GET")
httpClient.post=(resourse, body) => httpClient.send(resourse, "POST", body)

httpClient.getCarriersByDate=(date) => httpClient
    .get(`carriers?date=${date}`)

httpClient.getCountriesByDate=(date) => httpClient
    .get(`countries?date=${date}`)

httpClient.getRoadsByDateAndCountryCodes=(date, countryCodes) => httpClient
    .get(`roads?date=${date}&countryCodes=${countryCodes}`)

httpClient.getStationsByDateAndRoadCodes=(date, roadCodes) => httpClient
    .get(`stations?date=${date}&roadCodes=${encodeURIComponent(roadCodes)}`)

httpClient.createReport1ByParams=(params) => httpClient
    .post("report1", JSON.stringify({
        date1               : params.period.date1,
        date2               : params.period.date2,
        compareWithLastYear : params.period.compareWithLastYear,
        carrierName         : params.carrier.selected.name,
        departureType       : params.departure.type,
        departureCodes      : params.departure.selected.map(s => s.code),
        destinationType     : params.destination.type,
        destinationCodes    : params.destination.selected.map(s => s.code)
    }))