import {Report, Report3} from "./model/Report"

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

httpClient.getCarriersByDate=(date) => httpClient
    .get(`carriers?date=${date}`)

httpClient.getCountriesByDate=(date) => httpClient
    .get(`countries?date=${date}`)

httpClient.getRoadsByDateAndCountries=(date, countries) => httpClient
    .get('roads?'+ new URLSearchParams({date, countryCodes: countries.map(country => country.code)}).toString())

httpClient.getStationsByDateAndRoads=(date, roads) => httpClient
    .get(`stations?` + new URLSearchParams({
        date,
        roadCodes: roads
            .map(road => road.countryCode + (road.code === "," ? "comma" : road.code))
    }))

httpClient.getReportByParams=(reportNum, params) => httpClient
    .get(`reports/${reportNum}?`+ new URLSearchParams(params).toString())
    .then(report => {
        if (reportNum !== 3)
            return (report.body.length <= 1) // This means that the report is empty. The first row is "Total"
                ? false : new Report(reportNum, report)
        else
            return (report.dailyReport.body.length + report.monthlyReport.body.length <= 1) // Almost same
                ? false : new Report3(report.dailyReport, report.monthlyReport)
    })