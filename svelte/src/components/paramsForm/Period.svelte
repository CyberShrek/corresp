<script>
    import Dropdown from "../modals/Dropdown.svelte"
    import {httpClient} from "../../web/httpClient";

    export let
        date1 = new Date().toJSON().slice(0, 10),
        date2 = date1,
        compareWithLastYear = false,
        isValid

    let daysCount
    const getDaysFromDate=(date) => new Date(date).getTime() / (1000 * 3600 * 24)
    function updateDaysCount() {
        daysCount = getDaysFromDate(date2) - getDaysFromDate(date1)
    }

    const resetDate1=() => date1 = date2
    const resetDate2=() => date2 = date1
    // On date1 update
    $: if(date1){
        updateDaysCount()
        if(daysCount < 0) resetDate2()
    }
    // On date2 update
    $: if(!date2) resetDate2()
    else {
        updateDaysCount()
        if(daysCount < 0) resetDate1()
    }

    $: isValid = daysCount <= 62

</script>

<label> От: <input type="date" bind:value={date1} class:wrong={!date1 || !isValid && date2}> </label>
<label> До: <input type="date" bind:value={date2} class:wrong={!date2 || !isValid && date1}> </label>

{#if daysCount > 62}
    <Dropdown text="Укажите период менее 62 дней" type="error"/>
{/if}

<label> <input type="checkbox" bind:checked={compareWithLastYear}> Сравнить с прошлым годом </label>