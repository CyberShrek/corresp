<script>
import {httpClient} from "../../web/httpClient"
import Dropdown from "../common/Dropdown.svelte"
import MultiSelect from "../common/MultiSelect.svelte"
import {afterUpdate} from "svelte"

export let headingText, inputDate, type, selected, isValid

let countries = [], selectedCountryNames = [],
    roads     = [], selectedRoadNames    = [],
    stations  = [], selectedStationNames = []
$: selectedCountries = selectedCountryNames.map(name => countries.find(desired => desired.name === name))
$: selectedRoads     = selectedRoadNames   .map(name => roads    .find(desired => desired.name === name))
$: selectedStations  = selectedStationNames.map(name => stations .find(desired => desired.name === name))

$: countriesPromise = inputDate ?
    httpClient.getCountriesByDate(inputDate)
        .then(r => countries = r) : countries = selectedCountryNames = []

$: roadsPromise = selectedCountries.length > 0 ?
    httpClient.getRoadsByDateAndCountryCodes(inputDate, selectedCountries.map(desired => desired.code))
        .then(r => roads = r) : roads = selectedRoadNames = []

$: stationsPromise = selectedRoads.length > 0 ?
    httpClient.getStationsByDateAndRoadCodes(inputDate, selectedRoads.map(desired => desired.code))
        .then(r => stations = r) : stations = selectedStationNames = []

$: type = selectedStations  ? "s" :
          selectedRoads     ? "r" :
          selectedCountries ? "c" : ""

$: selected = selectedStations  ? selectedStations :
              selectedRoads     ? selectedRoads    :
              selectedCountries ? selectedCountries : []

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

</script>

<!-- COUNTRIES -->
<MultiSelect placeholder="Государства"
             options={countries.map(country => country.name)}
             bind:selectedOptions={selectedCountryNames}/>

{#await countriesPromise} <Dropdown text="Загружаю список государств"/>
{:catch error}            <Dropdown text="Не удалось загрузить список государств" type="error"/>
{/await}

<!-- ROADS -->
<MultiSelect placeholder="Дороги"
             options={roads.map(road => road.name)}
             bind:selectedOptions={selectedRoadNames}/>

{#await roadsPromise} <Dropdown text="Загружаю список дорог"/>
{:catch error}        <Dropdown text="Не удалось загрузить список дорог" type="error"/>
{/await}

<!-- STATIONS -->
<MultiSelect placeholder="Станции"
             options={stations.map(station => station.name)}
             bind:selectedOptions={selectedStationNames}/>

{#await stationsPromise} <Dropdown text="Загружаю список станций"/>
{:catch error}           <Dropdown text="Не удалось загрузить список станций" type="error"/>
{/await}