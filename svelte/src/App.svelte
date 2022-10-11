<script>
    import ParamsForm from "./components/ParamsForm/ParamsForm.svelte"
    import Report1 from "./components/reports/Report1.svelte"
    import Report2 from "./components/reports/Report2.svelte"
    import Report3 from "./components/reports/Report3.svelte"
    import Report4 from "./components/reports/Report4.svelte"
    import Report5 from "./components/reports/Report5.svelte"
    import Report6 from "./components/reports/Report6.svelte"

    let report1Input, report2Input, report3Input, report4Input, report5Input, report6Input

    report1Input = {"date1":"2022-06-06","date2":"2022-07-07","compareWithLastYear":false,"carrierCode":"1","departureType":"r","departureCodes":["М"],"destinationType":"r","destinationCodes":["М"]}

</script>
<p> Анализ корреспонденций пассажиропотоков </p>

<ParamsForm on:generateReport1={(event) => report1Input = event.detail}/>

{#if report1Input}
    <Report1 inputParams={report1Input}
             on:generateReport2={(event) => report2Input = alert(JSON.stringify(event.detail))}
             on:generateReport3={(event) => report3Input = alert(JSON.stringify(event.detail))}
             on:generateReport4={(event) => report4Input = alert(JSON.stringify(event.detail))}
             on:generateReport5={(event) => report5Input = alert(JSON.stringify(event.detail))}
             on:generateReport6={(event) => report6Input = alert(JSON.stringify(event.detail))}/>
{/if}

{#if report2Input}
    <Report2 inputParams={report2Input}/>
{/if}
{#if report3Input}
    <Report3 inputParams={report3Input}/>
{/if}
{#if report4Input}
    <Report4 inputParams={report4Input}/>
{/if}
{#if report5Input}
    <Report5 inputParams={report5Input}/>
{/if}
{#if report6Input}
    <Report6 inputParams={report6Input}/>
{/if}

<style>
    p {
        text-align: center;
        font-size: x-large;
    }
</style>