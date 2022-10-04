<script>
    import ParamsForm from "./components/ParamsForm/ParamsForm.svelte"
    import Report1 from "./components/reports/Report1.svelte"

    let formParams
</script>
<p> Анализ корреспонденций пассажиропотоков </p>

<ParamsForm on:generateReport={(event) => formParams = event.detail}/>

{#if formParams}
    <Report1 inputParams={formParams}/>
{/if}

<style>
    p {
        text-align: center;
        font-size: x-large;
    }
</style>