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
<form id="params-form" bind:offsetWidth={width}>
    <field class="period">
        <p> Период </p>
        <div><Period bind:date1={period.date1}
                     bind:date2={period.date2}
                     bind:compareWithLastYear={period.compareWithLastYear}
                     bind:isValid={period.isValid}/>
        </div></field>

    <field class="carrier">
        <p> Перевозчик </p>
        <div><Carrier inputDate={period.date1}
                      bind:selected={carrier.selected}
                      bind:isValid={carrier.isValid}/>
        </div></field>

    <field class="departure">
        <p> Объекты отправления </p>
        <div><Depstinature inputDate={period.date1}
                           bind:selectedStations={departure.selectedStations}
                           bind:isValid={departure.isValid}/>
        </div></field>

    <field class="destination">
        <p> Объекты назначения </p>
        <div><Depstinature inputDate={period.date1}
                           bind:selectedStations={destination.selectedStations}
                           bind:isValid={destination.isValid}/>
        </div></field>
</form>

<input type="submit"
       id="params-submit"
       class:unavailable={!(period.isValid && carrier.isValid && departure.isValid && destination.isValid)}
       on:click|preventDefault={generateReport}
       value="Сформировать отчёт" style="width: {width}px"/>