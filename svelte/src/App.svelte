<script>
    import ParamsForm from "./components/ParamsForm/ParamsForm.svelte"
    import Report1 from "./components/reports/Report1.svelte"
    import {httpClient} from "./web/httpClient";

    function generateReport1(event) {
        const detail = event.detail,
            period = detail.period,
            carrier = detail.carrier,
            departure = detail.departure,
            destination = detail.destination

        alert(period.date1)
    }

</script>
<p> Анализ корреспонденций пассажиропотоков </p>

<ParamsForm on:generateReport={generateReport1}/>

{#if generateReport1}
    <Report1 />
{/if}

<style>
    p {
        text-align: center;
        font-size: x-large;
    }
</style>