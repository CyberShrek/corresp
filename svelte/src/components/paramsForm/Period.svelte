<script>
    import Heading from "./parts/Heading.svelte"
    import Dropdown from "../common/Dropdown.svelte"

    export let
        date1 = new Date().toJSON().slice(0, 10),
        date2 = date1,
        compareWithLastYear = false,
        isValid

    const getDaysFromDate=(date) => new Date(date).getTime() / (1000 * 3600 * 24)
    $: date1Days = getDaysFromDate(date1)
    $: date2Days = getDaysFromDate(date2)
    $: daysCount = date2Days - date1Days
    $: isValid   = daysCount >= 0 && daysCount <= 62

</script>

<Heading text="Период"/>
<label> От: <input type="date" bind:value={date1} class:wrong={!date1 || !isValid && date2}> </label>
<label> До: <input type="date" bind:value={date2} class:wrong={!date2 || !isValid && date1}> </label>

{#if daysCount > 62}
    <Dropdown text="Укажите период менее 62 дней" type="error"/>
{:else if daysCount < 0}
    <Dropdown text="Дата 'От' не может быть больше даты 'До'" type="error"/>
{/if}

<label> <input type="checkbox" bind:checked={compareWithLastYear}> Сравнить с прошлым годом </label>