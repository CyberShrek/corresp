import {httpClient} from "../web/httpClient.js"

document.body.insertAdjacentHTML("beforeend",`
<form id="parameters-form">
    <div name="period" class="field">
        <p> Период </p>
        <label> От: <input type="date" name="1"> </label>
        <label> До: <input type="date" name="2"> </label>
        <label> <input type="checkbox"> Сравнение с прошлым годом </label>
    </div>

    <div name="carrier" class="field">
        <p> Перевозчик </p>
        <select></select>
    </div>

    <div name="departure" class="field">
        <p> Объект отправления </p>
        <select>
            <option name="country"> Государство </option>
            <option name="railRoad"> Дорога </option>
            <option name="station"> Станция </option>
        </select>
        <div></div class="checkboxes-select">
    </div>

    <div name="destination" class="field">
        <p> Объект назначения </p>
        <select>
            <option name="country"> Государство </option>
            <option name="railRoad"> Дорога </option>
            <option name="station"> Станция </option>
        </select>
        <div></div class="checkboxes-select">
    </div>

    <input type="submit" value="Сформировать отчёт" class="submit-button">
</form>`)

export const paramsForm = document.getElementById("parameters-form")

// Period field
paramsForm.period = paramsForm.querySelector(".field[name=period]")
Object.defineProperties(paramsForm.period, {
    date1     : {get() { return getDateByName(1) }},
    date2     : {get() { return getDateByName(2) }},
    daysCount : {get() {
        return Math.abs(new Date(this.date1).getTime() - new Date(this.date2).getTime()) / (1000 * 3600 * 24)
    }}
})
function getDateByName(name) {
    return paramsForm.period.querySelector(`input[type=date][name='${name}']`).value
}

// Carrier field
paramsForm.carrier = paramsForm.querySelector(".field[name=carrier]")
paramsForm.carrier.getSelect=() => paramsForm.querySelector(".field[name=carrier]")
httpClient.get("carriers")
    .then(carriers => {
        // Appending gotten carriers into the carrier selection element
        const selectEl = paramsForm.carrier.querySelector("select")
        carriers.forEach(carrier =>
            selectEl.insertAdjacentHTML("beforeend", `<option>${carrier}</option>`))
    })

// Departure field
paramsForm.departure = paramsForm.querySelector(".field[name=departure]")


// Destination field
paramsForm.destination = paramsForm.querySelector(".field[name=departure]")

paramsForm.querySelector("input[type=submit]").onclick=(event) => {
    event.preventDefault()
    alert(paramsForm.period.getDaysCount())
}

