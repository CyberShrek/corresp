<script>
    import ParamsForm from "./components/ParamsForm/ParamsForm.svelte"
    import Report1 from "./components/reports/Report1.svelte"
    import Report2 from "./components/reports/Report2.svelte"
    import Report3 from "./components/reports/Report3.svelte"
    import Report4 from "./components/reports/Report4.svelte"
    import Report5 from "./components/reports/Report5.svelte"
    import Report6 from "./components/reports/Report6.svelte"
    import FloatButton from "./components/common/FloatButton.svelte"
    import {scrollInto} from "./utils"
    import Report7 from "./components/reports/Report7.svelte";

    let report1Input, report2Input, report3Input, report4Input, report5Input, report6Input, report7Input

    let scrollY

    let test0 = {"date1":"2022-06-06",
        "date2":"2022-07-07",
        "compareWithLastYear":false,
        "carrierCode":"1",
        "departureType":"r",
        "departureCodes":["М"],
        "destinationType":"r",
        "destinationCodes":["М"]}

    let test1 = {"date1":"2022-06-06",
        "date2":"2022-07-07",
        "compareWithLastYear":true,
        "carrierCode":"1",
        "departureType":"r",
        "departureCodes":["М"],
        "destinationType":"r",
        "destinationCodes":["М"]}

</script>

<svelte:window bind:scrollY/>

<p> Анализ корреспонденций пассажиропотоков </p>

<ParamsForm on:generateReport1={(event) => report1Input = event.detail}/>

<div on:click={() => report1Input = test0}>Тест</div>
<div on:click={() => report1Input = test1}>Тест со сравнением с прошлым годом</div>

{#if report1Input}
    <Report1 inputParams={report1Input}
             on:generateReport2={(event) => report2Input = event.detail}

             on:generateReport4={(event) => report4Input = event.detail}
             on:generateReport5={(event) => report5Input = event.detail}
             on:generateReport7={(event) => report7Input = event.detail}/>
{/if}

{#if report2Input}
    <Report2 inputParams={report2Input} on:exit={() => report2Input = null}/>
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
{#if report7Input}
    <Report7 inputParams={report7Input}/>
{/if}

{#if (scrollY > 100)}
    <FloatButton title="Вернуться наверх"
                 image="img/to_top.svg"
                 right="20"
                 on:click={() => scrollInto(document.body)}/>
{/if}

<style>

    div {
        cursor: pointer;
        color: blue;
        align-items: center;
        justify-self: center;
        font-size: x-large;
    }
    p {
        text-align: center;
        font-size: x-large;
    }
</style>