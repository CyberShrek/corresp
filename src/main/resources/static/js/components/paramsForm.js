import {httpClient} from "../web/httpClient.js"

document.body.insertAdjacentHTML("beforeend", `
<form id="parameters-form">
    <div name="period"      class="field"></div>
    <div name="carrier"     class="field"></div>
    <div name="departure"   class="field"></div>
    <div name="destination" class="field"></div>

    <input type="submit" value="Сформировать отчёт" class="submit-button">
</form>`)

export const paramsForm = document.getElementById("parameters-form")

// Period field
{
    paramsForm.period = paramsForm.querySelector(".field[name=period]")
    paramsForm.period.insertAdjacentHTML("beforeend", `
        <p class="paragraph"> Период </p>
        <label class="date"> От: <input type="date" name="1"> </label>
        <label class="date"> До: <input type="date" name="2"> </label>
        <label class="checkbox"> <input type="checkbox"> Сравнение с прошлым годом </label>
    `)
    Object.defineProperties(paramsForm.period, {
        date1: {get() {return getDateByName(1)}},
        date2: {get() {return getDateByName(2)}},
        daysCount: {get() {
                return Math.abs(new Date(this.date1).getTime() - new Date(this.date2).getTime()) / (1000 * 3600 * 24)
            }
        }
    })

    function getDateByName(name) {
        return paramsForm.period.querySelector(`input[type=date][name='${name}']`).value
    }
}
// Carrier field
{
    paramsForm.carrier = paramsForm.querySelector(".field[name=carrier]")
    paramsForm.carrier.insertAdjacentHTML("beforeend",
        `<p class="paragraph"> Перевозчик </p>`)
    httpClient.get("carriers")
        .then(carriers => {
            // Appending gotten carriers into the carrier selection element
            carriers.forEach(carrier =>
                paramsForm.carrier.insertAdjacentHTML("beforeend", `
                <label class="radio-button">
                    <input type="radio" name="carriers" value="${carrier}"> ${carrier} </label>`))
        })
}
// Departure and Destination fields
{
    paramsForm.departure = defineField(paramsForm.querySelector(".field[name=departure]"), "Объект отправления")
    paramsForm.destination = defineField(paramsForm.querySelector(".field[name=destination]"), "Объект назначения")

    function defineField(field, paragraphText) {
        field.insertAdjacentHTML("beforeend", `        
            <p class="paragraph"> ${paragraphText} </p>
            <select>
                <option> - </option>
                <option> Государство </option>
                <option> Дорога </option>
                <option> Станция </option>
            </select>
            <div class="checkboxes"></div>
        `)
        field.select = field.querySelector("select")
        field.checkboxesSelect = field.querySelector(".checkboxes")
        field.select.onchange = () => {
            removeCheckboxes()
            switch (field.select.value) {
                case "Государство" :fillCheckboxes("countries");break
                case "Дорога"      :fillCheckboxes("railRoads");break
                case "Станция"     :fillCheckboxes("stations");break
                default :return
            }
        }

        function removeCheckboxes() {
            while (field.checkboxesSelect.hasChildNodes())
                field.checkboxesSelect.firstChild.remove()
        }

        function fillCheckboxes(optionsName) {
            httpClient.get(optionsName).then(options => {
                for (const option of options) {
                    field.checkboxesSelect.insertAdjacentHTML("beforeend",
                        `<label class="checkbox"><input type="checkbox" value="${option}"> ${option} </label>`)
                }
            })
        }

        return field
    }
}

paramsForm.querySelector("input[type=submit]").onclick = (event) => {
    event.preventDefault()
}

