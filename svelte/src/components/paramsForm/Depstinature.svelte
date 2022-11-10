<script>
import {httpClient} from "../../web/httpClient"
import Dropdown from "../modals/Dropdown.svelte"
import MultiSelect from "../common/MultiSelect.svelte"

export let inputDate, selectedStations, isValid

let countries = [], selectedCountries = [],
    roads     = [], selectedRoads     = [],
    stations  = []

// These setters are only used to avoid unwanted calls in the chain of reactive promises
const setCountries=(val) => val ? countries = val : countries = []
const setRoads=(val)     => val ? roads     = val : roads     = []
const setStations=(val)  => val ? stations  = val : stations  = []

$: countriesPromise = inputDate ?
    httpClient.getCountriesByDate(inputDate)
        .then(r => setCountries(r)) : setCountries(null)

$: roadsPromise = inputDate && selectedCountries.length > 0 ?
    httpClient.getRoadsByDateAndCountries(inputDate, selectedCountries)
        .then(r => setRoads(r)) : setRoads(null)

$: stationsPromise = inputDate && selectedRoads.length > 0 ?
    httpClient.getStationsByDateAndRoads(inputDate, selectedRoads)
        .then(r => setStations(r)) : setStations(null)

$: isValid = selectedStations && selectedStations.length > 0

const nameAndCodeOf=(entity) => entity.name + " ("+entity.code+")"

</script>

<!-- COUNTRIES -->
<MultiSelect options={countries}
             bind:selectedOptions={selectedCountries}
             placeholder={"Государств" + ((countries.length === 0) ? "а не найдены" : "о")}
             singleSelect={true}/>

{#await countriesPromise} <Dropdown text="Загружаю список государств"/>
{:catch error}            <Dropdown text="Не удалось загрузить список государств" type="error"/>
{/await}

<!-- ROADS -->
<MultiSelect placeholder={"Дороги" + ((selectedCountries.length > 0 && roads.length === 0) ? " не найдены" : "")}
             options={roads}
             bind:selectedOptions={selectedRoads}/>

{#await roadsPromise} <Dropdown text="Загружаю список дорог"/>
{:catch error}        <Dropdown text="Не удалось загрузить список дорог" type="error"/>
{/await}

<!-- STATIONS -->
<MultiSelect placeholder={"Станции" + ((selectedRoads.length > 0 && stations.length === 0) ? " не найдены" : "")}
             allowSelectAll={true}
             options={stations}
             bind:selectedOptions={selectedStations}/>

{#await stationsPromise} <Dropdown text="Загружаю список станций"/>
{:catch error}           <Dropdown text="Не удалось загрузить список станций" type="error"/>
{/await}