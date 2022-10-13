<script>
    import {fade} from "svelte/transition"
    import PopUp from "../common/PopUp.svelte"
    import {scrollInto} from "../../utils"
    import {httpClient} from "../../web/httpClient"

    export let
        title,
        report,
        inputParams

    $: promise = inputParams && httpClient.getReportByParams(1, inputParams)
        .then(res => updateReport(res))

    function updateReport(rep) {
        report = rep
        scrollInto(reportEl)
    }

    let reportEl

</script>

<report transition:fade bind:this={reportEl}>
    <header> <p>{title}</p> </header>
    {#await promise}
        <PopUp text="Загрузка отчёта" type="loading"/>
    {:then _}
        <slot></slot>
    {:catch error}
        Ошибка загрузки 🤬
        {#if scrollInto(reportEl)}{''}{/if}
    {/await}
</report>

<style>
    report {
        display: flex;
        flex-direction: column;
        min-height: 100vh;
        text-align: center;
        box-shadow: var(--shadow)
    }
    report header {
        display:flex;
        justify-content:center;
        align-items:center;
        width:100%;
        height:50px;
        color:white;
        font-weight:bold;
        background: var(--theme-color);
    }
    report header p{
        font-size: x-large;
        text-align: center;
    }
</style>