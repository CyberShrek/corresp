<script>
    import Heading from "../parts/Heading.svelte"
    import Dropdown from "../../common/Dropdown.svelte"

    export let date1 = new Date().toJSON().slice(0,10),
               date2 = date1 + ""
    $: daysCount = (new Date(date2).getTime() - new Date(date1).getTime()) / (1000 * 3600 * 24)
    $: periodIsValid = daysCount >= 0 && daysCount <= 62
</script>

<Heading text="Период"/>
<label> От: <input type="date" bind:value={date1} class:wrong={!periodIsValid}> </label>
<label> До: <input type="date" bind:value={date2} class:wrong={!periodIsValid}> </label>

{#if daysCount > 62}
    <Dropdown message="Укажите период менее 62 дней" isError="true"/>
{:else if daysCount < 0}
    <Dropdown message="Дата 'От' должна быть больше даты 'До'" isError="true"/>
{/if}

<label> <input type="checkbox"> Сравнение с прошлым годом </label>