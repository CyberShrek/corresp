<script>
import {httpClient} from "../../web/httpClient"
import Dropdown from "../common/Dropdown.svelte"
import MultiSelect from "../common/MultiSelect.svelte"
import {afterUpdate} from "svelte"

export let inputDate, type, selected, isValid

let countries = [], selectedCountries = [],
    roads     = [], selectedRoads     = [],
    stations  = [], selectedStations  = []

// These setters are only used to avoid unwanted calls in the chain of reactive promises
const setCountries=(val) => val ? countries = val : countries = []
const setRoads=(val)     => val ? roads     = val : roads     = []
const setStations=(val)  => val ? stations  = val : stations  = []

$: countriesPromise = inputDate ?
    httpClient.getCountriesByDate(inputDate)
        .then(r => setCountries(r)) : setCountries(null)

$: roadsPromise = selectedCountries.length > 0 ?
    httpClient.getRoadsByDateAndCountryCodes(inputDate, selectedCountries.map(country => country.code))
        .then(r => setRoads(r)) : setRoads(null)

$: stationsPromise = selectedRoads.length > 0 ?
    httpClient.getStationsByDateAndRoadCodes(inputDate, selectedRoads.map(road => road.code))
        .then(r => setStations(r)) : setStations(null)

afterUpdate(() => {
    if (selectedStations.length > 0)  { type = "s"; selected = selectedStations }
    else
    if (selectedRoads.length > 0)     { type = "r"; selected = selectedRoads }
    else
    if (selectedCountries.length > 0) { type = "c"; selected = selectedCountries }
    else
        type = selected = undefined
})

$: isValid = type && selected && selected.length > 0

const nameAndCodeOf=(entity) => entity.name + " ("+entity.code+")"

</script>

<!-- COUNTRIES -->
<MultiSelect placeholder="Государства"
             options={countries}
             bind:selectedOptions={selectedCountries}/>

{#await countriesPromise} <Dropdown text="Загружаю список государств"/>
{:catch error}            <Dropdown text="Не удалось загрузить список государств" type="error"/>
{/await}

<!-- ROADS -->
<MultiSelect placeholder={`Дороги${selectedRoads.length > 0 ? "" : " (все)"}`}
             allowSelectAll={true}
             options={roads}
             bind:selectedOptions={selectedRoads}/>

{#await roadsPromise} <Dropdown text="Загружаю список дорог"/>
{:catch error}        <Dropdown text="Не удалось загрузить список дорог" type="error"/>
{/await}

<!-- STATIONS -->
<MultiSelect placeholder={`Станции${selectedRoads.length > 0 ? "" : " (все)"}`}
             allowSelectAll={true}
             options={stations}
             bind:selectedOptions={selectedStations}/>

{#await stationsPromise} <Dropdown text="Загружаю список станций"/>
{:catch error}           <Dropdown text="Не удалось загрузить список станций" type="error"/>
{/await}