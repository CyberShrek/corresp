<script>
    import Error from "../../popups/Error.svelte"

    // Period field
    const getToday=() => new Date().toJSON().slice(0,10)
    let date1 = getToday(), date2 = getToday()
    $: daysCount = (new Date(date2).getTime() - new Date(date1).getTime()) / (1000 * 3600 * 24)
    $: periodIsValid = daysCount >= 0 && daysCount <= 62
</script>

<form>
    <field>
        <p> Период </p>
        <date> От: <input type="date" bind:value={date1} class:wrong={!periodIsValid}> </date>
        <date> До: <input type="date" bind:value={date2} class:wrong={!periodIsValid}> </date>
        {#if daysCount > 62}
            <Error message="Укажите период менее 62 дней"/>
        {:else if daysCount < 0}
            <Error message="Дата 'От' должна быть больше даты 'До'"/>
        {/if}

        <label> <input type="checkbox"> Сравнение с прошлым годом </label>
    </field>
    <field></field>
    <field></field>
    <field></field>

    <submit><input type="submit" value="Сформировать отчёт"></submit>
</form>

<style>
    form {
        display: flex;

        margin: 5px;
        background-color: whitesmoke;
        border-radius: 5px;
        box-shadow: 0 0 3px gray
    }

    form field {
        display: flex;
        flex-direction: column;
        align-items: center;

        padding: 0 10px;
        border-right: 1px solid lightgray;
    }

    form field > p {
        margin: 0;
        width: 100%;
        text-align: center;
        font-size: large;
        padding: 10px 0;
        border-bottom: 1px solid lightgray;
    }
</style>