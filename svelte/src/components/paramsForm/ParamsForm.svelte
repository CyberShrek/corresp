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
    <period class="field">
        <p> Период </p>
        <div><Period bind:date1={period.date1}
                     bind:date2={period.date2}
                     bind:compareWithLastYear={period.compareWithLastYear}
                     bind:isValid={period.isValid}/>
        </div></period>

    <carrier class="field">
        <p> Перевозчик </p>
        <div><Carrier inputDate={period.date1}
                      bind:selected={carrier.selected}
                      bind:isValid={carrier.isValid}/>
        </div></carrier>

    <departure class="field">
        <p> Отправления </p>
        <div><Depstinature inputDate={period.date1}
                           bind:type={departure.type}
                           bind:selected={departure.selected}
                           bind:isValid={departure.isValid}/>
        </div></departure>

    <destination class="field">
        <p> Назначения </p>
        <div><Depstinature inputDate={period.date1}
                           bind:type={destination.type}
                           bind:selected={destination.selected}
                           bind:isValid={destination.isValid}/>
        </div></destination>
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
        border: var(--border);
        border-radius: var(--border-radius);
        box-shadow: var(--shadow);
    }

    form > .field {
        display: flex;
        flex-direction: column;
        min-width: 180px;
        max-width: 25vw;
        padding: 0 10px;
    }

    form > .field > p {
        justify-self: start;
        margin: 0;
        width: 100%;
        text-align: center;
        font-size: large;
        padding: 10px 0;
        border-bottom: var(--border);
    }

    form > .field > div {
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        align-items: center;
        height: 100%;
    }

    form > period, form > carrier, form > departure { border-right: var(--border) }

    input[type=submit]{
        display: block;
        justify-self: center;
        font-size: x-large;
        color: white;
        background: darkseagreen;
        border: var(--border);
        box-shadow: var(--shadow);
    }
</style>