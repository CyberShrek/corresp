<!-- Drops a floating div exactly where the <Notation> component was added-->

<script>
    import { onMount } from "svelte"
    import { blur } from "svelte/transition"

    export let message,
        isError = false

    let color = "white",
        background = isError ? "red" : "lightskyblue"

    let elem, top, width
    onMount(() => {
        const prevElem = elem.previousElementSibling
        top = prevElem.offsetTop + prevElem.clientHeight + "px"
        width = elem.parentElement.clientWidth + "px"
    })
</script>

<p bind:this={elem}
   transition:blur
   style="top:{top}; width:{width}; color:{color}; background:{background}">
    {message}
</p>

<style>
    p {
        position:absolute;
        margin:5px;
        text-align: center;
        border-radius: 5px;
    }
</style>