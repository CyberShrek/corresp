<script>
import Heading from "./parts/Heading.svelte"
import {httpClient} from "../../web/httpClient"
import Dropdown from "../common/Dropdown.svelte"
import MultiSelect from "../common/MultiSelect.svelte"



export let headingText, inputDate, type, selected = [], isValid

$: isValid = selected && selected.length > 0

let types = [{name:"Государство"}, {name:"Дорога"}, {name:"Станция"}]

</script>

<Heading text={headingText}/>


<MultiSelect bind:selected options={types} />
<MultiSelect bind:selected options={types} />
<MultiSelect bind:selected options={types} />
<!--<Select bind:value={type}>-->
<!--    <option>-</option>-->
<!--    <option>Государство</option>-->
<!--    <option>Дорога</option>-->
<!--    <option>Станция</option>-->
<!--</Select>-->

<!--<div>-->
<!--    {#await response}-->
<!--        <Dropdown text="Загружаю список объектов"/>-->
<!--    {:then entities}-->
<!--        {#if entities}-->
<!--        {#each entities as entity}-->
<!--            <label> <input type="checkbox"-->
<!--                           bind:group={selected}-->
<!--                           name={headingText.replace(" ", "-")}-->
<!--                           value={entity.code}/> {entity.value} </label>-->
<!--        {/each}-->
<!--        {/if}-->
<!--    {:catch error}-->
<!--        <Dropdown text="Не удалось загрузить данные" type="error"/>-->
<!--    {/await}-->
<!--</div>-->

<style>
div {
    display: flex;
    flex-direction: column;
    align-items: start;

    white-space: nowrap;
    width: 100%;
    overflow: auto;
}
</style>