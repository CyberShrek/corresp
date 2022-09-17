<script>
    import Heading from "./parts/Heading.svelte"
    import Dropdown from "../common/Dropdown.svelte"

    const today = new Date().toJSON().slice(0, 10)

    export const params = {
        date1: today,
        date2: today,
        compareWithLastYear: false
    }
    export let isValid

    const getDaysFromDate=(date) => new Date(date).getTime() / (1000 * 3600 * 24)
    $: date1Days = getDaysFromDate(params.date1)
    $: date2Days = getDaysFromDate(params.date2)
    $: daysCount = date2Days - date1Days
    $: isValid   = daysCount >= 0 && daysCount <= 62

</script>

<Heading text="Период"/>
<label> От: <input type="date" bind:value={params.date1} class:wrong={!isValid}> </label>
<label> До: <input type="date" bind:value={params.date2} class:wrong={!isValid}> </label>

{#if daysCount > 62}
    <Dropdown text="Укажите период менее 62 дней" type="error"/>
{:else if daysCount < 0}
    <Dropdown text="Дата 'От' не может быть больше даты 'До'" type="error"/>
{/if}

<label> <input type="checkbox" bind:checked={params.compareWithLastYear}> Сравнить с прошлым годом </label>