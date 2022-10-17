<script>
    import {fade} from "svelte/transition"
    import PopUp from "../../common/PopUp.svelte"
    import {scrollInto} from "../../../utils"
    import {httpClient} from "../../../web/httpClient"

    export let
        title,
        report,
        reportNum,
        inputParams,
        windowed = false

    $: promise = inputParams && httpClient.getReportByParams(reportNum, inputParams)
        .then(res => updateReport(res))

    let reportEl
    function updateReport(rep) {
        report = rep
        // scrollInto(reportEl)
    }

</script>

{#if windowed}
    {#await promise}
        <PopUp text="Загрузка отчёта" type="loading"/>
    {:then _}
        <PopUp on:exit>
            <report transition:fade
                    bind:this={reportEl}
                    style="height: {windowed ? '' : '100vh'}">

                {#if report.rows.length > 1}
                    <header> <p>{title}</p> </header>
                    <slot></slot>
                {:else}
                    Ничего не найдено 😕
                {/if}
            </report>
        </PopUp>
    {:catch error}
        <PopUp on:exit>
            Ошибка загрузки 🤬
        </PopUp>
    {/await}
{:else}
    <report transition:fade
            bind:this={reportEl}
            style="min-height: {windowed ? 'auto' : '100vh'}">
        {#await promise}
            <PopUp text="Загрузка отчёта" type="loading"/>
        {:then _}
            <header> <p>{title}</p> </header>
            {#if report.rows.length > 1}
                <slot></slot>
                {#if scrollInto(reportEl)}{''}{/if}
            {:else}
                Ничего не найдено 😕
                {#if scrollInto(reportEl)}{''}{/if}
            {/if}
        {:catch error}
            <header> <p>{title}</p> </header>
            Ошибка загрузки 🤬
            {#if scrollInto(reportEl)}{''}{/if}
        {/await}
    </report>
{/if}


<style>
    report {
        display: flex;
        flex-direction: column;
        text-align: center;
        background: white;
        border-radius: calc(var(--border-radius) + 2px);
        box-shadow: var(--shadow)
    }
    report header {
        display:flex;
        justify-content:center;
        align-items:center;
        margin: 2px;
        width: auto;
        height:50px;
        color:white;
        font-weight:bold;
        background: var(--theme-color);
        border-radius: var(--border-radius);
    }
    report header p{
        font-size: x-large;
        text-align: center;
    }
</style>