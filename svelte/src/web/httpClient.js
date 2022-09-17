export const httpClient = {}

httpClient.get = (resourse) => fetch(resourse)
    .then(response => {
        if (response.ok){
            if (response.status === 200) return response.json()
            else throw new Error(response)
        }
        else throw new Error(response)
    })

httpClient.getCarriersByDate=(date) => httpClient.get(`carriers?date=${date}`)

httpClient.getCountries=() => httpClient.get("countries")

httpClient.getStations=() => httpClient.get("stations")

httpClient.getRoads=() => httpClient.get("roads")