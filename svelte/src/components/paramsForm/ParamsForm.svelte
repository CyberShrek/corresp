<script>
    import Period from "./Period.svelte"
    import Carrier from "./Carrier.svelte"
    import Depstinature from "./Depstinature.svelte"
    import {createEventDispatcher} from "svelte"
    const dispatch = createEventDispatcher()

    let period = {}, carrier = {}, departure = {}, destination = {}

    let width
</script>
<form bind:offsetWidth={width}>
    <period class="field"> <Period bind:date1={period.date1}
                                   bind:date2={period.date2}
                                   bind:compareWithLastYear={period.compareWithLastYear}
                                   bind:isValid={period.isValid}/> </period>

    <carrier class="field"> <Carrier inputDate={period.date1}
                                     bind:selected={carrier.selected}
                                     bind:isValid={carrier.isValid}/> </carrier>

    <departure class="field"> <Depstinature headingText="Отправления"
                                            inputDate={period.date1}
                                            bind:type={departure.type}
                                            bind:values={departure.values}
                                            bind:isValid={departure.isValid}/> </departure>

    <destination class="field"> <Depstinature headingText="Назначения"
                                              inputDate={period.date1}
                                              bind:type={destination.type}
                                              bind:values={destination.values}
                                              bind:isValid={destination.isValid}/> </destination>
</form>

<input type="submit"
       class:unavailable={!(period.isValid && carrier.isValid && departure.isValid && destination.isValid)}
       on:click|preventDefault={() => dispatch("generateReport", {period, carrier, departure, destination})}
       value="Сформировать отчёт" style="width: {width}px"/>

<style>
    form {
        justify-self: center;
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
        display: block;
        justify-self: center;
        font-size: x-large;
        color: white;
        background: darkseagreen;
        border-radius: 5px;
        border: var(--border);
        box-shadow: var(--shadow);
    }
</style>