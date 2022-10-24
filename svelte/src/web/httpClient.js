import {Report} from "./model/Report"

export const httpClient = {}

httpClient.send=(resourse, method, body) => fetch(resourse,
    {method, body: JSON.stringify(body), headers: {"Content-Type": "application/json"}})
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

httpClient.getReportByParams=(reportNum, params) => httpClient
    .get(`reports/${reportNum}?`+new URLSearchParams(params).toString())
    .then(reportTable => new Report(reportNum, reportTable))