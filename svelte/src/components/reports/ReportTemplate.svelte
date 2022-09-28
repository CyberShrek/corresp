<script>
    import {fade} from "svelte/transition"
    import {afterUpdate} from "svelte"

    export let title

    let report

    afterUpdate(() => {
        // report.scrollIntoView({
        //     behavior: 'smooth'
        // })
    })
</script>

<report transition:fade bind:this={report}>
    <header> <p>{title}</p> </header>
    <slot></slot>
</report>

<style>
    report {
        display: flex;
        flex-direction: column;
        margin-top: 50vh;
    }
    report header {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        height: 50px;
        background: lightblue;
    }
    report header p{
        font-size: x-large;
        text-align: center;
    }
</style>