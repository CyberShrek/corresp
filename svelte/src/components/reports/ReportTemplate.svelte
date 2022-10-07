<script>
    import {fade} from "svelte/transition"
    import PopUp from "../common/PopUp.svelte"
    import {scrollInto} from "../../utils"

    export let title, report, loadingPromise
</script>

<report transition:fade bind:this={report}>
    <header> <p>{title}</p> </header>
    {#await loadingPromise}
        <PopUp text="Загрузка отчёта" type="loading"/>
    {:then _}
        <slot></slot>
        {#if scrollInto(report)}{''}{/if}
    {:catch error}
        Ошибка загрузки 🤬
        {#if scrollInto(report)}{''}{/if}
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