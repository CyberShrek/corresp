<script>
    import Period from "./Period.svelte"
    import Carrier from "./Carrier.svelte"
    import Depstinature from "./Depstinature.svelte"

    let period = {}, carrier = {}, departure = {}, destination = {}

    $: allIsValid = period.isValid && carrier.isValid && departure.isValid && destination.isValid

    let width
</script>

<form bind:clientWidth={width}>
    <period class="field"> <Period bind:date1={period.date1}
                                   bind:date2={period.date2}
                                   bind:compareWithLastYear={period.compareWithLastYear}
                                   bind:isValid={period.isValid}/> </period>

    <carrier class="field"> <Carrier inputDate={period.date1}
                                     bind:value={carrier.value}
                                     bind:isValid={carrier.isValid}/> </carrier>

    <departure class="field"> <Depstinature headingText="Объект отправления"
                                            inputDate={period.date1}
                                            bind:type={departure.type}
                                            bind:values={departure.values}
                                            bind:isValid={departure.isValid}/> </departure>

    <destination class="field"> <Depstinature headingText="Объект назначения"
                                              inputDate={period.date1}
                                              bind:type={destination.type}
                                              bind:values={destination.values}
                                              bind:isValid={destination.isValid}/> </destination>
</form>
<input type="submit"
       value="Сформировать отчёт"
       style="width: {width}"
       class:unavailable={!allIsValid}
/>

<style>
    form {
        display: flex;
        margin: 5px;
        background-color: whitesmoke;
        border-radius: 5px;
        border: var(--border);
        box-shadow: var(--shadow);
    }
    form > .field {
        display: flex;
        flex-direction: column;
        align-items: center;

        min-width: 180px;
        padding: 0 10px;
    }
    form > period{
        justify-content: space-between;
    }
    form > period, form > carrier, form > departure {
        border-right: var(--border);
    }

    input[type=submit]{
        background: darkseagreen;
        color: white;
        font-size: x-large;
        border-radius: 5px;
        border: var(--border);
        box-shadow: var(--shadow);
    }
</style>