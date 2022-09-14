export const httpClient = {}

httpClient.get = (resourse) => fetch(resourse)
    .then(response => {
        if (response.ok && response.status === 200) return response.json()
        else response.text()
            .then(errorBody => alert(new Error(response.status + ": " + errorBody)))
    })
