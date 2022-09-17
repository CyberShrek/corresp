<script>
    import Period from "./Period.svelte"
    import Carrier from "./Carrier.svelte"
    import Depstinature from "./Depstinature.svelte"
    import Dropdown from "../common/Dropdown.svelte";

    let period = {}, carrier = {}, departure = {}, destination = {},
        periodValid, carrierValid, departureValid, destinationValid

    $: allIsValid = periodValid && carrierValid && departureValid && destinationValid

</script>

<form>
    <period class="field"> <Period bind:params={period}
                                   bind:isValid={periodValid} /> </period>

    <carrier class="field"> <Carrier inputDate={period.date1}
                                     bind:params={carrier}
                                     bind:isValid={carrierValid}/> </carrier>

    <departure class="field"> <Depstinature headingText="Объект отправления"
                                            bind:params={departure}
                                            bind:isValid={departureValid}/> </departure>

    <destination class="field"> <Depstinature headingText="Объект назначения"
                                              bind:params={destination}
                                              bind:isValid={destinationValid}/> </destination>

</form>
<!--{#if allIsValid}-->
<Dropdown text="Сформировать отчёт" type="button" size="large"/>
<!--{/if}-->

<style>
    form {
        display: flex;

        margin: 5px;
        background-color: whitesmoke;
        border-radius: 5px;
        border: 1px solid lightgray;
        box-shadow: 0 0 3px gray
    }
    form > .field {
        display: flex;
        flex-direction: column;
        align-items: center;

        width: 180px;
        padding: 0 10px;
    }
    form > period, form > carrier, form > departure {
        border-right: 1px solid lightgray;
    }
</style>