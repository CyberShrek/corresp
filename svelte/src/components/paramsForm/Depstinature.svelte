<script>
import Heading from "./parts/Heading.svelte"
import Select from "./parts/Select.svelte";
import {httpClient} from "../../web/httpClient";
import Dropdown from "../common/Dropdown.svelte";

export let headingText, inputDate, type, values = [], isValid

$: isValid = values && values.length > 0

$: list = type === "Государство" ? httpClient.getCountriesByDate(inputDate)
        : type === "Дорога"      ? httpClient.getRoadsByDate(inputDate)
        : type === "Станция"     ? httpClient.getStationsByDate(inputDate)
        : null

</script>

<Heading text={headingText}/>
<Select bind:value={type}>
    <option>-</option>
    <option>Государство</option>
    <option>Дорога</option>
    <option>Станция</option>
</Select>

<div>
    {#await list}
        <Dropdown text="Загружаю список объектов"/>
    {:then entities}
        {#if entities}
        {#each entities as entity}
            <label> <input type="checkbox"
                           bind:group={values}
                           name={headingText.replace(" ", ".")}
                           value={entity}/> {entity} </label>
        {/each}
        {/if}
    {:catch error}
        <Dropdown text="Не удалось загрузить данные" type="error"/>
    {/await}
</div>

<style>
div {
    display: flex;
    flex-direction: column;
    align-items: start;
    width: 100%;
    overflow-y: auto;
}
</style>