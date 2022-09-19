<script>
    import Heading from "./parts/Heading.svelte"
    import {httpClient} from "../../web/httpClient"
    import Dropdown from "../common/Dropdown.svelte"
    import Select from "./parts/Select.svelte"

    export let inputDate, value, isValid

    $: isValid = value !== undefined && value !== "-"

    let carriers = {}
    const setCarriers=(arg) => carriers = arg

    let dropdown = {}
    const setDropdown=(text, type) => dropdown = {text, type}
</script>

<Heading text="Перевозчик"/>
<Select bind:value={value}>
    <option>-</option>
    {#each carriers as carrier (carrier)}
        <option>{carrier}</option>
    {/each}

    {#if inputDate}
        {#await httpClient.getCarriersByDate(inputDate)}{ setDropdown("Загружаю список перевозчиков") }
        {:then carriers}                                { setDropdown(false) }
            {#if carriers.length === 0}
                { setDropdown("Не найдено перевозчиков за указанный период", "warning") }
            {/if}
            { setCarriers(carriers) }
        {:catch error}
            { setDropdown("Не удалось загрузить данные", "error") }
        {/await}
    {/if}
</Select>

{#if dropdown.text}
    <Dropdown {...dropdown}/>
{/if}
