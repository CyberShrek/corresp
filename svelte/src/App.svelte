<script>
    import ParamsForm from "./components/ParamsForm/ParamsForm.svelte"
    import Report1 from "./components/reports/Report1.svelte"

    let formParams

    // test
    formParams = {"period":{"isValid":true,"compareWithLastYear":false,"date2":"2022-07-05","date1":"2022-06-05"},"carrier":{"isValid":true,"selected":{"name":"АО \"ФПК\"                        ","code":"1"}},"departure":{"type":"r","selected":[{"name":"МОСКОВСКАЯ","code":"М"}],"isValid":true},"destination":{"type":"r","selected":[{"name":"МОСКОВСКАЯ","code":"М"}],"isValid":true}}
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