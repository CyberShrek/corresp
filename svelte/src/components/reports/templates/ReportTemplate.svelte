<script>
    import PopUp from "../../modals/PopUp.svelte"
    import {httpClient} from "../../../web/httpClient"

    export let
        title,
        report,
        reportNum,
        reportEl,
        inputParams,
        reportPromise,
        allowExcelExport = false,
        allowImageExport = false

    $: reportPromise = inputParams && httpClient.getReportByParams(reportNum, inputParams)
        .then(res => updateReport(res))

    function updateReport(rep) {
        report = rep
        console.log(report)
    }

    function exportExcel(){
        window.open('data:application/vnd.ms-excel,' + encodeURIComponent(reportEl.querySelector("table").outerHTML))
    }

    function exportImage(){

    }

</script>

{#await reportPromise}
    <PopUp text="Загрузка отчёта" type="loading"/>
{:then _}
    {#if inputParams && !report}
        <PopUp text="Нет данных по выбранным параметрам" type="warning"
               on:exit={() => inputParams = null}/>
    {/if}
<!--{:catch error}-->
    <!--{#if inputParams}-->
    <!--    <PopUp text={"Ошибка: " + error} type="error"-->
    <!--           on:exit={() => inputParams = null}/>-->
    <!--{/if}-->
{/await}

<report bind:this={reportEl} class:unavailable={!report}>
    <header>
        <p>{title}</p>
        {#if allowExcelExport}
            <button title="Экпортировать в .xlsx"
                    on:click={exportExcel}>
                <img src="img/download.svg" alt="">
            </button>
        {/if}
        {#if allowImageExport}
            <button title="Экпортировать в .jpeg"
                    on:click={exportImage}>
                <img src="img/download.svg" alt="">
            </button>
        {/if}
        <slot name="after-button"></slot>
    </header>
    {#if report}
        <slot></slot>
    {/if}
</report>