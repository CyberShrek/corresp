export const httpClient = {}

httpClient.get = (resourse) => fetch(resourse)
    .then(response => {
        if (response.ok){
            if (response.status === 200) return response.json()
            else throw new Error(response)
        }
        else throw new Error(response)
    })

httpClient.getCarriersByDate=(date) => httpClient
    .get(`carriers?date=${date}`)

httpClient.getCountriesByDate=(date) => httpClient
    .get(`countries?date=${date}`)

httpClient.getRoadsByDateAndCountryCodes=(date, countryCodes) => httpClient
    .get(`roads?date=${date}&countryCodes=${countryCodes}`)

httpClient.getStationsByDateAndRoadCodes=(date, roadCodes) => httpClient
    .get(`stations?date=${date}&roadCodes=${roadCodes}`)

