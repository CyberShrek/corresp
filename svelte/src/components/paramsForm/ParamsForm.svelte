<script>
    import Period from "./Period.svelte"
    import Carrier from "./Carrier.svelte"
    import Depstinature from "./Depstinature.svelte"
    import {createEventDispatcher} from "svelte"
    import {CarrierEntity, DepstinatureEntity, PeriodEntity} from "../../web/model/ParamFormEntity"
    const dispatch = createEventDispatcher()

    let period = new PeriodEntity(),
        carrier = new CarrierEntity(),
        departure = new DepstinatureEntity(),
        destination = new DepstinatureEntity()

    let width

    function generateReport(){
        dispatch("generateReport1",
            {
                date1               : period.date1,
                date2               : period.date2,
                compareWithLastYear : period.compareWithLastYear,
                carrierCode         : carrier.selected.code,
                fromCodes           : departure.selectedStations.map(s => s.code),
                toCodes             : destination.selectedStations.map(s => s.code)
            })
    }

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
        <p> Объекты отправления </p>
        <div><Depstinature inputDate={period.date1}
                           bind:selectedStations={departure.selectedStations}
                           bind:isValid={departure.isValid}/>
        </div></departure>

    <destination class="field">
        <p> Объекты назначения </p>
        <div><Depstinature inputDate={period.date1}
                           bind:selectedStations={destination.selectedStations}
                           bind:isValid={destination.isValid}/>
        </div></destination>
</form>

<input type="submit"
       class:unavailable={!(period.isValid && carrier.isValid && departure.isValid && destination.isValid)}
       on:click|preventDefault={generateReport}
       value="Сформировать отчёт" style="width: {width}px"/>

<style>
    form {
        justify-self: center;
        display: flex;
        margin: 5px;
        background-color: var(--solid-color);
        border-radius: var(--border-radius);
        box-shadow: var(--shadow);
    }

    form > .field {
        display: flex;
        flex-direction: column;
        min-width: 180px;
        max-width: 25vw;
        padding: 0 10px;
        margin:  10px 0;
    }

    form > .field > p {
        justify-self: start;
        width: 100%;
        text-align: center;
        font-size: large;
        padding: 5px 0 10px 0;
        margin: 0 0 5px 0;
        border-bottom: var(--border);
    }

    form > .field > div {
        display: flex;
        flex-direction: column;
        align-items: center;
        height: 100%;
    }

    form > period, form > carrier, form > departure { border-right: var(--border) }

    input[type=submit]{
        z-index: 10;
        display: block;
        justify-self: center;
        margin-bottom: 100px;
        font-size: x-large;
        color: white;
        background: var(--submit-color);
        border: 0;
        box-shadow: var(--shadow);
    }
</style>