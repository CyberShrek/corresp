<!-- Draws an error popup exactly where the <Error> component was added-->

<script>
    import { onMount } from "svelte"
    import { blur } from "svelte/transition"

    export let message

    let elem, top, width
    onMount(() => {
        const prevElem = elem.previousElementSibling
        top = prevElem.offsetTop + prevElem.clientHeight
        width = elem.parentElement.clientWidth
    })
</script>

<p bind:this={elem}
   transition:blur
   style="top: {top}px; width: {width}px">
    {message}
</p>

<style>
    p {
        position:absolute;
        margin:5px;
        text-align: center;
        color:white;
        background-color:red;
        border-radius: 5px;
    }
</style>